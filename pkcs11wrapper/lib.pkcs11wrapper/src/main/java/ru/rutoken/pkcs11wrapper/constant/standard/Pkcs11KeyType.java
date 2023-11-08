/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant.standard;

import org.jetbrains.annotations.Nullable;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11KeyType;

@SuppressWarnings("SpellCheckingInspection")
public enum Pkcs11KeyType implements IPkcs11KeyType {
    CKK_RSA(0x00000000L),
    CKK_DSA(0x00000001L),
    CKK_DH(0x00000002L),
    CKK_EC(0x00000003L),
    CKK_X9_42_DH(0x00000004L),
    CKK_KEA(0x00000005L),
    CKK_GENERIC_SECRET(0x00000010L),
    CKK_RC2(0x00000011L),
    CKK_RC4(0x00000012L),
    CKK_DES(0x00000013L),
    CKK_DES2(0x00000014L),
    CKK_DES3(0x00000015L),
    CKK_CAST(0x00000016L),
    CKK_CAST3(0x00000017L),
    CKK_CAST128(0x00000018L),
    CKK_RC5(0x00000019L),
    CKK_IDEA(0x0000001AL),
    CKK_SKIPJACK(0x0000001BL),
    CKK_BATON(0x0000001CL),
    CKK_JUNIPER(0x0000001DL),
    CKK_CDMF(0x0000001EL),
    CKK_AES(0x0000001FL),
    CKK_BLOWFISH(0x00000020L),
    CKK_TWOFISH(0x00000021L),
    CKK_SECURID(0x00000022L),
    CKK_HOTP(0x00000023L),
    CKK_ACTI(0x00000024L),
    CKK_CAMELLIA(0x00000025L),
    CKK_ARIA(0x00000026L),

    CKK_MD5_HMAC(0x00000027L),
    CKK_SHA_1_HMAC(0x00000028L),
    CKK_RIPEMD128_HMAC(0x00000029L),
    CKK_RIPEMD160_HMAC(0x0000002AL),
    CKK_SHA256_HMAC(0x0000002BL),
    CKK_SHA384_HMAC(0x0000002CL),
    CKK_SHA512_HMAC(0x0000002DL),
    CKK_SHA224_HMAC(0x0000002EL),

    CKK_SEED(0x0000002FL),
    CKK_GOSTR3410(0x00000030L),
    CKK_GOSTR3411(0x00000031L),
    CKK_GOST28147(0x00000032L),

    CKK_VENDOR_DEFINED(0x80000000L);
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
