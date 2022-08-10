package ru.rutoken.pkcs11wrapper.rutoken.constant;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11jna.RtPkcs11Constants;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11HardwareFeatureType;
import ru.rutoken.pkcs11wrapper.constant.standard.EnumFromValueHelper;

public enum RtPkcs11HardwareFeatureType implements IPkcs11HardwareFeatureType {
    CKH_VENDOR_TOKEN_INFO(RtPkcs11Constants.CKH_VENDOR_TOKEN_INFO),
    CKH_VENDOR_EMITENT_KEY(RtPkcs11Constants.CKH_VENDOR_EMITENT_KEY),
    CKH_VENDOR_SECURE_COUNTER(RtPkcs11Constants.CKH_VENDOR_SECURE_COUNTER),
    CKH_VENDOR_NDEF_TAG(RtPkcs11Constants.CKH_VENDOR_NDEF_TAG),
    CKH_VENDOR_RNG(RtPkcs11Constants.CKH_VENDOR_RNG),
    CKH_VENDOR_PIN_POLICY(RtPkcs11Constants.CKH_VENDOR_PIN_POLICY);

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