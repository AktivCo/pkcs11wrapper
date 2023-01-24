/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant.standard;

import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;

public enum Pkcs11PseudoRandomFunction implements LongValueSupplier {
    CKP_PKCS5_PBKD2_HMAC_SHA1(0x00000001L),
    CKP_PKCS5_PBKD2_HMAC_GOSTR3411(0x00000002L),
    CKP_PKCS5_PBKD2_HMAC_SHA224(0x00000003L),
    CKP_PKCS5_PBKD2_HMAC_SHA256(0x00000004L),
    CKP_PKCS5_PBKD2_HMAC_SHA384(0x00000005L),
    CKP_PKCS5_PBKD2_HMAC_SHA512(0x00000006L),
    CKP_PKCS5_PBKD2_HMAC_SHA512_224(0x00000007L),
    CKP_PKCS5_PBKD2_HMAC_SHA512_256(0x00000008L);

    private static final EnumFromValueHelper<Pkcs11PseudoRandomFunction> FROM_VALUE_HELPER =
            new EnumFromValueHelper<>();
    private final long mValue;

    Pkcs11PseudoRandomFunction(long value) {
        mValue = value;
    }

    public static Pkcs11PseudoRandomFunction fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, Pkcs11PseudoRandomFunction.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }
}
