/*
 * Copyright (c) 2022, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.samples.findobjects;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509CertificateHolder;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PublicKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11RsaPublicKeyObject;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Session;
import ru.rutoken.samples.findobjects.Algorithm.*;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.*;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.*;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType.*;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyType.CKK_GOSTR3410_512;
import static ru.rutoken.samples.utils.Constants.GOSTR3411_1994_OID;
import static ru.rutoken.samples.utils.Constants.GOSTR3411_2012_256_OID;
import static ru.rutoken.samples.utils.PkiUtils.generateX509Certificate;
import static ru.rutoken.samples.utils.Utils.*;

final class Utils {
    private Utils() {
    }

    @Nullable
    public static Pkcs11PublicKeyObject findPublicKeyByCertificate(RtPkcs11Session session,
                                                                   X509CertificateHolder certificateHolder)
            throws CertificateException, IOException {
        final var certificate = generateX509Certificate(certificateHolder);
        return certificate.getPublicKey() instanceof RSAPublicKey ? findRsaPublicKeyByCertificate(session, certificate)
                : findGostPublicKeyByCertificate(session, certificateHolder);
    }

    @Nullable
    public static Algorithm getAlgorithm(Pkcs11PublicKeyObject publicKey, RtPkcs11Session session) {
        final var keyType = publicKey.getKeyTypeAttributeValue(session).getEnumValue(session);

        if (keyType == CKK_GOSTR3410) {
            final var params = publicKey.getByteArrayAttributeValue(session, CKA_GOSTR3411_PARAMS).getByteArrayValue();
            return getGost256Algorithm(params);
        } else if (keyType == CKK_EC) {
            return new EcdsaAlgorithm();
        } else if (keyType == CKK_GOSTR3410_512) {
            return new GostAlgorithm2012_512();
        } else if (keyType == CKK_RSA) {
            final var modulusBits = publicKey.getLongAttributeValue(session, CKA_MODULUS_BITS).getLongValue();
            return new RsaAlgorithm(modulusBits);
        }

        return null;
    }

    public static void printInfo(String type, byte[] id, Algorithm algorithm, X509CertificateHolder certificate) {
        println("Container type: " + type);
        if (id != null) printHex("Identifier:", id);
        if (algorithm != null) println("Algorithm: " + algorithm);
        if (certificate != null) {
            println("Owner: " + getOwner(certificate));
            println("Organization: " + getSubjectRdnValue(certificate, BCStyle.O));
            println("Expires: " + toTimeString(certificate.getNotAfter()));
        }
    }

    @Nullable
    private static Pkcs11RsaPublicKeyObject findRsaPublicKeyByCertificate(RtPkcs11Session session,
                                                                          X509Certificate certificate) {
        final var objects = session.getObjectManager()
                .findObjectsAtOnce(Pkcs11RsaPublicKeyObject.class, createRsaPublicKeyTemplate(certificate));
        return (objects.size() == 1) ? objects.get(0) : null;
    }

    @Nullable
    private static Pkcs11PublicKeyObject findGostPublicKeyByCertificate(RtPkcs11Session session,
                                                                        X509CertificateHolder certificateHolder)
            throws IOException {
        final var template = new ArrayList<Pkcs11Attribute>();
        template.add(new Pkcs11ByteArrayAttribute(CKA_VALUE, getGostPublicKeyValue(certificateHolder)));

        final var objects = session.getObjectManager().findObjectsAtOnce(Pkcs11PublicKeyObject.class, template);
        return (objects.size() == 1) ? objects.get(0) : null;
    }

    private static byte[] getGostPublicKeyValue(X509CertificateHolder certificateHolder) throws IOException {
        final var keyValue = certificateHolder.getSubjectPublicKeyInfo().parsePublicKey().getEncoded();

        // Remove the key header (see ASN.1 Basic Encoding Rules)
        var pos = 2;
        if ((keyValue[1] & (1 << 7)) != 0) {
            pos += keyValue[1] & (0xFF >> 1);
        }

        return Arrays.copyOfRange(keyValue, pos, keyValue.length);
    }

    private static List<Pkcs11Attribute> createRsaPublicKeyTemplate(X509Certificate certificate) {
        final var publicKey = certificate.getPublicKey();
        if (!(publicKey instanceof RSAPublicKey))
            throw new IllegalStateException("Public key extracted from certificate is not an RSA key");

        final var rsaPublicKey = (RSAPublicKey) publicKey;
        final var modulus = rsaPublicKey.getModulus();
        final var publicExp = rsaPublicKey.getPublicExponent();

        if (modulus.signum() < 0 && publicExp.signum() < 0)
            throw new IllegalStateException("Modulus or public exponent is less than zero");

        return Arrays.asList(
                new Pkcs11ByteArrayAttribute(CKA_MODULUS, dropPrecedingZeros(modulus.toByteArray())),
                new Pkcs11ByteArrayAttribute(CKA_PUBLIC_EXPONENT, publicExp.toByteArray())
        );
    }

    @Nullable
    private static Algorithm getGost256Algorithm(byte[] gostR3411ParamsValue) {
        if (Arrays.equals(gostR3411ParamsValue, GOSTR3411_1994_OID))
            return new GostAlgorithm2001();
        else if (Arrays.equals(gostR3411ParamsValue, GOSTR3411_2012_256_OID))
            return new GostAlgorithm2012_256();

        return null;
    }

    private static String getOwner(X509CertificateHolder certificateHolder) {
        final var cn = getSubjectRdnValue(certificateHolder, BCStyle.CN);
        final var surname = getSubjectRdnValue(certificateHolder, BCStyle.SURNAME);
        final var givenName = getSubjectRdnValue(certificateHolder, BCStyle.GIVENNAME);

        final var hasFullName = surname != null && givenName != null;

        if (!hasFullName && cn == null)
            throw new IllegalStateException("Suitable RDNs are not found");

        return hasFullName ? surname + " " + givenName : cn;
    }

    @Nullable
    private static String getSubjectRdnValue(X509CertificateHolder certificateHolder, ASN1ObjectIdentifier type) {
        final var rdnOptional = Arrays.stream(certificateHolder.getSubject().getRDNs())
                .filter(it -> it.getFirst().getType().equals(type))
                .findFirst();

        return rdnOptional.map(rdn -> rdn.getFirst().getValue().toString()).orElse(null);
    }

    private static String toTimeString(Date date) {
        return new SimpleDateFormat("d MMMM yyyy, HH:mm:ss", Locale.getDefault()).format(date);
    }
}
