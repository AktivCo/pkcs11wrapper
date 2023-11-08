/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant.standard;

import org.jetbrains.annotations.Nullable;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11ObjectClass;

public enum Pkcs11ObjectClass implements IPkcs11ObjectClass {
    CKO_DATA(0x00000000L),
    CKO_CERTIFICATE(0x00000001L),
    CKO_PUBLIC_KEY(0x00000002L),
    CKO_PRIVATE_KEY(0x00000003L),
    CKO_SECRET_KEY(0x00000004L),
    CKO_HW_FEATURE(0x00000005L),
    CKO_DOMAIN_PARAMETERS(0x00000006L),
    CKO_MECHANISM(0x00000007L),
    CKO_OTP_KEY(0x00000008L),

    CKO_VENDOR_DEFINED(0x80000000L);

    private static final EnumFromValueHelper<Pkcs11ObjectClass> FROM_VALUE_HELPER = new EnumFromValueHelper<>();
    private final long mValue;

    Pkcs11ObjectClass(long value) {
        mValue = value;
    }

    @Nullable
    public static Pkcs11ObjectClass nullableFromValue(long value) {
        return FROM_VALUE_HELPER.nullableFromValue(value, Pkcs11ObjectClass.class);
    }

    public static Pkcs11ObjectClass fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, Pkcs11ObjectClass.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }
}
