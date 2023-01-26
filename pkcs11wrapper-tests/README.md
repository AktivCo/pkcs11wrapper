# pkcs11wrapper tests

## Description

Android instrumented tests for the pkcs11wrapper library, written in Java/Kotlin.

NOTE: some tests need [Rutoken](https://www.rutoken.ru/products/all/) and **can erase all data on it**.

## Requirements

`pkcs11wrapper-tests` should be built using Android SDK Platform 31 or newer and Java 11 JDK. `ANDROID_HOME`
and `JAVA_HOME` environment variables should also be set.

## How to build

First, you need to build pkcs11wrapper sources (see [How to build](../README.md#how-to-build)).

Then you need to download external dependencies:

```shell
python3 scripts/download_deps.py
```

After that, `pkcs11wrapper-tests` can be built using Android Studio or from the console.

To build from the console:

```shell
./gradlew assembleAndroidTest
```

The resulting apk files with tests can be found in
`pkcs11wrapper-tests/app/build/outputs/apk/androidTest/armv7a/debug/pkcs11wrapper-armv7a-debug-androidTest.apk` and
`pkcs11wrapper-tests/app/build/outputs/apk/androidTest/arm64/debug/pkcs11wrapper-arm64-debug-androidTest.apk`.

## How to run

Connect Android device (with USB cable or with Wi-Fi) and make sure that `adb` utility can see it (list of attached
devices is not empty):

```shell
adb devices
```

If you are not planning to use `run_tests.py` for running tests, you need to install Rutoken Control Panel version
1.9.0 or higher (or apk from external dependencies) on the device.

To install apk from external dependencies:

```shell
adb install external/rtservice/android/rtservice-withUI-release.apk
```

Before running tests, connect Rutoken to the device and make sure that Rutoken service is running.

There are 3 ways to launch pkcs11wrapper tests:

1. Run in Android Studio: right-click the `ru.rutoken.pkcs11wrapper` in `Project` tab and choose _Run 'Tests' in '
   ru.rutoken...'_.
2. Using Gradle in the console:
   ```shell
   ./gradlew connectedAndroidTest # install and run instrumentation tests both for arm64 and armv7
   ./gradlew connectedArm64DebugAndroidTest # install and run instrumentation tests for arm64
   ./gradlew connectedArmv7aDebugAndroidTest # install and run instrumentation tests for armv7
   ```
3. Using run_tests.py (this script automatically installs Rutoken Control Panel and starts service):
   ```shell
   python3 -u scripts/run_tests.py -p android-arm64 -s external/rtservice/android/rtservice-withUI-release.apk -t app/build/outputs/apk/androidTest/arm64/debug/pkcs11wrapper-arm64-debug-androidTest.apk # install and run tests for arm64
   python3 -u scripts/run_tests.py -p android-armv7a -s external/rtservice/android/rtservice-withUI-release.apk -t app/build/outputs/apk/androidTest/armv7a/debug/pkcs11wrapper-armv7a-debug-androidTest.apk # install and run tests for armv7
   ```