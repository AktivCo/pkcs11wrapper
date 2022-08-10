package ru.rutoken.pkcs11wrapper.constant.standard;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11jna.Pkcs11Constants;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11KeyType;

@SuppressWarnings("SpellCheckingInspection")
public enum Pkcs11KeyType implements IPkcs11KeyType {
    CKK_RSA(Pkcs11Constants.CKK_RSA),
    CKK_DSA(Pkcs11Constants.CKK_DSA),
    CKK_DH(Pkcs11Constants.CKK_DH),
    CKK_EC(Pkcs11Constants.CKK_EC),
    CKK_X9_42_DH(Pkcs11Constants.CKK_X9_42_DH),
    CKK_KEA(Pkcs11Constants.CKK_KEA),
    CKK_GENERIC_SECRET(Pkcs11Constants.CKK_GENERIC_SECRET),
    CKK_RC2(Pkcs11Constants.CKK_RC2),
    CKK_RC4(Pkcs11Constants.CKK_RC4),
    CKK_DES(Pkcs11Constants.CKK_DES),
    CKK_DES2(Pkcs11Constants.CKK_DES2),
    CKK_DES3(Pkcs11Constants.CKK_DES3),
    CKK_CAST(Pkcs11Constants.CKK_CAST),
    CKK_CAST3(Pkcs11Constants.CKK_CAST3),
    CKK_CAST128(Pkcs11Constants.CKK_CAST128),
    CKK_RC5(Pkcs11Constants.CKK_RC5),
    CKK_IDEA(Pkcs11Constants.CKK_IDEA),
    CKK_SKIPJACK(Pkcs11Constants.CKK_SKIPJACK),
    CKK_BATON(Pkcs11Constants.CKK_BATON),
    CKK_JUNIPER(Pkcs11Constants.CKK_JUNIPER),
    CKK_CDMF(Pkcs11Constants.CKK_CDMF),
    CKK_AES(Pkcs11Constants.CKK_AES),
    CKK_BLOWFISH(Pkcs11Constants.CKK_BLOWFISH),
    CKK_TWOFISH(Pkcs11Constants.CKK_TWOFISH),
    CKK_SECURID(Pkcs11Constants.CKK_SECURID),
    CKK_HOTP(Pkcs11Constants.CKK_HOTP),
    CKK_ACTI(Pkcs11Constants.CKK_ACTI),
    CKK_CAMELLIA(Pkcs11Constants.CKK_CAMELLIA),
    CKK_ARIA(Pkcs11Constants.CKK_ARIA),

    CKK_MD5_HMAC(Pkcs11Constants.CKK_MD5_HMAC),
    CKK_SHA_1_HMAC(Pkcs11Constants.CKK_SHA_1_HMAC),
    CKK_RIPEMD128_HMAC(Pkcs11Constants.CKK_RIPEMD128_HMAC),
    CKK_RIPEMD160_HMAC(Pkcs11Constants.CKK_RIPEMD160_HMAC),
    CKK_SHA256_HMAC(Pkcs11Constants.CKK_SHA256_HMAC),
    CKK_SHA384_HMAC(Pkcs11Constants.CKK_SHA384_HMAC),
    CKK_SHA512_HMAC(Pkcs11Constants.CKK_SHA512_HMAC),
    CKK_SHA224_HMAC(Pkcs11Constants.CKK_SHA224_HMAC),

    CKK_SEED(Pkcs11Constants.CKK_SEED),
    CKK_GOSTR3410(Pkcs11Constants.CKK_GOSTR3410),
    CKK_GOSTR3411(Pkcs11Constants.CKK_GOSTR3411),
    CKK_GOST28147(Pkcs11Constants.CKK_GOST28147),

    CKK_VENDOR_DEFINED(Pkcs11Constants.CKK_VENDOR_DEFINED);
    /**
     * @deprecated CKK_EC is preferred.
     */
    @Deprecated
    public static final Pkcs11KeyType CKK_ECDSA = CKK_EC;
    /**
     * @deprecated CKK_CAST128 is preferred.
     */
    @Deprecated
    public static final Pkcs11KeyType CKK_CAST5 = CKK_CAST128;

    private static final EnumFromValueHelper<Pkcs11KeyType> FROM_VALUE_HELPER = new EnumFromValueHelper<>();
    private final long mValue;

    Pkcs11KeyType(long value) {
        mValue = value;
    }

    @Nullable
    public static Pkcs11KeyType nullableFromValue(long value) {
        return FROM_VALUE_HELPER.nullableFromValue(value, Pkcs11KeyType.class);
    }

    public static Pkcs11KeyType fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, Pkcs11KeyType.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }
}
