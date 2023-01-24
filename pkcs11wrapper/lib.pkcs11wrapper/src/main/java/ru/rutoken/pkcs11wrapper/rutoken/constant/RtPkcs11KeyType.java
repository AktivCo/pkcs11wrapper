/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.constant;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType.CKK_GOSTR3410;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11jna.Pkcs11Tc26Constants;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11KeyType;
import ru.rutoken.pkcs11wrapper.constant.standard.EnumFromValueHelper;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType;

public enum RtPkcs11KeyType implements IPkcs11KeyType {
    CKK_GOSTR3410_512(Pkcs11Tc26Constants.CKK_GOSTR3410_512),
    CKK_KUZNECHIK(Pkcs11Tc26Constants.CKK_KUZNECHIK),
    CKK_MAGMA(Pkcs11Tc26Constants.CKK_MAGMA),
    CKK_KUZNECHIK_TWIN_KEY(Pkcs11Tc26Constants.CKK_KUZNECHIK_TWIN_KEY),
    CKK_MAGMA_TWIN_KEY(Pkcs11Tc26Constants.CKK_MAGMA_TWIN_KEY);

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
