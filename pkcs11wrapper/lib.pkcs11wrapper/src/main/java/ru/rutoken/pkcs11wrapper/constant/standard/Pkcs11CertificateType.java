/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant.standard;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11CertificateType;

@SuppressWarnings("SpellCheckingInspection")
public enum Pkcs11CertificateType implements IPkcs11CertificateType {
    CKC_X_509(0x00000000L),
    CKC_X_509_ATTR_CERT(0x00000001L),
    CKC_WTLS(0x00000002L),
    CKC_VENDOR_DEFINED(0x80000000L);

    private static final EnumFromValueHelper<Pkcs11CertificateType> FROM_VALUE_HELPER = new EnumFromValueHelper<>();
    private final long mValue;

    Pkcs11CertificateType(long value) {
        mValue = value;
    }

    @Nullable
    public static Pkcs11CertificateType nullableFromValue(long value) {
        return FROM_VALUE_HELPER.nullableFromValue(value, Pkcs11CertificateType.class);
    }

    public static Pkcs11CertificateType fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, Pkcs11CertificateType.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }
}
