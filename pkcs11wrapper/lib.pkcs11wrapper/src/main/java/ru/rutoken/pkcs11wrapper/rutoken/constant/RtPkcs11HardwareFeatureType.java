/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.constant;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11HardwareFeatureType;
import ru.rutoken.pkcs11wrapper.constant.standard.EnumFromValueHelper;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11HardwareFeatureType;

public enum RtPkcs11HardwareFeatureType implements IPkcs11HardwareFeatureType {
    CKH_VENDOR_TOKEN_INFO(Pkcs11HardwareFeatureType.CKH_VENDOR_DEFINED.getAsLong() + 0x01),
    CKH_VENDOR_EMITENT_KEY(Pkcs11HardwareFeatureType.CKH_VENDOR_DEFINED.getAsLong() + 0x02),
    CKH_VENDOR_SECURE_COUNTER(Pkcs11HardwareFeatureType.CKH_VENDOR_DEFINED.getAsLong() + 0x03),
    CKH_VENDOR_NDEF_TAG(Pkcs11HardwareFeatureType.CKH_VENDOR_DEFINED.getAsLong() + 0x04),
    CKH_VENDOR_RNG(Pkcs11HardwareFeatureType.CKH_VENDOR_DEFINED.getAsLong() + 0x05),
    CKH_VENDOR_PIN_POLICY(Pkcs11HardwareFeatureType.CKH_VENDOR_DEFINED.getAsLong() + 0x06);

    private static final EnumFromValueHelper<RtPkcs11HardwareFeatureType> FROM_VALUE_HELPER =
            new EnumFromValueHelper<>();
    private final long mValue;

    RtPkcs11HardwareFeatureType(long value) {
        mValue = value;
    }

    @Nullable
    public static RtPkcs11HardwareFeatureType nullableFromValue(long value) {
        return FROM_VALUE_HELPER.nullableFromValue(value, RtPkcs11HardwareFeatureType.class);
    }

    public static RtPkcs11HardwareFeatureType fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, RtPkcs11HardwareFeatureType.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }

    public static class Factory implements IPkcs11HardwareFeatureType.VendorFactory {
        @Nullable
        @Override
        public RtPkcs11HardwareFeatureType nullableFromValue(long value) {
            return RtPkcs11HardwareFeatureType.nullableFromValue(value);
        }
    }
}