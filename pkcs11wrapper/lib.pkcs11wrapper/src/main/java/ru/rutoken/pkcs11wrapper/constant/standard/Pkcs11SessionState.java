/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant.standard;

import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;

public enum Pkcs11SessionState implements LongValueSupplier {
    CKS_RO_PUBLIC_SESSION(0L),
    CKS_RO_USER_FUNCTIONS(1L),
    CKS_RW_PUBLIC_SESSION(2L),
    CKS_RW_USER_FUNCTIONS(3L),
    CKS_RW_SO_FUNCTIONS(4L);

    private static final EnumFromValueHelper<Pkcs11SessionState> FROM_VALUE_HELPER = new EnumFromValueHelper<>();
    private final long mValue;

    Pkcs11SessionState(long value) {
        mValue = value;
    }

    public static Pkcs11SessionState fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, Pkcs11SessionState.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }
}
