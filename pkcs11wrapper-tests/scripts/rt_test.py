#!/usr/bin/env python2
# -*- coding: utf-8 -*-
from os import path
from os import name as os_name
from platform import machine as os_machine
from platform import system as os_platform
from urllib2 import urlopen, HTTPError, Request
from re import compile, search
from zipfile import ZipFile
from glob import glob
from urllib import urlencode
from subprocess import Popen, PIPE, STDOUT
from shutil import copyfile

HACK_APK = "http://aktiv-builds/new/other/rtservice-2100M-debug.apk"


class SlotInfo:
    def __init__(self, token):
        # "token" is semicolon delimited string
        info = token.split(";")
        self.slot_number = info[0].rstrip()
        self.token_serial = info[1].rstrip()
        self.token_version = info[2].rstrip()
        self.token_model = info[3].rstrip()
        self.token_label = info[4].rstrip()
        self.token_type = info[5].rstrip()
        self.reader_name = info[6].rstrip()
        self.rsa_support = info[7].rstrip()


class AktivBuildsDownloader:
    def __init__(self, aktiv_builds_url, project_name, version, target_os, arch, target):
        self.aktiv_builds_url = aktiv_builds_url
        self.project_name = project_name
        self.package = "{0}-{1}.zip".format(project_name, version)
        self.package_url = "{0}/{1}/{2}".format(self.aktiv_builds_url, project_name, self.package)

        self.platform = "{0}-{1}".format(target_os, arch)
        self.target_os = target_os
        self.arch = arch
        self.target = target

    def __search_pattern(self, archive_list):
        platform = self.platform.replace("+", "\+")
        platform_list = platform.split("-")
        if len(platform_list) > 1:
            platform_list.pop(0)

        for suffix in reversed(platform_list):
            if self.target:
                pattern = ".*({0})[-^].*{1}|.*({0})[^_].*{1}".format(platform, self.target)
                match = filter(compile(pattern).search, archive_list)
                if match:
                    if len(match) == 1:
                        return ["{0}/{1}/**".format(self.project_name, match[0]), pattern]
                    else:
                        return ["{0}/{1}*-{2}/**".format(self.project_name, path.commonprefix(match), self.target),
                                pattern]

            pattern = ".*({0})[-^]|.*({0})$".format(platform)
            match = filter(compile(pattern).search, archive_list)
            if match:
                if len(match) == 1:
                    return ["{0}/{1}/**".format(self.project_name, match[0]), pattern]
                else:
                    return ["{0}/{1}*/**".format(self.project_name, path.commonprefix(match)), pattern]

            platform = platform.replace("-" + suffix, "")
            raise RuntimeError("Platform {0} not found in package".format(platform))

    def __get_archive_list(self):
        params = "?" + urlencode({"action": "list", "path": self.project_name})
        try:
            request = Request(self.package_url + params)
            response = urlopen(request)
        except HTTPError as e:
            # TODO: change to FileNotFoundError in python3
            raise RuntimeError("[-] : list {0} from {1} failed (return code {2})".format(self.project_name, self.package_url, e.code),
                e.code)
        else:
            content = response.read().split("\0")
            return filter(None, content)

    def __download_package(self, path_to_package, pattern):
        params = "?" + urlencode({"action": "get", "eglob": pattern})
        try:
            request = Request(self.package_url + params)
            response = urlopen(request)
            block_size = 5 * 1024
            with open(path_to_package, "wb") as archive:
                while True:
                    chunk = response.read(block_size)
                    if not chunk:
                        break
                    archive.write(chunk)
        except HTTPError as e:
            raise HTTPError("[-] : {0} download {1} failed (return code {2})".format(self.package, self.package_url, e.code))
        else:
            print "[+] : {0} download successful".format(self.package)

    def download(self, path_to_download):
        archive_list = self.__get_archive_list()
        pattern = self.__search_pattern(archive_list)
        self.__download_package(path_to_download, pattern[0])

    @staticmethod
    def unzip(path_to_package, path_to_extract="."):
        with ZipFile(path_to_package, "r") as archive:
            archive.extractall(path_to_extract)


