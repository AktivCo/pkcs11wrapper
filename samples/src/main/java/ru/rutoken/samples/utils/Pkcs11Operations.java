/*
 * Copyright (c) 2022, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.samples.utils;

import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11InitializeArgs;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11KeyPair;
import ru.rutoken.pkcs11wrapper.object.Pkcs11StorageObject;
import ru.rutoken.pkcs11wrapper.object.certificate.Pkcs11CertificateObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PublicKeyObject;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Session;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Token;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_RSA_PKCS_KEY_PAIR_GEN;
import static ru.rutoken.samples.utils.Pkcs11ObjectTemplates.*;
import static ru.rutoken.samples.utils.Utils.*;

public final class Pkcs11Operations {
    private Pkcs11Operations() {
    }

    public static RtPkcs11Token initializePkcs11AndGetFirstToken(RtPkcs11Module module) {
        module.initializeModule(new Pkcs11InitializeArgs.Builder().setOsLockingOk(true).build());

        final var slots = module.getSlotList(true);
        if (slots.size() == 0)
            throw new IllegalStateException("Rutoken is not found");

        return (RtPkcs11Token) slots.get(0).getToken();
    }

    public static Pkcs11CertificateObject findCertificateById(RtPkcs11Session session, byte[] id) {
        final var certificateTemplate = makeCertificateTemplate(session.getAttributeFactory(), id);
        // For simplicity, we find first object matching template, in production you should generally check that
        // only single object matches template.
        return findFirstObject(session, Pkcs11CertificateObject.class, certificateTemplate);
    }

    public static <T extends Pkcs11StorageObject> T findFirstObject(RtPkcs11Session session, Class<T> clazz,
                                                                    List<Pkcs11Attribute> template) {
        final var objects = session.getObjectManager().findObjectsAtOnce(clazz, template);
        if (objects.size() < 1)
            throw new IllegalStateException(clazz.getSimpleName() + " object not found");
        return objects.get(0);
    }

    public static Pkcs11PrivateKeyObject findPrivateKeyByCertificateValue(RtPkcs11Session session,
                                                                          byte[] certificateValue)
            throws CertificateException {
        return findKeyPairByCertificateValue(session, certificateValue).getPrivateKey();
    }

    public static Pkcs11KeyPair<Pkcs11PublicKeyObject, Pkcs11PrivateKeyObject> findKeyPairByCertificateValue(
            RtPkcs11Session session, byte[] certificateValue) throws CertificateException {
        // Find corresponding public key handle for certificate
        final var x509certificate = (X509Certificate) CertificateFactory.getInstance("X.509")
                .generateCertificate(new ByteArrayInputStream(certificateValue));

        final List<Pkcs11Attribute> publicKeyValueTemplate;
        if (x509certificate.getPublicKey() instanceof RSAPublicKey) {
            RSAPublicKey publicKey = (RSAPublicKey) x509certificate.getPublicKey();

            publicKeyValueTemplate = makeRsaPublicKeyTemplate(
                    session.getAttributeFactory(),
                    dropPrecedingZeros(publicKey.getModulus().toByteArray()),
                    publicKey.getPublicExponent().toByteArray()
            );
        } else { // GOST
            final var sequence = ASN1Sequence.getInstance(x509certificate.getPublicKey().getEncoded());
            final var publicKeyValue =
                    ASN1OctetString.getInstance(((ASN1BitString) sequence.getObjectAt(1)).getOctets()).getOctets();

            publicKeyValueTemplate = makeGostPublicKeyBaseTemplate(session.getAttributeFactory(), publicKeyValue);
        }

        // For simplicity, we find first object matching template, in production you should generally check that
        // only single object matches template.
        final var publicKey = findFirstObject(session, Pkcs11PublicKeyObject.class, publicKeyValueTemplate);

        // Using public key we can find private key handle
        final var publicKeyId = publicKey.getIdAttributeValue(session).getByteArrayValue();
        final var privateKeyTemplate = makePrivateKeyBaseTemplate(session.getAttributeFactory(), publicKeyId);

        // For simplicity, we find first object matching template, in production you should generally check that
        // only single object matches template.
        final var privateKey = findFirstObject(session, Pkcs11PrivateKeyObject.class, privateKeyTemplate);

        return new Pkcs11KeyPair<>(publicKey, privateKey);
    }

    /**
     * This method checks if there are some mechanisms in {@param mechanisms} that are not supported by {@param token}
     * and returns all such mechanisms.
     */
    public static List<IPkcs11MechanismType> getUnsupportedMechanisms(RtPkcs11Token token,
                                                                      IPkcs11MechanismType... mechanisms) {
        final var result = new ArrayList<IPkcs11MechanismType>();
        for (var mechanism : mechanisms) {
            if (!isMechanismSupported(token, mechanism))
                result.add(mechanism);
        }
        return result;
    }

    public static boolean isRsaModulusSupported(RtPkcs11Token token, int modulusBits) {
        final var info = token.getMechanismInfo(CKM_RSA_PKCS_KEY_PAIR_GEN);
        return (modulusBits >= info.getMinKeySize()) && (modulusBits <= info.getMaxKeySize());
    }

    private static boolean isMechanismSupported(RtPkcs11Token token, IPkcs11MechanismType mechanism) {
        final var mechanismList = token.getMechanismList();
        return mechanismList.stream().anyMatch(it -> it.getAsLong() == mechanism.getAsLong());
    }
}
