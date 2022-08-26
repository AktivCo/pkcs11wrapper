#!/usr/bin/env python2
# -*- coding: utf-8 -*-

import signal
import os
from argparse import ArgumentParser
from glob import glob
from os import path, getcwd, remove
from os import name as os_name
from re import search
from subprocess import Popen

from rt_test import AktivBuildsDownloader, Android, Common


class TestRunner:
    def __init__(self, project_name, target_os, arch, target, token_type, production, local_library=False):
        self.workspace = getcwd()
        self.project_name = project_name

        self.target_os = target_os
        self.arch = arch
        self.build_variant = target
        self.token_type = token_type.upper()
        self.filters = Common.get_test_filters()
        self.production = production

        if local_library:
            self.build_folder = path.join(self.workspace, arch) if target_os == "android" else self.workspace
        else:
            self.build_folder = path.realpath(Common.get_project_path(project_name, target_os, arch, build_variant))

        self.android = Android(self.build_folder, arch, target) if target_os == "android" else None

        self.pcsc_lib_folder = path.join(self.build_folder, "rtpcsc")
        self.pcsc_lib_name = Common.generate_lib_name_for_target_os(target_os, "rtpcsc")
        self.pcsc_full_path = path.join(self.pcsc_lib_folder, self.pcsc_lib_name)

    def run_tests(self):
        if os_name != "nt" and self.target_os != "android":
            execute_files = glob("{0}/*/*".format(self.build_folder))
            Common.make_executable(execute_files)

        test_filter = "*"
        if token_type == "BT":
            test_filter = self.filters["BT_FILTER"]

        if self.target_os in ("android", "ios"):
            self.__run_tests_on_mobile()
        else:
            self.__run_tests_on_host(test_filter)

    def __run_tests_on_host(self, pcsc_test_filter):
        path_to_unit_tests = path.join(self.build_folder, Common.generate_name_for_os("pcscunittests"))
        command = "{0} --gtest_output='xml'".format(path_to_unit_tests)
        Common.execute(command, stream=True, error_message="pcscunittests failed")

        path_to_pcsc_test = path.join(self.build_folder, Common.generate_name_for_os("pcsctest"))
        command = "{0} --gtest_output='xml' --gtest_filter={1}".format(path_to_pcsc_test, pcsc_test_filter)
        Common.execute(command, stream=True, error_message="pcsctests failed")

    def __run_tests_on_mobile(self):
        if self.target_os != "android":
            raise RuntimeError("{0} currently not supported".format(self.target_os))
        if token_type not in ("BT", "ECP"):
            raise RuntimeError("{0} with {1} currently not supported".format(self.target_os, token_type))

        self.__resolve_rutoken_service()
        self.__run_instrumental_tests()

    def __get_pcsc_path(self):
        platform_folder_pattern = "{0}-{1}-*{2}".format(self.target_os, self.arch, self.build_variant)
        pcsc_path_pattern = path.join(".", self.project_name, platform_folder_pattern)
        glob_pcsc_folder = glob(pcsc_path_pattern)
        if not glob_pcsc_folder:
            raise RuntimeError("{0} doesn't exist".format(pcsc_path_pattern))
        return glob_pcsc_folder[0]

    def __resolve_rutoken_service(self):
        if self.production:
            self.android.update_rutoken_service(is_standalone=True)
        elif self.token_type == "ECP":
            self.android.install_with_hack_rutoken_service(is_standalone=True)
        else:
            try:
                self.android.uninstall_rutoken_service()
            except RuntimeError:
                pass
            self.android.install_rutoken_service(is_standalone=True)

    def __run_instrumental_tests(self):
        adb = "adb -s {0} ".format(self.android.device_serial)

        test_packages = self.__resolve_instrumental_tests()
        if not test_packages:
            print "NO INSTRUMENTAL TESTS FOUND"
            return

        self.android.start_rutoken_service()
        failed_packages = []
        for package in test_packages:
            Common.execute(adb + "logcat -c")
            file_name = "InstrumentalTest-logcat-{0}.log".format(package)

            # run in background
            logcat_process = Popen(adb + "logcat gtest *:S -v raw > " + file_name, shell=True,
                                   preexec_fn=os.setsid)

            command = adb + "shell am instrument " \
                      "-w -r -e debug false {0}/androidx.test.runner.AndroidJUnitRunner".format(package)

            try:
                output = Common.execute(command, stream=True)
                if search("FAILURES!!!", output) is not None:
                    failed_packages.append(package)
                Common.write_log("InstrumentalTest-{0}.log".format(package), output)
            except RuntimeError as e:
                failed_packages.append(package)
                print e.message

            # need to kill process group because of shell=True parameter
            os.killpg(os.getpgid(logcat_process.pid), signal.SIGTERM)
            logcat_process.wait()

            contains_gtest_logs = True
            with open(file_name, "r") as logcat_output:
                # File can contain two logcat strings ("beginning of main" and "beginning of system").
                # Check if file contains gtest logs (its lines number must be more than 2); if not - it is removed
                for _ in range(3):
                    line = logcat_output.readline()
                    if not line:
                        os.remove(file_name)
                        contains_gtest_logs = False
                        break

            if contains_gtest_logs:
                Common.execute("cat " + file_name, stream=True)

        if failed_packages:
            packages_list = "\n;".join(failed_packages)
            raise RuntimeError("Instrumental tests failed for:\n{0}.".format(packages_list))

        print "INSTRUMENTAL TESTS SUCCEEDED"

    def __resolve_instrumental_tests(self):
        installed_packages = []

        for test_apk in self.__get_test_apks():
            try:
                self.android.uninstall_package(test_apk.package)
            except RuntimeError:
                pass

            self.android.install_package(test_apk.path)
            installed_packages.append(test_apk.package)

        return installed_packages

    def __get_test_apks(self):
        apk_paths = glob(path.join(self.build_folder, "instrumental_tests", "*androidTest.apk"))

        return [self.TestApk(apk_path,
                             self.__get_package_name_for_path(apk_path))
                for apk_path in apk_paths]

    @staticmethod
    def __get_package_name_for_path(apk_path):
        info = Common.execute("aapt dump badging " + apk_path)
        package_name = search("^package: name='(.*?)'", info)

        if package_name is None:
            raise RuntimeError("Can not find package name for {0}.".format(apk_path))

        return package_name.group(1)

    class TestApk:
        def __init__(self, apk_path, package):
            self.path = apk_path
            self.package = package


