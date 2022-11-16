package ru.rutoken.samples.findObjects;

import org.bouncycastle.cert.X509CertificateHolder;

abstract class Container {
    private final byte[] mId;

    Container(byte[] id) {
        mId = id;
    }

    public byte[] getId() {
        return mId;
    }

    public abstract void printInfo();

    static final class CertificateAndKeyPairContainer extends Container {
        private final byte[] mKeyId; // key ID may differ from certificate ID
        private final X509CertificateHolder mCertificate;
        private final Algorithm mAlgorithm;

        public CertificateAndKeyPairContainer(byte[] certificateId, byte[] keyId, X509CertificateHolder certificate,
                                              Algorithm algorithm) {
            super(certificateId);
            mKeyId = keyId;
            mCertificate = certificate;
            mAlgorithm = algorithm;
        }

        public byte[] getKeyId() {
            return mKeyId;
        }

        public X509CertificateHolder getCertificate() {
            return mCertificate;
        }

        public Algorithm getAlgorithm() {
            return mAlgorithm;
        }

        @Override
        public void printInfo() {
            Utils.printInfo("Certificate with key pair", getId(), mAlgorithm, mCertificate);
        }
    }

    static final class CertificateContainer extends Container {
        private final X509CertificateHolder mCertificate;

        public CertificateContainer(byte[] certificateId, X509CertificateHolder certificate) {
            super(certificateId);
            mCertificate = certificate;
        }

        public X509CertificateHolder getCertificate() {
            return mCertificate;
        }

        @Override
        public void printInfo() {
            Utils.printInfo("Certificate", getId(), null, mCertificate);
        }
    }

    static final class KeyPairContainer extends Container {
        private final Algorithm mAlgorithm;

        public KeyPairContainer(byte[] keyId, Algorithm algorithm) {
            super(keyId);
            mAlgorithm = algorithm;
        }

        public Algorithm getAlgorithm() {
            return mAlgorithm;
        }

        @Override
        public void printInfo() {
            Utils.printInfo("Key pair", getId(), mAlgorithm, null);
        }
    }
}
