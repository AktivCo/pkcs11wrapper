package ru.rutoken.samples.signverify;

import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;

import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11KeyPair;
import ru.rutoken.pkcs11wrapper.object.Pkcs11StorageObject;
import ru.rutoken.pkcs11wrapper.object.certificate.Pkcs11CertificateObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PublicKeyObject;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Session;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import static ru.rutoken.pkcs11jna.Pkcs11Constants.*;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_CERTIFICATE_CATEGORY;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_CERTIFICATE_TYPE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_CLASS;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_ID;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_VALUE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11CertificateCategory.CK_CERTIFICATE_CATEGORY_TOKEN_USER;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11CertificateType.CKC_X_509;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ObjectClass.CKO_CERTIFICATE;
import static ru.rutoken.samples.utils.Utils.printHex;
import static ru.rutoken.samples.utils.Utils.println;

final class Utils {
    private Utils() {
    }

    public static Pkcs11CertificateObject findCertificateById(RtPkcs11Session session, byte[] id) {
        final var certificateTemplate = makeCertificateTemplate(session.getAttributeFactory(), id);
        // For simplicity, we find first object matching template, in production you should generally check that
        // only single object matches template.
        return findFirstObject(session, Pkcs11CertificateObject.class, certificateTemplate);
    }

    public static Pkcs11PrivateKeyObject findPrivateKeyByCertificateValue(RtPkcs11Session session,
                                                                          byte[] certificateValue)
            throws CertificateException {
        return findKeyPairByCertificateValue(session, certificateValue).getPrivateKey();
    }

    private static <T extends Pkcs11StorageObject> T findFirstObject(RtPkcs11Session session, Class<T> clazz,
                                                                     List<Pkcs11Attribute> template) {
        final var objects = session.getObjectManager().findObjectsAtOnce(clazz, template);
        if (objects.size() < 1)
            throw new IllegalStateException(clazz.getSimpleName() + " object not found");
        return objects.get(0);
    }

    private static Pkcs11KeyPair<Pkcs11PublicKeyObject, Pkcs11PrivateKeyObject> findKeyPairByCertificateValue(
            RtPkcs11Session session, byte[] certificateValue) throws CertificateException {
        // Find corresponding public key handle for certificate
        println("Parsing X.509 certificate");
        final var x509certificate = (X509Certificate) CertificateFactory.getInstance("X.509")
                .generateCertificate(new ByteArrayInputStream(certificateValue));

        println("Decode public key from ASN.1 structure");
        final var sequence = ASN1Sequence.getInstance(x509certificate.getPublicKey().getEncoded());
        final var publicKeyValue =
                ASN1OctetString.getInstance(((ASN1BitString) sequence.getObjectAt(1)).getOctets()).getOctets();
        printHex("Public key value:", publicKeyValue);

        println("Finding public key by value");
        final var publicKeyValueTemplate = makePublicKeyTemplate(session.getAttributeFactory(), publicKeyValue);
        // For simplicity, we find first object matching template, in production you should generally check that
        // only single object matches template.
        final var publicKey = findFirstObject(session, Pkcs11PublicKeyObject.class, publicKeyValueTemplate);

        // Using public key we can find private key handle
        println("Getting public key ID");
        final var publicKeyId = publicKey.getIdAttributeValue(session).getByteArrayValue();

        println("Finding private key by public key ID");
        final var privateKeyTemplate = makePrivateKeyTemplate(session.getAttributeFactory(), publicKeyId);
        // For simplicity, we find first object matching template, in production you should generally check that
        // only single object matches template.
        final var privateKey = findFirstObject(session, Pkcs11PrivateKeyObject.class, privateKeyTemplate);

        return new Pkcs11KeyPair<>(publicKey, privateKey);
    }

    private static List<Pkcs11Attribute> makeCertificateTemplate(IPkcs11AttributeFactory attributeFactory, byte[] id) {
        final var result = new ArrayList<Pkcs11Attribute>();

        result.add(attributeFactory.makeAttribute(CKA_CLASS, CKO_CERTIFICATE));
        result.add(attributeFactory.makeAttribute(CKA_CERTIFICATE_TYPE, CKC_X_509));
        result.add(attributeFactory.makeAttribute(CKA_ID, id));
        result.add(attributeFactory.makeAttribute(CKA_CERTIFICATE_CATEGORY,
                CK_CERTIFICATE_CATEGORY_TOKEN_USER.getAsLong()));

        return result;
    }

    private static List<Pkcs11Attribute> makePublicKeyTemplate(IPkcs11AttributeFactory attributeFactory,
                                                               byte[] publicKeyValue) {
        final var result = new ArrayList<Pkcs11Attribute>();

        result.add(attributeFactory.makeAttribute(CKA_CLASS, CKO_PUBLIC_KEY));
        result.add(attributeFactory.makeAttribute(CKA_VALUE, publicKeyValue));

        return result;
    }

    private static List<Pkcs11Attribute> makePrivateKeyTemplate(IPkcs11AttributeFactory attributeFactory,
                                                                byte[] privateKeyId) {
        final var result = new ArrayList<Pkcs11Attribute>();

        result.add(attributeFactory.makeAttribute(CKA_CLASS, CKO_PRIVATE_KEY));
        result.add(attributeFactory.makeAttribute(CKA_ID, privateKeyId));

        return result;
    }
}
