/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.manager;

import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11KeyObject;
import ru.rutoken.pkcs11wrapper.reference.SessionReference;

public interface Pkcs11SignManager extends SessionReference {
    void signInit(Pkcs11Mechanism mechanism, Pkcs11KeyObject key);

    byte[] sign(byte[] data);

    /**
     * Version for optimisation: instead of calling C_Sign twice -
     * one call to acquire signed data length and the other with a properly
     * allocated memory for a result, there might be one call with presumably
     * enough allocated space.
     *
     * @param data               data to sign
     * @param maxSignatureLength maximum length of result signature
     * @return byte array of proper length ({@literal <= maxSignatureLength}) with signature
     */
    byte[] sign(byte[] data, int maxSignatureLength);

    default byte[] signAtOnce(byte[] data, Pkcs11Mechanism mechanism, Pkcs11KeyObject key) {
        signInit(mechanism, key);
        return sign(data);
    }

    void signUpdate(byte[] part);

    byte[] signFinal();

    void signRecoverInit(Pkcs11Mechanism mechanism, Pkcs11KeyObject key);

    byte[] signRecover(byte[] data);
}
