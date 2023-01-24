/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.key;

/**
 * May be used for both 2001 and 2012 keys
 */
public class Pkcs11Gost256PrivateKeyObject extends Pkcs11GostPrivateKeyObject {
    protected Pkcs11Gost256PrivateKeyObject(long objectHandle) {
        super(objectHandle);
    }
}
