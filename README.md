# Pkcs11Wrapper

This is Java library that simplifies PKCS11 native (C) library usage from Java code.

## Architecture

The library defines two general layers, called high-level and low-level.

* The high-level defines OOP classes that represent such PKCS11 abstractions like session, slot, object, 
attribute etc. and uses low-level to implement them. 
In most cases it should be more convenient to use high-level layer, but you can go to low-level at any time.
* The low-level defines Java interface that is very similar to native (C) PKCS11 interface.
This layer may be used to swap PKCS11 implementation, for example to use fake, JNI, JNA or something else. 
In case of JNA implementation, this layer completely hides all issues related to calls from Java to C/C++ code.  

## Vendor defined values & C_EX_ functions

To add PKCS11 vendor defined values and extended functions support
a user can provide factories and implement interfaces defined by Pkcs11Wrapper.

## Quick start

To start using pkcs11wrapper, you should extend Pkcs11BaseModule in your code.

JNA Example:

```java
public class Main {
    public static void main(String[] args) {
        Module module = new Module();
        try {
            module.initializeModule(Pkcs11InitializeArgs.Builder().setOsLockingOk(true).build());
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
