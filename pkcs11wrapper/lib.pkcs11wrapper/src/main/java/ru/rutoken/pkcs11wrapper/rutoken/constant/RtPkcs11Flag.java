/*
 * Copyright (c) 2025, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.constant;

import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;

public enum RtPkcs11Flag implements LongValueSupplier {
    // backported define from pkcs3.0 header required for eddsa support
    CKF_EC_OID(0x00800000L);

    private final long mValue;

    RtPkcs11Flag(long value) {
        mValue = value;
    }

    @Override
    public long getAsLong() {
        return mValue;
    }
}
