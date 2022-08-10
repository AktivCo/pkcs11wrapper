package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface CkVendorX509Store {
    @Nullable
    List<byte[]> getTrustedCertificates();

    void setTrustedCertificates(@Nullable List<byte[]> trustedCertificates);

    @Nullable
    List<byte[]> getCertificates();

    void setCertificates(@Nullable List<byte[]> certificates);

    @Nullable
    List<byte[]> getCrls();

    void setCrls(@Nullable List<byte[]> crls);
}
