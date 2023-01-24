/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.datatype;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PublicKeyObject;

/**
 * Immutable key pair.
 * Pkcs11 standard does not define this type, but pkcs11wrapper adds it for simplicity.
 */
public class Pkcs11KeyPair<PublicKey extends Pkcs11PublicKeyObject, PrivateKey extends Pkcs11PrivateKeyObject> {
    private final PublicKey mPublicKey;
    private final PrivateKey mPrivateKey;

    public Pkcs11KeyPair(PublicKey publicKey, PrivateKey privateKey) {
        mPublicKey = Objects.requireNonNull(publicKey);
        mPrivateKey = Objects.requireNonNull(privateKey);
    }

    public PublicKey getPublicKey() {
        return mPublicKey;
    }

    public PrivateKey getPrivateKey() {
        return mPrivateKey;
    }
}
