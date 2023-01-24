/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant.standard;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11jna.Pkcs11Constants;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11CertificateType;

@SuppressWarnings("SpellCheckingInspection")
public enum Pkcs11CertificateType implements IPkcs11CertificateType {
    CKC_X_509(Pkcs11Constants.CKC_X_509),
    CKC_X_509_ATTR_CERT(Pkcs11Constants.CKC_X_509_ATTR_CERT),
    CKC_WTLS(Pkcs11Constants.CKC_WTLS),
    CKC_VENDOR_DEFINED(Pkcs11Constants.CKC_VENDOR_DEFINED);

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