class Common:
    def __init__(self, project_tests):
        self.project_name = project_tests.project_name
        self.target_os = project_tests.target_os
        self.arch = project_tests.arch
        self.build_variant = project_tests.build_variant
        self.token_type = project_tests.token_type
        self.build_folder = project_tests.build_folder

    @staticmethod
    def console(command, stream=False):
        ret = None
        out = ''
        print(command)

        try:
            process = Popen(command, stdout=PIPE, stderr=STDOUT, shell=True)
            if stream:
                for line in iter(process.stdout.readline, b""):
                    out += line
                    print line.rstrip()
                process.wait()
            else:
                out = process.communicate()[0]
            ret = process.returncode
        except Exception as e:
            ret = e.args[0]
            out = e
        finally:
            return {"code": ret, "message": out}

    @classmethod
    def execute(cls, command, stream=False, error_message=""):
        """Executes console command
        :raises RuntimeError on command nonzero return code"""
        res = cls.console(command, stream)
        if res["code"] != 0:
            message = error_message if error_message else res["message"]
            raise RuntimeError(str(message) + "\nexit code: " + str(res["code"]))
        return res["message"]

    @staticmethod
    def get_project_path(project_name, target_os, arch, build_variant):
        platform_folder_pattern = "{0}-{1}-*{2}".format(target_os, arch, build_variant)
        path_pattern = path.join(".", project_name, platform_folder_pattern)
        glob_folder = glob(path_pattern)
        if not glob_folder:
            # TODO: change to FileNotFoundError in python3
            raise RuntimeError("{0} doesn't exist".format(path_pattern))
        return glob_folder[0]

    @staticmethod
    def get_test_filters():
        filters = {}
        with open("test_filters", "r") as f:
            for line in f.read().splitlines():
                filter_name, value = line.split("=")[0], line.split("=")[1]
                filters[filter_name] = value
        return filters

    @classmethod
    def make_executable(cls, list_files):
        for file_ in list_files:
            cls.execute("chmod +x {0}".format(file_))

    @staticmethod
    def generate_name_for_os(file_name):
        if os_name == "nt":
            file_name += ".exe"
        return file_name

    @staticmethod
    def generate_lib_name_for_target_os(target_os, file_name):
        if 'windows' in target_os:
            file_name += ".dll"
        else:
            file_name = "lib" + file_name
            if os_platform() == "Darwin":
                file_name += ".dylib"
            else:
                file_name += ".so"
        return file_name

    def __execute_tokenchooser_locally(self, pkcs11ecp_lib_full_path, pkcs11ecp_lib_name, country):
        target_name = "tokenchooser"
        if country == "kaz":
            target_name = "tokenchooser-kaz"
        tokenchooser_folder = path.join(self.build_folder, target_name)

        if not path.exists(tokenchooser_folder):
            # TODO: change to FileNotFoundError in python3
            raise RuntimeError("tokenchooser folder does not exist")

        copyfile(pkcs11ecp_lib_full_path, path.join(tokenchooser_folder, pkcs11ecp_lib_name))
        tokenchooser_path = path.join(tokenchooser_folder, self.generate_name_for_os(target_name))
        message = self.execute(tokenchooser_path)
        print message
        return filter(None, message.split("\n"))

    def get_slots(self, pkcs11ecp_lib_full_path, pkcs11ecp_lib_name, country):
        tokens_info = self.__execute_tokenchooser_locally(pkcs11ecp_lib_full_path,
                                                                     pkcs11ecp_lib_name, country)
        slots = []
        for token in tokens_info:
            slot = SlotInfo(token)

            if search(self.token_type, slot.token_type):
                slots.append(slot)

        if not slots:
            raise RuntimeError("{0} token not found".format(self.token_type), 1)

        return slots

    @staticmethod
    def write_log(log_path, output):
        print "writing log file: " + log_path
        with open(log_path, "w") as log_file:
            log_file.write(output)

    @staticmethod
    def get_default_platform():
        machine = os_machine()
        system_name = os_platform()
        dict_system = {"Linux": "glibc",
                       "Windows": "windows",
                       "Darwin": "osx",
                       "FreeBSD": "freebsd"}

        architecture = "x86_64" if "64" in machine else "x86" if "86" in machine else None
        if system_name not in dict_system:
            raise Exception("OS {0} is not in list of known".format(system_name))
        if architecture is None:
            raise Exception("Architecture {0} for OS {1} is not in list of known".format(machine, system_name))

        return dict_system[system_name], architecture


