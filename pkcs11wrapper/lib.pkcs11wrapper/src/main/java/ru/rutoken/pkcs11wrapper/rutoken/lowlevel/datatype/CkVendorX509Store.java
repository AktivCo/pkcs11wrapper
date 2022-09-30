package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface CkVendorX509Store {
    void setTrustedCertificates(@Nullable List<byte[]> trustedCertificates);

    void setCertificates(@Nullable List<byte[]> certificates);

    void setCrls(@Nullable List<byte[]> crls);
}
