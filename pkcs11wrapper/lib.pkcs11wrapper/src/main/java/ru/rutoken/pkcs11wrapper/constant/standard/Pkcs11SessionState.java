package ru.rutoken.pkcs11wrapper.constant.standard;

import ru.rutoken.pkcs11jna.Pkcs11Constants;
import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;

public enum Pkcs11SessionState implements LongValueSupplier {
    CKS_RO_PUBLIC_SESSION(Pkcs11Constants.CKS_RO_PUBLIC_SESSION),
    CKS_RO_USER_FUNCTIONS(Pkcs11Constants.CKS_RO_USER_FUNCTIONS),
    CKS_RW_PUBLIC_SESSION(Pkcs11Constants.CKS_RW_PUBLIC_SESSION),
    CKS_RW_USER_FUNCTIONS(Pkcs11Constants.CKS_RW_USER_FUNCTIONS),
    CKS_RW_SO_FUNCTIONS(Pkcs11Constants.CKS_RW_SO_FUNCTIONS);

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
