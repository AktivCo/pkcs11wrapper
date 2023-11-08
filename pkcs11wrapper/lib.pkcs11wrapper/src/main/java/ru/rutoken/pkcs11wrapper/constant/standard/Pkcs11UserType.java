/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant.standard;

import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;

public enum Pkcs11UserType implements LongValueSupplier {
    /**
     * Security Officer.
     */
    CKU_SO(0L),
    /**
     * Normal user.
     */
    CKU_USER(1L),
    CKU_CONTEXT_SPECIFIC(2L);

    private static final EnumFromValueHelper<Pkcs11UserType> FROM_VALUE_HELPER = new EnumFromValueHelper<>();
    private final long mValue;

    Pkcs11UserType(long value) {
        mValue = value;
    }

    public static Pkcs11UserType fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, Pkcs11UserType.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }
}
