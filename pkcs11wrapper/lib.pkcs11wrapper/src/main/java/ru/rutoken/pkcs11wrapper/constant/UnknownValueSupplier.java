/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant;

/**
 * This class is used when low-level (library) returns value unknown for pkcs11wrapper.
 * Pkcs11wrapper does not throw any exceptions if it meets unknown values,
 * but uses this class to represent them.
 */
abstract class UnknownValueSupplier implements LongValueSupplier {
    private final long mValue;

    UnknownValueSupplier(long value) {
        mValue = value;
    }

    @Override
    public long getAsLong() {
        return mValue;
    }
}
