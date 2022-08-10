package ru.rutoken.pkcs11wrapper.constant.standard;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11jna.Pkcs11Constants;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11ObjectClass;

public enum Pkcs11ObjectClass implements IPkcs11ObjectClass {
    CKO_DATA(Pkcs11Constants.CKO_DATA),
    CKO_CERTIFICATE(Pkcs11Constants.CKO_CERTIFICATE),
    CKO_PUBLIC_KEY(Pkcs11Constants.CKO_PUBLIC_KEY),
    CKO_PRIVATE_KEY(Pkcs11Constants.CKO_PRIVATE_KEY),
    CKO_SECRET_KEY(Pkcs11Constants.CKO_SECRET_KEY),
    CKO_HW_FEATURE(Pkcs11Constants.CKO_HW_FEATURE),
    CKO_DOMAIN_PARAMETERS(Pkcs11Constants.CKO_DOMAIN_PARAMETERS),
    CKO_MECHANISM(Pkcs11Constants.CKO_MECHANISM),
    CKO_OTP_KEY(Pkcs11Constants.CKO_OTP_KEY),

    CKO_VENDOR_DEFINED(Pkcs11Constants.CKO_VENDOR_DEFINED);

    private static final EnumFromValueHelper<Pkcs11ObjectClass> FROM_VALUE_HELPER = new EnumFromValueHelper<>();
    private final long mValue;

    Pkcs11ObjectClass(long value) {
        mValue = value;
    }

    @Nullable
    public static Pkcs11ObjectClass nullableFromValue(long value) {
        return FROM_VALUE_HELPER.nullableFromValue(value, Pkcs11ObjectClass.class);
    }

    public static Pkcs11ObjectClass fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, Pkcs11ObjectClass.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }
}
