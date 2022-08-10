package ru.rutoken.pkcs11wrapper.constant.standard;

import ru.rutoken.pkcs11jna.Pkcs11Constants;
import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;

public enum Pkcs11UserType implements LongValueSupplier {
    CKU_SO(Pkcs11Constants.CKU_SO),
    CKU_USER(Pkcs11Constants.CKU_USER),
    CKU_CONTEXT_SPECIFIC(Pkcs11Constants.CKU_CONTEXT_SPECIFIC);

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
