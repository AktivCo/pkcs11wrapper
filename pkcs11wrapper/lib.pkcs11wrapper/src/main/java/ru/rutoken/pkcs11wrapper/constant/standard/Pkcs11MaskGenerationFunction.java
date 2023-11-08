/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant.standard;

import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;

public enum Pkcs11MaskGenerationFunction implements LongValueSupplier {
    CKG_MGF1_SHA1(0x00000001L),
    CKG_MGF1_SHA256(0x00000002L),
    CKG_MGF1_SHA384(0x00000003L),
    CKG_MGF1_SHA512(0x00000004L),
    CKG_MGF1_SHA224(0x00000005L);

    private static final EnumFromValueHelper<Pkcs11MaskGenerationFunction> FROM_VALUE_HELPER =
            new EnumFromValueHelper<>();
    private final long mValue;

    Pkcs11MaskGenerationFunction(long value) {
        mValue = value;
    }

    public static Pkcs11MaskGenerationFunction fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, Pkcs11MaskGenerationFunction.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }
}
