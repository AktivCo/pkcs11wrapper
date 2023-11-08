/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.constant;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_GOST28147_KEY_GEN;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_GOSTR3410;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_GOSTR3410_KEY_PAIR_GEN;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_GOSTR3410_WITH_GOSTR3411;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_GOSTR3411;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_GOSTR3411_HMAC;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.internal.RtPkcs11InternalConstants.CK_VENDOR_PKCS11_RU_TEAM_TC26;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.constant.standard.EnumFromValueHelper;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType;

public enum RtPkcs11MechanismType implements IPkcs11MechanismType {
    CKM_GOSTR3410_512_KEY_PAIR_GEN(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x005L),
    CKM_GOSTR3410_512(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x006L),
    CKM_GOSTR3410_12_DERIVE(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x007L),
    CKM_GOSTR3410_WITH_GOSTR3411_12_256(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x008L),
    CKM_GOSTR3410_WITH_GOSTR3411_12_512(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x009L),
    CKM_GOSTR3410_PUBLIC_KEY_DERIVE(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x00AL),
    CKM_GOSTR3410_512_PUBLIC_KEY_DERIVE(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x00BL),

    CKM_GOSTR3411_12_256(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x012L),
    CKM_GOSTR3411_12_512(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x013L),
    CKM_GOSTR3411_12_256_HMAC(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x014L),
    CKM_GOSTR3411_12_512_HMAC(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x015L),

    CKM_TLS_GOST_PRF_2012_256(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x016L),
    CKM_TLS_GOST_PRF_2012_512(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x017L),
    CKM_TLS_GOST_MASTER_KEY_DERIVE_2012_256(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x018L),

    CKM_KDF_4357(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x025L),
    CKM_KDF_GOSTR3411_2012_256(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x026L),

    CKM_KDF_HMAC3411_2012_256(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x028L),
    CKM_KDF_TREE_GOSTR3411_2012_256(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x02AL),

    CKM_KUZNECHIK_KEXP_15_WRAP(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x02BL),
    CKM_MAGMA_KEXP_15_WRAP(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x02CL),
    CKM_KUZNECHIK_MGM(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x02DL),
    CKM_MAGMA_MGM(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x02EL),

    CKM_KUZNECHIK_KEY_GEN(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x030L),
    CKM_KUZNECHIK_ECB(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x031L),
    CKM_KUZNECHIK_CTR_ACPKM(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x032L),
    CKM_KUZNECHIK_MAC(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x033L),

    CKM_MAGMA_KEY_GEN(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x034L),
    CKM_MAGMA_ECB(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x035L),
    CKM_MAGMA_CTR_ACPKM(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x036L),
    CKM_MAGMA_MAC(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x037L),

    CKM_VENDOR_SECURE_IMPORT(Pkcs11MechanismType.CKM_VENDOR_DEFINED.getAsLong() + 3),
    CKM_VKO_GOSTR3410_2012_512(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x038L),
    CKM_GOST_KEG(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x039L);

    public static final Pkcs11MechanismType CKM_GOSTR3410_256_KEY_PAIR_GEN = CKM_GOSTR3410_KEY_PAIR_GEN;
    public static final Pkcs11MechanismType CKM_GOSTR3410_256 = CKM_GOSTR3410;
    public static final Pkcs11MechanismType CKM_GOSTR3410_WITH_GOSTR3411_94 = CKM_GOSTR3410_WITH_GOSTR3411;
    public static final RtPkcs11MechanismType CKM_GOSTR3410_2012_DERIVE = CKM_GOSTR3410_12_DERIVE;
    public static final RtPkcs11MechanismType CKM_GOSTR3410_WITH_GOSTR3411_2012_256 =
            CKM_GOSTR3410_WITH_GOSTR3411_12_256;
    public static final RtPkcs11MechanismType CKM_GOSTR3410_WITH_GOSTR3411_2012_512 =
            CKM_GOSTR3410_WITH_GOSTR3411_12_512;
    public static final Pkcs11MechanismType CKM_GOSTR3411_94 = CKM_GOSTR3411;
    public static final Pkcs11MechanismType CKM_GOSTR3411_94_HMAC = CKM_GOSTR3411_HMAC;
    public static final RtPkcs11MechanismType CKM_GOSTR3411_2012_256 = CKM_GOSTR3411_12_256;
    public static final RtPkcs11MechanismType CKM_GOSTR3411_2012_512 = CKM_GOSTR3411_12_512;
    public static final RtPkcs11MechanismType CKM_GOSTR3411_2012_256_HMAC = CKM_GOSTR3411_12_256_HMAC;
    public static final RtPkcs11MechanismType CKM_GOSTR3411_2012_512_HMAC = CKM_GOSTR3411_12_512_HMAC;
    public static final RtPkcs11MechanismType CKM_KUZNYECHIK_KEXP_15_WRAP = CKM_KUZNECHIK_KEXP_15_WRAP;
    public static final RtPkcs11MechanismType CKM_KUZNYECHIK_MGM = CKM_KUZNECHIK_MGM;
    public static final RtPkcs11MechanismType CKM_KUZNYECHIK_KEY_GEN = CKM_KUZNECHIK_KEY_GEN;
    public static final RtPkcs11MechanismType CKM_KUZNYECHIK_ECB = CKM_KUZNECHIK_ECB;
    public static final RtPkcs11MechanismType CKM_KUZNYECHIK_CTR_ACPKM = CKM_KUZNECHIK_CTR_ACPKM;
    public static final RtPkcs11MechanismType CKM_KUZNYECHIK_MAC = CKM_KUZNECHIK_MAC;
    public static final Pkcs11MechanismType CKM_TLS_GOST_PRE_MASTER_KEY_GEN = CKM_GOST28147_KEY_GEN;

    private static final EnumFromValueHelper<RtPkcs11MechanismType> FROM_VALUE_HELPER = new EnumFromValueHelper<>();
    private final long mValue;

    RtPkcs11MechanismType(long value) {
        mValue = value;
    }

    @Nullable
    public static RtPkcs11MechanismType nullableFromValue(long value) {
        return FROM_VALUE_HELPER.nullableFromValue(value, RtPkcs11MechanismType.class);
    }

    public static RtPkcs11MechanismType fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, RtPkcs11MechanismType.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }

    public static class Factory implements VendorFactory {
        @Nullable
        @Override
        public RtPkcs11MechanismType nullableFromValue(long value) {
            return RtPkcs11MechanismType.nullableFromValue(value);
        }
    }
}
