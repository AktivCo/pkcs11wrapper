#!/usr/bin/env python3

from argparse import ArgumentParser, Namespace
from pathlib import Path
from re import search

from utils import Adb, Console, TestApk


class TestRunner:
    def __init__(self, arch: str, service_path: str, tests_path: str) -> None:
        self.__service_path = self.__validate_apk_path(service_path)
        self.__tests: TestApk = TestApk(self.__validate_apk_path(tests_path), self.__get_package_for_apk(tests_path))
        self.adb = Adb(arch)

    def run(self) -> None:
        self.__resolve_rutoken_service()
        self.__resolve_tests()
        self.__run_instrumented_tests()

    @staticmethod
    def __validate_apk_path(filepath: str) -> str:
        apk_path: Path = Path(filepath)

        if (not apk_path.is_file()) or (apk_path.suffix != ".apk"):
            raise RuntimeError(f'"{filepath}" is not an apk path')

        return filepath

    def __resolve_rutoken_service(self) -> None:
        self.adb.update_rutoken_service(self.__service_path)

    def __resolve_tests(self) -> None:
        self.adb.update_tests(self.__tests)

    def __run_instrumented_tests(self) -> None:
        is_success: bool = True
        self.adb.start_rutoken_service()
        try:
            output = self.adb.run_tests(self.__tests.package)
            if search("FAILURES!!!", output) is not None:
                is_success = False
        except RuntimeError as e:
            is_success = False
            print(e)

        if not is_success:
            raise RuntimeError("INSTRUMENTED TESTS FAILED")

        print("INSTRUMENTED TESTS SUCCEEDED")

    @staticmethod
    def __get_package_for_apk(apk_path: str) -> str:
        info = Console.execute(f"aapt dump badging {apk_path}")
        package_name = search("^package: name='(.*?)'", info)

        if package_name is None:
            raise RuntimeError("Can not find package name for {0}.".format(apk_path))

        return package_name.group(1)


def get_arguments() -> Namespace:
    parser = ArgumentParser()
    parser.add_argument("-p", "--platform", help="Platform to run tests on (Jenkins label)", type=str, required=True)
    parser.add_argument("-s", "--service", help="Rutoken service apk path", type=str, required=True)
    parser.add_argument("-t", "--tests", help="Tests apk path", type=str, required=True)
    return parser.parse_args()


if __name__ == "__main__":
    args = get_arguments()
    tests_arch = args.platform.split("-")[1]

    TestRunner(tests_arch, args.service, args.tests).run()
