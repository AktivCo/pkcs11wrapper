/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.constant;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType.CKK_GOSTR3410;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType.CKK_VENDOR_DEFINED;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.internal.RtPkcs11InternalConstants.CK_VENDOR_PKCS11_RU_TEAM_TC26;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11KeyType;
import ru.rutoken.pkcs11wrapper.constant.standard.EnumFromValueHelper;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType;

public enum RtPkcs11KeyType implements IPkcs11KeyType {
    CKK_GOSTR3410_512(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x003L),
    CKK_KUZNECHIK(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x004L),
    CKK_MAGMA(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x005L),
    CKK_KUZNECHIK_TWIN_KEY(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x006L),
    CKK_MAGMA_TWIN_KEY(CK_VENDOR_PKCS11_RU_TEAM_TC26 | 0x007L),

    CKK_VENDOR_BIP32(CKK_VENDOR_DEFINED.getAsLong() + 2L),

    // backported define from pkcs3.0 header required for eddsa support
    CKK_EC_EDWARDS(0x00000040L);

    public static final Pkcs11KeyType CKK_GOSTR3410_256 = CKK_GOSTR3410;
    public static final RtPkcs11KeyType CKK_KUZNYECHIK = CKK_KUZNECHIK;
    public static final RtPkcs11KeyType CKK_KUZNYECHIK_TWIN_KEY = CKK_KUZNECHIK_TWIN_KEY;

    private static final EnumFromValueHelper<RtPkcs11KeyType> FROM_VALUE_HELPER = new EnumFromValueHelper<>();
    private final long mValue;

    RtPkcs11KeyType(long value) {
        mValue = value;
    }

    @Nullable
    public static RtPkcs11KeyType nullableFromValue(long value) {
        return FROM_VALUE_HELPER.nullableFromValue(value, RtPkcs11KeyType.class);
    }

    public static RtPkcs11KeyType fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, RtPkcs11KeyType.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }

    public static class Factory implements IPkcs11KeyType.VendorFactory {
        @Nullable
        @Override
        public RtPkcs11KeyType nullableFromValue(long value) {
            return RtPkcs11KeyType.nullableFromValue(value);
        }
    }
}
