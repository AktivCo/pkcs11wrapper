package ru.rutoken.pkcs11wrapper.constant.standard;

import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;

public enum Pkcs11KeyDerivationFunction implements LongValueSupplier {
    CKD_NULL(0x00000001L),
    CKD_SHA1_KDF(0x00000002L),
    CKD_SHA1_KDF_ASN1(0x00000003L),
    CKD_SHA1_KDF_CONCATENATE(0x00000004L),
    CKD_SHA224_KDF(0x00000005L),
    CKD_SHA256_KDF(0x00000006L),
    CKD_SHA384_KDF(0x00000007L),
    CKD_SHA512_KDF(0x00000008L),
    CKD_CPDIVERSIFY_KDF(0x00000009L);

    private static final EnumFromValueHelper<Pkcs11KeyDerivationFunction> FROM_VALUE_HELPER =
            new EnumFromValueHelper<>();
    private final long mValue;

    Pkcs11KeyDerivationFunction(long value) {
        mValue = value;
    }

    public static Pkcs11KeyDerivationFunction fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, Pkcs11KeyDerivationFunction.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }
}
