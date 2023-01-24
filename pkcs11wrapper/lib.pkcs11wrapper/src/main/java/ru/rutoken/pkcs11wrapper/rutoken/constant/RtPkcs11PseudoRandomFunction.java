/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.constant;

import ru.rutoken.pkcs11jna.Pkcs11Tc26Constants;
import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;
import ru.rutoken.pkcs11wrapper.constant.standard.EnumFromValueHelper;

public enum RtPkcs11PseudoRandomFunction implements LongValueSupplier {
    CKP_PKCS5_PBKD2_HMAC_GOSTR3411_2012_512(Pkcs11Tc26Constants.CKP_PKCS5_PBKD2_HMAC_GOSTR3411_2012_512);

    private static final EnumFromValueHelper<RtPkcs11PseudoRandomFunction> FROM_VALUE_HELPER =
            new EnumFromValueHelper<>();
    private final long mValue;

    RtPkcs11PseudoRandomFunction(long value) {
        mValue = value;
    }

    public static RtPkcs11PseudoRandomFunction fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, RtPkcs11PseudoRandomFunction.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }
}
