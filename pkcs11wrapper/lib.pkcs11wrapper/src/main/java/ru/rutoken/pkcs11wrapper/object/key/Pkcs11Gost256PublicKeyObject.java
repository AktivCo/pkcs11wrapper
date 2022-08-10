package ru.rutoken.pkcs11wrapper.object.key;

/**
 * May be used for both 2001 and 2012 keys
 */
public class Pkcs11Gost256PublicKeyObject extends Pkcs11GostPublicKeyObject {
    protected Pkcs11Gost256PublicKeyObject(long objectHandle) {
        super(objectHandle);
    }
}