def get_arguments():
    parser = ArgumentParser(description="Script for run pcsc tests")
    parser.add_argument("-p", "--platform", help="Platform run pcsc tests", type=str, required=False)
    parser.add_argument("-t", "--target", help="Tests build variant: release/debug, default debug", type=str,
                        required=False,
                        choices=["release", "debug"], default="debug")
    parser.add_argument("-k", "--kind", help="Token type: ecp/lite/bt, default ecp", type=str, required=False,
                        default="ecp")
    parser.add_argument("-r", "--revision",
                        help="pcsc revision (if not specified, the local version of the library will be tested)",
                        type=str, required=False, default="unspecified")
    parser.add_argument("-s", "--sign", help="Production version, default false", type=str, required=False,
                        choices=["true", "false"], default="false")
    return parser.parse_args()


if __name__ == "__main__":
    args = get_arguments()
    project_name = 'pcsc-android'
    path_to_package = path.join(".", "{0}.zip".format(project_name))
    revision = args.revision
    if args.platform:
        target_os, arch = args.platform.split("-")[:2]
    else:
        target_os, arch = Common.get_default_platform()
    build_variant = args.target
    production = (args.sign == "true")
    token_type = args.kind.upper()

    if path.exists(project_name):
        print "WARNING: {0} folder already exists".format(project_name)

    local_library = (revision == "unspecified" or target_os not in ("android", "ios"))

    if not local_library:
        d = AktivBuildsDownloader("http://aktiv-builds/new", project_name, revision, target_os, arch, build_variant)
        d.download(path_to_package)
        d.unzip(path_to_package)
        remove(path_to_package)

    t = TestRunner(project_name, target_os, arch, build_variant, token_type, production, local_library)
    t.run_tests()
