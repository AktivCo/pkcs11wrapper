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

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11jna.Pkcs11Tc26Constants;
import ru.rutoken.pkcs11jna.RtPkcs11Constants;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.constant.standard.EnumFromValueHelper;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType;

public enum RtPkcs11MechanismType implements IPkcs11MechanismType {
    CKM_GOSTR3410_512_KEY_PAIR_GEN(Pkcs11Tc26Constants.CKM_GOSTR3410_512_KEY_PAIR_GEN),
    CKM_GOSTR3410_512(Pkcs11Tc26Constants.CKM_GOSTR3410_512),
    CKM_GOSTR3410_12_DERIVE(Pkcs11Tc26Constants.CKM_GOSTR3410_12_DERIVE),
    CKM_GOSTR3410_WITH_GOSTR3411_12_256(Pkcs11Tc26Constants.CKM_GOSTR3410_WITH_GOSTR3411_12_256),
    CKM_GOSTR3410_WITH_GOSTR3411_12_512(Pkcs11Tc26Constants.CKM_GOSTR3410_WITH_GOSTR3411_12_512),
    CKM_GOSTR3410_PUBLIC_KEY_DERIVE(Pkcs11Tc26Constants.CKM_GOSTR3410_PUBLIC_KEY_DERIVE),
    CKM_GOSTR3410_512_PUBLIC_KEY_DERIVE(Pkcs11Tc26Constants.CKM_GOSTR3410_512_PUBLIC_KEY_DERIVE),

    CKM_GOSTR3411_12_256(Pkcs11Tc26Constants.CKM_GOSTR3411_12_256),
    CKM_GOSTR3411_12_512(Pkcs11Tc26Constants.CKM_GOSTR3411_12_512),
    CKM_GOSTR3411_12_256_HMAC(Pkcs11Tc26Constants.CKM_GOSTR3411_12_256_HMAC),
    CKM_GOSTR3411_12_512_HMAC(Pkcs11Tc26Constants.CKM_GOSTR3411_12_512_HMAC),

    CKM_TLS_GOST_PRF_2012_256(Pkcs11Tc26Constants.CKM_TLS_GOST_PRF_2012_256),
    CKM_TLS_GOST_PRF_2012_512(Pkcs11Tc26Constants.CKM_TLS_GOST_PRF_2012_512),
    CKM_TLS_GOST_MASTER_KEY_DERIVE_2012_256(Pkcs11Tc26Constants.CKM_TLS_GOST_MASTER_KEY_DERIVE_2012_256),

    CKM_KDF_4357(Pkcs11Tc26Constants.CKM_KDF_4357),
    CKM_KDF_GOSTR3411_2012_256(Pkcs11Tc26Constants.CKM_KDF_GOSTR3411_2012_256),

    CKM_KDF_HMAC3411_2012_256(Pkcs11Tc26Constants.CKM_KDF_HMAC3411_2012_256),
    CKM_KDF_TREE_GOSTR3411_2012_256(Pkcs11Tc26Constants.CKM_KDF_TREE_GOSTR3411_2012_256),

    CKM_KUZNECHIK_KEXP_15_WRAP(Pkcs11Tc26Constants.CKM_KUZNECHIK_KEXP_15_WRAP),
    CKM_MAGMA_KEXP_15_WRAP(Pkcs11Tc26Constants.CKM_MAGMA_KEXP_15_WRAP),
    CKM_KUZNECHIK_MGM(Pkcs11Tc26Constants.CKM_KUZNECHIK_MGM),
    CKM_MAGMA_MGM(Pkcs11Tc26Constants.CKM_MAGMA_MGM),

    CKM_KUZNECHIK_KEY_GEN(Pkcs11Tc26Constants.CKM_KUZNECHIK_KEY_GEN),
    CKM_KUZNECHIK_ECB(Pkcs11Tc26Constants.CKM_KUZNECHIK_ECB),
    CKM_KUZNECHIK_CTR_ACPKM(Pkcs11Tc26Constants.CKM_KUZNECHIK_CTR_ACPKM),
    CKM_KUZNECHIK_MAC(Pkcs11Tc26Constants.CKM_KUZNECHIK_MAC),

    CKM_MAGMA_KEY_GEN(Pkcs11Tc26Constants.CKM_MAGMA_KEY_GEN),
    CKM_MAGMA_ECB(Pkcs11Tc26Constants.CKM_MAGMA_ECB),
    CKM_MAGMA_CTR_ACPKM(Pkcs11Tc26Constants.CKM_MAGMA_CTR_ACPKM),
    CKM_MAGMA_MAC(Pkcs11Tc26Constants.CKM_MAGMA_MAC),

    CKM_VENDOR_SECURE_IMPORT(RtPkcs11Constants.CKM_VENDOR_SECURE_IMPORT),
    CKM_VKO_GOSTR3410_2012_512(Pkcs11Tc26Constants.CKM_VKO_GOSTR3410_2012_512),
    CKM_GOST_KEG(Pkcs11Tc26Constants.CKM_GOST_KEG);

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
