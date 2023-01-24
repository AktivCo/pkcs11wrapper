/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant.standard;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11jna.Pkcs11Constants;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11HardwareFeatureType;

public enum Pkcs11HardwareFeatureType implements IPkcs11HardwareFeatureType {
    CKH_MONOTONIC_COUNTER(Pkcs11Constants.CKH_MONOTONIC_COUNTER),
    CKH_CLOCK(Pkcs11Constants.CKH_CLOCK),
    CKH_USER_INTERFACE(Pkcs11Constants.CKH_USER_INTERFACE),
    CKH_VENDOR_DEFINED(Pkcs11Constants.CKH_VENDOR_DEFINED);

    private static final EnumFromValueHelper<Pkcs11HardwareFeatureType> FROM_VALUE_HELPER = new EnumFromValueHelper<>();
    private final long mValue;

    Pkcs11HardwareFeatureType(long value) {
        mValue = value;
    }

    @Nullable
    public static Pkcs11HardwareFeatureType nullableFromValue(long value) {
        return FROM_VALUE_HELPER.nullableFromValue(value, Pkcs11HardwareFeatureType.class);
    }

    public static Pkcs11HardwareFeatureType fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, Pkcs11HardwareFeatureType.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }
}
