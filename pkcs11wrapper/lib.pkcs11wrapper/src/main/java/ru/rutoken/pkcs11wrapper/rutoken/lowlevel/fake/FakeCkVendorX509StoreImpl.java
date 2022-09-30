package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.fake;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorX509Store;

class FakeCkVendorX509StoreImpl implements CkVendorX509Store {
    public FakeCkVendorX509StoreImpl() {
    }

    @Override
    public void setTrustedCertificates(@Nullable List<byte[]> trustedCertificates) {
    }

    @Override
    public void setCertificates(@Nullable List<byte[]> certificates) {
    }

    @Override
    public void setCrls(@Nullable List<byte[]> crls) {
    }
}
