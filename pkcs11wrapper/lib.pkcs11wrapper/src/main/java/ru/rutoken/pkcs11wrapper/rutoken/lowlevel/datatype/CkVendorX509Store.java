/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface CkVendorX509Store {
    void setTrustedCertificates(@Nullable List<byte[]> trustedCertificates);

    void setCertificates(@Nullable List<byte[]> certificates);

    void setCrls(@Nullable List<byte[]> crls);
}
