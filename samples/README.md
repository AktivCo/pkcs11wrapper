# pkcs11wrapper samples

## Description

A set of examples to illustrate pkcs11wrapper library API. These particular samples use JNA implementation. They are
guaranteed to work with the Java SE Development Kit 11.

NOTE: samples need [Rutoken](https://www.rutoken.ru/products/all/), some of them **erase all data on it**. There is
the `GlobalRun` class to run all samples, which **erases all data on the token**.

## How to build

First, you need to build pkcs11wrapper sources (see [How to build](../README.md#how-to-build)).

Project `samples` can be built using IntelliJ IDEA or from the console.

To build from the console:

```shell
./gradlew jar
```

The resulting JAR file with samples can be found in `build/libs/samples.jar`.

## How to run

Connect [Rutoken](https://www.rutoken.ru/products/all/) to your computer.

You must specify the full path to the rtpkcs11ecp framework if your OS is MacOS, or the full path to the directory
containing rtpkcs11ecp library otherwise. You can do it in the samples source code (see file `RtPkcs11Module.java`) or
by passing it as a console argument.

The samples can be run in IntelliJ IDEA one at a time or all at once (using the `GlobalRun` class).

The samples JAR can be launched by this command (it uses the `GlobalRun` class to run all samples):

```shell
java -jar build/libs/samples.jar # rtpkcs11ecp path is set in the samples source code
java -jar build/libs/samples.jar "path/to/pkcs11_library" # rtpkcs11ecp path is passed as an argument
```
