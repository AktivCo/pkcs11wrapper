/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.manager;

import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11KeyObject;
import ru.rutoken.pkcs11wrapper.reference.SessionReference;

public interface Pkcs11EncryptionManager extends SessionReference {
    void encryptInit(Pkcs11Mechanism mechanism, Pkcs11KeyObject key);

    byte[] encrypt(byte[] data);

    default byte[] encryptAtOnce(byte[] data, Pkcs11Mechanism mechanism, Pkcs11KeyObject key) {
        encryptInit(mechanism, key);
        return encrypt(data);
    }

    byte[] encryptUpdate(byte[] part);

    byte[] encryptFinal();
}
