package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.fake;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorX509Store;

class FakeCkVendorX509StoreImpl implements CkVendorX509Store {
    @Nullable
    private List<byte[]> mTrustedCertificates = null;
    @Nullable
    private List<byte[]> mCertificates = null;
    @Nullable
    private List<byte[]> mCrls = null;

    public FakeCkVendorX509StoreImpl() {
    }

    public FakeCkVendorX509StoreImpl(@Nullable List<byte[]> trustedCertificates, @Nullable List<byte[]> certificates,
                                     @Nullable List<byte[]> crls) {
        mTrustedCertificates = trustedCertificates;
        mCertificates = certificates;
        mCrls = crls;
    }

    @Override
    @Nullable
    public List<byte[]> getTrustedCertificates() {
        return mTrustedCertificates;
    }

    @Override
    public void setTrustedCertificates(@Nullable List<byte[]> trustedCertificates) {
        mTrustedCertificates = trustedCertificates;
    }

    @Override
    @Nullable
    public List<byte[]> getCertificates() {
        return mCertificates;
    }

    @Override
    public void setCertificates(@Nullable List<byte[]> certificates) {
        mCertificates = certificates;
    }

    @Override
    @Nullable
    public List<byte[]> getCrls() {
        return mCrls;
    }

    @Override
    public void setCrls(@Nullable List<byte[]> crls) {
        mCrls = crls;
    }
}
