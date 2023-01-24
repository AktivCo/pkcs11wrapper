/*
 * Copyright (c) 2022, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.samples.utils;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Simple implementation of Certification Authority (CA) that issues certificates by CSR
 */
public class GostDemoCA {
    private static final int YEARS = 1;

    /**
     * Expires in 2045.
     */
    private static final String CA_CERTIFICATE
            = "MIIBTzCB+wIJAMGuFHcbok4sMAwGCCqFAwcBAQMCBQAwKzELMAkGA1UEBhMCUlUx"
            + "CzAJBgNVBAMMAkNBMQ8wDQYDVQQIDAZNb3Njb3cwHhcNMTgwNjA2MTAyNzA4WhcN"
            + "NDUxMDIyMTAyNzA4WjArMQswCQYDVQQGEwJSVTELMAkGA1UEAwwCQ0ExDzANBgNV"
            + "BAgMBk1vc2NvdzBmMB8GCCqFAwcBAQEBMBMGByqFAwICIwEGCCqFAwcBAQICA0MA"
            + "BECM6iQnPgDs6K2jmUVLHf4V63xwO2j4vO2X2kNQVELu2bROK+wBaNWkTX5TW+IO"
            + "9gLZFioYMSEK2LxsIO3Zf+JeMAwGCCqFAwcBAQMCBQADQQATx6Ksy1KUuvfa2q8X"
            + "kfo3pDN1x1aGo4AmQolzEpbXvzbyMy3vk+VOqegdd8KP4E3x43zaTmHmnu/G1v20"
            + "VzwO";

    private static final String CA_PRIVATE_KEY
            = "MEgCAQAwHwYIKoUDBwEBAQEwEwYHKoUDAgIjAQYIKoUDBwEBAgIEIgQgHZQED2H6"
            + "/QSaiwT1uLTmx9S6dK48xAnrT5/xc/R0+P0=";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private GostDemoCA() {
    }

    private static Date addYearsToDate(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

    public static byte[] issueCertificate(byte[] csr)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, OperatorCreationException {
        X509CertificateHolder caCertificateHolder = new X509CertificateHolder(getRootCertificate());

        PKCS8EncodedKeySpec caPrivateKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(CA_PRIVATE_KEY));
        KeyFactory keyFactory = KeyFactory.getInstance("ECGOST3410-2012");
        PrivateKey caPrivateKey = keyFactory.generatePrivate(caPrivateKeySpec);

        PKCS10CertificationRequest certificationRequest = new PKCS10CertificationRequest(csr);
        Date notBefore = new Date();
        Date notAfter = addYearsToDate(notBefore, YEARS);

        X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(
                caCertificateHolder.getIssuer(),
                new BigInteger(160, new Random()).setBit(0),
                notBefore,
                notAfter,
                certificationRequest.getSubject(),
                certificationRequest.getSubjectPublicKeyInfo()
        );

        ContentSigner signer = new JcaContentSignerBuilder("GOST3411-2012-256WITHECGOST3410-2012-256")
                .build(caPrivateKey);

        return certificateBuilder.build(signer).getEncoded();
    }

    public static byte[] getRootCertificate() {
        return Base64.getDecoder().decode(CA_CERTIFICATE);
    }
}
