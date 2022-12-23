package ru.rutoken.samples.utils;

import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public final class PkiUtils {
    private PkiUtils() {
    }

    public static X509Certificate generateX509Certificate(X509CertificateHolder certificateHolder)
            throws CertificateException, IOException {
        return (X509Certificate) CertificateFactory.getInstance("X.509")
                .generateCertificate(new ByteArrayInputStream(certificateHolder.getEncoded()));
    }

    public static String certificateToPem(byte[] encodedCertificate) throws IOException {
        try (StringWriter stringWriter = new StringWriter();
             JcaPEMWriter pemWriter = new JcaPEMWriter(stringWriter)) {
            pemWriter.writeObject(new X509CertificateHolder(encodedCertificate));
            pemWriter.flush();
            return stringWriter.toString();
        }
    }

    public static String cmsToPem(byte[] encodedCms) throws IOException {
        try (StringWriter stringWriter = new StringWriter();
             JcaPEMWriter pemWriter = new JcaPEMWriter(stringWriter)) {
            pemWriter.writeObject(ContentInfo.getInstance(encodedCms));
            pemWriter.flush();
            return stringWriter.toString();
        }
    }
}
