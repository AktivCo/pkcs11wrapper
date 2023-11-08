/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.manager;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.object.certificate.Pkcs11CertificateObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PrivateKeyObject;
import ru.rutoken.pkcs11wrapper.rutoken.datatype.AttachedCmsVerifyResult;
import ru.rutoken.pkcs11wrapper.rutoken.datatype.DetachedCmsVerifyResult;
import ru.rutoken.pkcs11wrapper.rutoken.datatype.VendorX509Store;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Session;

public class RtPkcs11CmsManager extends RtBaseManager {
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

    public enum CrlCheckMode implements LongValueSupplier {
        /**
         * If we have no suitable CRL - it won't be an error.
         */
        OPTIONAL_CRL_CHECK(0x00000000L),
        /**
         * Signer's CA CRL should be passed.
         */
        LEAF_CRL_CHECK(0x00000001L),
        /**
         * CRLs of all CA from the chain should be passed.
         */
        ALL_CRL_CHECK(0x00000002L);

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
