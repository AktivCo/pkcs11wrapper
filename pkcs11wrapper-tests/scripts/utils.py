#!/usr/bin/env python3

from time import sleep
from typing import Optional, Tuple
from re import findall, search
from subprocess import Popen, PIPE, STDOUT


class TestApk:
    def __init__(self, apk_path, package) -> None:
        self.path = apk_path
        self.package = package


class Console:
    @classmethod
    def execute(cls, command, stream=False) -> str:
        return_code, message = cls.__console(command, stream)
        if return_code != 0:
            raise RuntimeError(f"{message}\nexit code: {return_code}")
        return message

    @staticmethod
    def __console(command, stream=False) -> Tuple[int, str]:
        print(command)
        try:
            process = Popen(command, stdout=PIPE, stderr=STDOUT, shell=True, text=True)
            if stream:
                out = ""
                for line in iter(process.stdout.readline, ""):
                    out += line
                    print(line.rstrip())
                process.wait()
            else:
                out = process.communicate()[0]
            return process.returncode, out

        except Exception as e:
            return -1, str(e)


class Adb:
    def __init__(self, arch) -> None:
        self.__require_single_device_connected()
        self.__abi_option = self.__get_abi_option(arch)

    @staticmethod
    def uninstall_package(package_name) -> None:
        Console.execute("adb uninstall " + package_name)

    @staticmethod
    def start_rutoken_service() -> None:
        api_lvl = int(Console.execute("adb shell getprop ro.build.version.sdk"))

        if api_lvl >= 26:
            Console.execute("adb shell am start-foreground-service -n 'ru.rutoken/.RutokenService'")
        else:  # Older devices do not support start-foreground-service
            Console.execute("adb shell am startservice -n 'ru.rutoken/.RutokenService'")

        sleep(2)  # Giving the service a moment to find tokens

    @staticmethod
    def parse_for_exit_code(output) -> int:
        return int(search("Complete with code: (\d+)", output).group(1))

    @staticmethod
    def run_tests(package_name: str) -> str:
        command = f"adb shell am instrument -w -r -e debug false {package_name}/androidx.test.runner.AndroidJUnitRunner"
        return Console.execute(command, stream=True)

    @staticmethod
    def __require_single_device_connected() -> None:
        command = Console.execute("adb devices")
        device_count = len(findall(r"([\w.:]+)\tdevice", command))

        if device_count == 0:
            raise RuntimeError("Device not found")
        elif device_count > 1:
            raise RuntimeError("Too many devices")

    def install_package(self, apk_path: str) -> None:
        Console.execute(f"adb install -r -g {self.__abi_option} {apk_path}")

    def update_rutoken_service(self, service_apk: str) -> None:
        try:
            self.__install_rutoken_service(service_apk)
        except RuntimeError as e:
            if "INSTALL_FAILED_VERSION_DOWNGRADE" not in str(e):
                raise
            else:
                print("Installation of Rutoken service is cancelled: version downgrade needed (and we don't want that)")

    def update_tests(self, tests: TestApk) -> None:
        try:
            self.install_package(tests.path)
        except RuntimeError:
            self.uninstall_package(tests.package)
            self.install_package(tests.path)

    def __install_rutoken_service(self, service_apk: str) -> None:
        self.install_package(service_apk)

    @staticmethod
    def __get_abi_option(arch: str) -> Optional[str]:
        abi = {
            "armv7a": "armeabi-v7a",
            "arm64": "arm64-v8a"
        }.get(arch)

        return f"--abi {abi}" if abi is not None else ""