class Android:
    def __init__(self, build_folder, arch, build_variant):
        self.build_folder = build_folder
        self.abi = self.__get_abi(arch)
        self.build_variant = build_variant
        self.device_serial = self.__get_device_serial()

    def start_rutoken_service(self):
        """Native application can not start service using rtpkcs11ecp.so"""
        """Older devices do not support start-foreground-service"""
        adb = "adb -s {0} ".format(self.device_serial)
        api_lvl = int(Common.execute(adb + "shell getprop ro.build.version.sdk"))

        if api_lvl >= 26:
            Common.execute(adb + "shell am start-foreground-service -n 'ru.rutoken/.RutokenService'")
        else:
            Common.execute(adb + "shell am startservice -n 'ru.rutoken/.RutokenService'")

    @staticmethod
    def parse_for_exit_code(output):
        return int(search("Complete with code: (\d+)", output).group(1))

    @staticmethod
    def __get_device_serial():
        message = Common.execute("adb devices")

        device_serial_match = search(r"([\w.:]+)\tdevice", message)
        if not device_serial_match:
            raise RuntimeError("Device not found")
        return device_serial_match.group(1)

    @staticmethod
    def __get_abi(arch):
        return {
            "armv7a": "armeabi-v7a",
            "arm64": "arm64-v8a"
        }.get(arch)

    def uninstall_rutoken_service(self):
        self.uninstall_package("ru.rutoken")

    def uninstall_package(self, package_name):
        Common.execute("adb -s {0} uninstall {1}".format(self.device_serial, package_name))

    def install_rutoken_service(self, is_standalone, downgrade=True):
        flavor = "standalone" if is_standalone else "withUI"
        template = "{0}/*/*rtservice-{1}-{2}.apk".format(self.build_folder, flavor, self.build_variant)
        files = glob(template)
        if not files:
            raise RuntimeError("RutokenService {0} not found".format(template))
        apk_file = files[0]
        Common.execute(
            "adb -s {0} install {1} -r {2} -g {3}".format(
                self.device_serial, self.__get_abi_option(), '-d' if downgrade else '', apk_file
            )
        )

    def install_with_hack_rutoken_service(self, is_standalone):
        # first install debuggable apk with versionCode = 2100000000 to downgrade release apk
        # without uninstalling to keep granted usb permissions
        hack_apk_data = urlopen(Request(HACK_APK)).read()
        apk2100M_filename = "rtservice-2100M-debug.apk"
        with open(apk2100M_filename, 'wb') as f:
            f.write(hack_apk_data)
        Common.execute("adb -s {0} install -r -d {1}".format(self.device_serial, apk2100M_filename))
        self.install_rutoken_service(is_standalone, downgrade=True)

    def install_package(self, package_path):
        Common.execute("adb -s {0} install {1} {2}".format(self.device_serial, self.__get_abi_option(), package_path))

    def update_rutoken_service(self, is_standalone):
        try:
            self.install_rutoken_service(is_standalone, downgrade=False)
        except RuntimeError as e:
            if "INSTALL_FAILED_VERSION_DOWNGRADE" not in str(e):
                raise e
            else:
                print "Installation of rtservice cancelled: version downgrade needed (and we don't want that)"

    def __get_abi_option(self):
        return "--abi {0}".format(self.abi) if self.abi is not None else ""
