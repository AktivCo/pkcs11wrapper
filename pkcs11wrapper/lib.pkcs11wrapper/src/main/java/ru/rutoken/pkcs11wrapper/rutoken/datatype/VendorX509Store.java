package ru.rutoken.pkcs11wrapper.rutoken.datatype;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorX509Store;

public class VendorX509Store {
    @Nullable
    private final List<byte[]> mTrustedCertificates;
    @Nullable
    private final List<byte[]> mCertificates;
    @Nullable
    private final List<byte[]> mCrls;

    public VendorX509Store(@Nullable List<byte[]> trustedCertificates, @Nullable List<byte[]> certificates,
                           @Nullable List<byte[]> crls) {
        mTrustedCertificates = trustedCertificates;
        mCertificates = certificates;
        mCrls = crls;
    }

    @Nullable
    public List<byte[]> getTrustedCertificates() {
        return mTrustedCertificates;
    }

    @Nullable
    public List<byte[]> getCertificates() {
        return mCertificates;
    }

    @Nullable
    public List<byte[]> getCrls() {
        return mCrls;
    }

    public CkVendorX509Store toCkVendorX509Store(IRtPkcs11LowLevelFactory factory) {
        final CkVendorX509Store store = factory.makeVendorX509Store();
        store.setTrustedCertificates(mTrustedCertificates);
        store.setCertificates(mCertificates);
        store.setCrls(mCrls);
        return store;
    }
}
