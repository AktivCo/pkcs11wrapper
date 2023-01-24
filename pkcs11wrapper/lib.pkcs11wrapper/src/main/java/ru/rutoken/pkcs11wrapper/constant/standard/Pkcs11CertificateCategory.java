/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant.standard;

import ru.rutoken.pkcs11wrapper.constant.AttributeLongValueSupplier;

public enum Pkcs11CertificateCategory implements AttributeLongValueSupplier {
    CK_CERTIFICATE_CATEGORY_UNSPECIFIED(0L),
    CK_CERTIFICATE_CATEGORY_TOKEN_USER(1L),
    CK_CERTIFICATE_CATEGORY_AUTHORITY(2L),
    CK_CERTIFICATE_CATEGORY_OTHER_ENTITY(3L);

    private static final EnumFromValueHelper<Pkcs11CertificateCategory> FROM_VALUE_HELPER = new EnumFromValueHelper<>();
    private final long mValue;

    Pkcs11CertificateCategory(long value) {
        mValue = value;
    }

    public static Pkcs11CertificateCategory fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, Pkcs11CertificateCategory.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }
}