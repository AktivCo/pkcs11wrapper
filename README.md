# pkcs11wrapper

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ru.rutoken.pkcs11wrapper/pkcs11wrapper/badge.svg)](https://maven-badges.herokuapp.com/maven-central/ru.rutoken.pkcs11wrapper/pkcs11wrapper)

## Description

Java library that simplifies PKCS11 native (C) library usage from Java code.

The library defines two general layers, called high-level and low-level.

* The high-level layer defines object-oriented abstractions of PKCS11 session, slot, object, attribute etc. It uses
  low-level layer internally. In most cases it should be more convenient to use high-level layer, but you can go to
  low-level at any time.
* The low-level layer defines Java interface which is very similar to native (C) PKCS11 interface. You may want to use
  this layer to change PKCS11 implementation to fake, JNI, JNA or something else (we have JNI and fake only). In case of
  JNA implementation, this layer completely hides all issues related to calls from Java to C/C++ code.

## Repository structure

This repository includes the following projects:

1. `pkcs11wrapper` - the source code of the pkcs11wrapper library, which contains two modules:
    * `lib.pkcs11wrapper` - pkcs11wrapper library written in Java;
    * `lib.pkcs11wrapper-ktx` - pkcs11wrapper API extensions library written in Kotlin. It is unstable and incomplete;
2. `pkcs11wrapper-tests` - tests for the pkcs11wrapper library, implemented as Android instrumented tests and written in
   Java/Kotlin. They are designed for our internal use;
3. `samples` - examples of pkcs11wrapper library usage written in Java. For more information
   see [samples README](samples/README.md).

## How to build

Project `pkcs11wrapper` can be built using IntelliJ IDEA or from the console.

To build from the console:

```shell
cd pkcs11wrapper
./gradlew build
```

## Add pkcs11wrapper to your project

Grab pkcs11wrapper from Maven Central at the
coordinates `ru.rutoken.pkcs11wrapper:pkcs11wrapper:${pkcs11wrapperVersion}` or add it to your project:

### Gradle example

```kotlin
dependencies {
    implementation("ru.rutoken.pkcs11wrapper:pkcs11wrapper:${pkcs11wrapperVersion}")
}
```

Notice that pkcs11wrapper depends on [pkcs11jna](https://search.maven.org/artifact/ru.rutoken/pkcs11jna) library that
depends on JNA library. In case you want to include your own version of JNA in the project you should exclude
pkcs11jna's JNA dependency. This is mandatory for Android projects.

### Android Gradle example

```kotlin
dependencies {
    implementation("ru.rutoken.pkcs11wrapper:pkcs11wrapper:${pkcs11wrapperVersion}@jar")
    implementation("net.java.dev.jna:jna:${jnaVersion}@aar")
}
```

## Quick start

To start using pkcs11wrapper, you should extend Pkcs11BaseModule in your code.

JNA Example:

```java
public class Main {
    public static void main(String[] args) {
        Module module = new Module();
        try {
            module.initializeModule(new Pkcs11InitializeArgs.Builder().setOsLockingOk(true).build());
            List<Pkcs11Slot> slots = module.getSlotList(true);
            // do some job...
        } finally {
            module.finalizeModule();
        }
    }

    private static class Module extends Pkcs11BaseModule {
        Module() {
            super(new Pkcs11Api(new Pkcs11JnaLowLevelApi(Native.load("rtpkcs11ecp", Pkcs11.class),
                    new Pkcs11JnaLowLevelFactory.Builder().build())));
        }
    }
}
```
