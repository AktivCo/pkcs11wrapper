/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.constant;

import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;
import ru.rutoken.pkcs11wrapper.constant.standard.EnumFromValueHelper;

public enum RtPkcs11KeyDerivationFunction implements LongValueSupplier {
    /**
     * rtpkcs11 extension KDF type for GOST 2001.
     */
    CKD_CPDIVERSIFY_KDF_JRT(0x90000009L),
    /**
     * rtpkcs11 extension KDF type for GOST 2001.
     */
    CKD_NULL_KDF_JRT(0x90000001L),

    /* GOST DIVERSIFICATION TYPES */
    CKD_KDF_4357(RtPkcs11MechanismType.CKM_KDF_4357.getAsLong()),
    CKD_KDF_GOSTR3411_2012_256(RtPkcs11MechanismType.CKM_KDF_GOSTR3411_2012_256.getAsLong());

    private static final EnumFromValueHelper<RtPkcs11KeyDerivationFunction> FROM_VALUE_HELPER =
            new EnumFromValueHelper<>();
    private final long mValue;

    RtPkcs11KeyDerivationFunction(long value) {
        mValue = value;
    }

    public static RtPkcs11KeyDerivationFunction fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, RtPkcs11KeyDerivationFunction.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }
}
