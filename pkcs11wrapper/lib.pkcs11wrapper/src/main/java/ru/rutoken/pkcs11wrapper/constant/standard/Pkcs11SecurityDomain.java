package ru.rutoken.pkcs11wrapper.constant.standard;

import ru.rutoken.pkcs11wrapper.constant.AttributeLongValueSupplier;

public enum Pkcs11SecurityDomain implements AttributeLongValueSupplier {
    CK_SECURITY_DOMAIN_UNSPECIFIED(0L),
    CK_SECURITY_DOMAIN_MANUFACTURER(1L),
    CK_SECURITY_DOMAIN_OPERATOR(2L),
    CK_SECURITY_DOMAIN_THIRD_PARTY(3L);

    private static final EnumFromValueHelper<Pkcs11SecurityDomain> FROM_VALUE_HELPER = new EnumFromValueHelper<>();
    private final long mValue;

    Pkcs11SecurityDomain(long value) {
        mValue = value;
    }

    public static Pkcs11SecurityDomain fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, Pkcs11SecurityDomain.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }
}