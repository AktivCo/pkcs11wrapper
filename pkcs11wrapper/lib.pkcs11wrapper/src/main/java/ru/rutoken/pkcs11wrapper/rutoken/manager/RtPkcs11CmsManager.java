package ru.rutoken.pkcs11wrapper.rutoken.manager;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import ru.rutoken.pkcs11jna.RtPkcs11Constants;
import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.manager.impl.BaseManager;
import ru.rutoken.pkcs11wrapper.object.certificate.Pkcs11CertificateObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PrivateKeyObject;
import ru.rutoken.pkcs11wrapper.rutoken.datatype.AttachedCmsVerifyResult;
import ru.rutoken.pkcs11wrapper.rutoken.datatype.DetachedCmsVerifyResult;
import ru.rutoken.pkcs11wrapper.rutoken.datatype.VendorX509Store;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Api;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Session;

public class RtPkcs11CmsManager extends BaseManager {
    public RtPkcs11CmsManager(RtPkcs11Session session) {
        super(session);
    }

    private static long @Nullable [] toCertificateHandlesArray(@Nullable List<Pkcs11CertificateObject> certificates) {
        if (null == certificates)
            return null;

        final long[] certificateHandles = new long[certificates.size()];
        for (int i = 0; i < certificates.size(); i++)
            certificateHandles[i] = certificates.get(i).getHandle();

        return certificateHandles;
    }

    public byte[] sign(byte[] data, Pkcs11CertificateObject signerCertificate, Pkcs11PrivateKeyObject signerPrivateKey,
                       @Nullable List<Pkcs11CertificateObject> additionalCertificates, long flags) {
        return getApi().C_EX_PKCS7Sign(mSession.getSessionHandle(), data, signerCertificate.getHandle(),
                signerPrivateKey.getHandle(), toCertificateHandlesArray(additionalCertificates), flags);
    }

    public void verifyInit(byte[] cms, @Nullable VendorX509Store store, CrlCheckMode mode, long flags) {
        getApi().C_EX_PKCS7VerifyInit(mSession.getSessionHandle(), cms,
                store != null ? store.toCkVendorX509Store(getLowLevelFactory()) : null, mode.getAsLong(), flags);
    }

    public AttachedCmsVerifyResult verify() {
        return getApi().C_EX_PKCS7Verify(mSession.getSessionHandle());
    }

    public AttachedCmsVerifyResult verifyAttachedAtOnce(byte[] cms, @Nullable VendorX509Store store,
                                                        CrlCheckMode mode, long flags) {
        verifyInit(cms, store, mode, flags);
        return verify();
    }

    public AttachedCmsVerifyResult requireVerifyAttachedAtOnce(byte[] cms, @Nullable VendorX509Store store,
                                                               CrlCheckMode mode, long flags) {
        final AttachedCmsVerifyResult result = verifyAttachedAtOnce(cms, store, mode, flags);
        Pkcs11Exception.throwIfNotOk(result.getResult(), "CMS verification failed");
        return result;
    }

    public DetachedCmsVerifyResult verifyDetachedAtOnce(byte[] cms, byte[] data, @Nullable VendorX509Store store,
                                                        CrlCheckMode mode, long flags) {
        verifyInit(cms, store, mode, flags);
        verifyUpdate(data);
        return verifyFinal();
    }

    @Nullable
    public List<byte[]> requireVerifyDetachedAtOnce(byte[] cms, byte[] data, @Nullable VendorX509Store store,
                                                    CrlCheckMode mode, long flags) {
        verifyInit(cms, store, mode, flags);
        verifyUpdate(data);
        return requireVerifyFinal();
    }

    public void verifyUpdate(byte[] data) {
        getApi().C_EX_PKCS7VerifyUpdate(mSession.getSessionHandle(), data);
    }

    public DetachedCmsVerifyResult verifyFinal() {
        return getApi().C_EX_PKCS7VerifyFinal(mSession.getSessionHandle());
    }

    public List<byte[]> requireVerifyFinal() {
        final DetachedCmsVerifyResult result = verifyFinal();
        Pkcs11Exception.throwIfNotOk(result.getResult(), "CMS verification failed");
        return result.getSignerCertificates();
    }

    @Override
    public RtPkcs11Api getApi() {
        return (RtPkcs11Api) super.getApi();
    }

    @Override
    public IRtPkcs11LowLevelFactory getLowLevelFactory() {
        return (IRtPkcs11LowLevelFactory) super.getLowLevelFactory();
    }

    @Override
    public RtPkcs11Session getSession() {
        return (RtPkcs11Session) mSession;
    }

    public enum CrlCheckMode implements LongValueSupplier {
        OPTIONAL_CRL_CHECK(RtPkcs11Constants.OPTIONAL_CRL_CHECK),
        LEAF_CRL_CHECK(RtPkcs11Constants.LEAF_CRL_CHECK),
        ALL_CRL_CHECK(RtPkcs11Constants.ALL_CRL_CHECK);

        private final long mValue;

        CrlCheckMode(long value) {
            mValue = value;
        }

        @Override
        public long getAsLong() {
            return mValue;
        }
    }
}
