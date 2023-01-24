/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant.standard;

import ru.rutoken.pkcs11jna.Pkcs11Constants;
import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;

public enum Pkcs11MaskGenerationFunction implements LongValueSupplier {
    CKG_MGF1_SHA1(Pkcs11Constants.CKG_MGF1_SHA1),
    CKG_MGF1_SHA256(Pkcs11Constants.CKG_MGF1_SHA256),
    CKG_MGF1_SHA384(Pkcs11Constants.CKG_MGF1_SHA384),
    CKG_MGF1_SHA512(Pkcs11Constants.CKG_MGF1_SHA512),
    CKG_MGF1_SHA224(Pkcs11Constants.CKG_MGF1_SHA224);

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
