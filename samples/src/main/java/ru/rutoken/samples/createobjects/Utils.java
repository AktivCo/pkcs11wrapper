package ru.rutoken.samples.createobjects;

import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11KeyPair;
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11GostPrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11GostPublicKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11RsaPrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11RsaPublicKeyObject;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Session;

import java.util.ArrayList;
import java.util.List;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.*;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11CertificateCategory.CK_CERTIFICATE_CATEGORY_TOKEN_USER;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11CertificateType.CKC_X_509;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType.CKK_RSA;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_RSA_PKCS_KEY_PAIR_GEN;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ObjectClass.*;

public class Utils {
    private Utils() {
    }

    public static Pkcs11KeyPair<Pkcs11GostPublicKeyObject, Pkcs11GostPrivateKeyObject> generateGostKeyPair(
            RtPkcs11Session session, GostKeyPairParams keyPairParams) {
        return session.getKeyManager().generateKeyPair(
                Pkcs11GostPublicKeyObject.class,
                Pkcs11GostPrivateKeyObject.class,
                Pkcs11Mechanism.make(keyPairParams.getMechanismType()),
                makeGostPublicKeyTemplate(session.getAttributeFactory(), keyPairParams),
                makeGostPrivateKeyTemplate(session.getAttributeFactory(), keyPairParams)
        );
    }

    public static Pkcs11KeyPair<Pkcs11RsaPublicKeyObject, Pkcs11RsaPrivateKeyObject> generateRsaKeyPair(
            RtPkcs11Session session, int modulusBits, byte[] publicExponent, String publicKeyLabel,
            String privateKeyLabel, byte[] id) {
        return session.getKeyManager().generateKeyPair(
                Pkcs11RsaPublicKeyObject.class,
                Pkcs11RsaPrivateKeyObject.class,
                Pkcs11Mechanism.make(CKM_RSA_PKCS_KEY_PAIR_GEN),
                makeRsaPublicKeyTemplate(session.getAttributeFactory(), modulusBits, publicExponent, publicKeyLabel,
                        id),
                makeRsaPrivateKeyTemplate(session.getAttributeFactory(), publicExponent, privateKeyLabel, id)
        );
    }

    public static List<Pkcs11Attribute> makeCertificateTemplate(IPkcs11AttributeFactory attributeFactory, byte[] id,
                                                                byte[] value) {
        final var result = new ArrayList<Pkcs11Attribute>();

        result.add(attributeFactory.makeAttribute(CKA_CLASS, CKO_CERTIFICATE));
        result.add(attributeFactory.makeAttribute(CKA_CERTIFICATE_TYPE, CKC_X_509));
        result.add(attributeFactory.makeAttribute(CKA_CERTIFICATE_CATEGORY,
                CK_CERTIFICATE_CATEGORY_TOKEN_USER.getAsLong()));
        result.add(attributeFactory.makeAttribute(CKA_ID, id));
        result.add(attributeFactory.makeAttribute(CKA_TOKEN, true));
        result.add(attributeFactory.makeAttribute(CKA_PRIVATE, false));
        result.add(attributeFactory.makeAttribute(CKA_VALUE, value));

        return result;
    }

    private static List<Pkcs11Attribute> makeGostPublicKeyTemplate(IPkcs11AttributeFactory attributeFactory,
                                                                   GostKeyPairParams keyPairParams) {
        final var result = new ArrayList<Pkcs11Attribute>();

        result.add(attributeFactory.makeAttribute(CKA_CLASS, CKO_PUBLIC_KEY));
        result.add(attributeFactory.makeAttribute(CKA_KEY_TYPE, keyPairParams.getKeyType()));
        result.add(attributeFactory.makeAttribute(CKA_LABEL, keyPairParams.getPublicKeyLabel()));
        result.add(attributeFactory.makeAttribute(CKA_ID, keyPairParams.getId()));
        result.add(attributeFactory.makeAttribute(CKA_PRIVATE, false));
        result.add(attributeFactory.makeAttribute(CKA_GOSTR3410_PARAMS, keyPairParams.getParamset3410()));
        result.add(attributeFactory.makeAttribute(CKA_GOSTR3411_PARAMS, keyPairParams.getParamset3411()));
        result.add(attributeFactory.makeAttribute(CKA_DERIVE, true));
        result.add(attributeFactory.makeAttribute(CKA_TOKEN, true));
        result.add(attributeFactory.makeAttribute(CKA_VERIFY, true));

        return result;
    }

    private static List<Pkcs11Attribute> makeGostPrivateKeyTemplate(IPkcs11AttributeFactory attributeFactory,
                                                                    GostKeyPairParams keyPairParams) {
        final var result = new ArrayList<Pkcs11Attribute>();

        result.add(attributeFactory.makeAttribute(CKA_CLASS, CKO_PRIVATE_KEY));
        result.add(attributeFactory.makeAttribute(CKA_KEY_TYPE, keyPairParams.getKeyType()));
        result.add(attributeFactory.makeAttribute(CKA_LABEL, keyPairParams.getPrivateKeyLabel()));
        result.add(attributeFactory.makeAttribute(CKA_ID, keyPairParams.getId()));
        result.add(attributeFactory.makeAttribute(CKA_PRIVATE, true));
        result.add(attributeFactory.makeAttribute(CKA_GOSTR3410_PARAMS, keyPairParams.getParamset3410()));
        result.add(attributeFactory.makeAttribute(CKA_GOSTR3411_PARAMS, keyPairParams.getParamset3411()));
        result.add(attributeFactory.makeAttribute(CKA_DERIVE, true));
        result.add(attributeFactory.makeAttribute(CKA_TOKEN, true));
        result.add(attributeFactory.makeAttribute(CKA_SIGN, true));

        return result;
    }

    private static List<Pkcs11Attribute> makeRsaPublicKeyTemplate(IPkcs11AttributeFactory attributeFactory,
                                                                  int modulusBits, byte[] publicExponent,
                                                                  String label, byte[] id) {
        final var result = new ArrayList<Pkcs11Attribute>();

        result.add(attributeFactory.makeAttribute(CKA_CLASS, CKO_PUBLIC_KEY));
        result.add(attributeFactory.makeAttribute(CKA_KEY_TYPE, CKK_RSA));
        result.add(attributeFactory.makeAttribute(CKA_LABEL, label));
        result.add(attributeFactory.makeAttribute(CKA_ID, id));
        result.add(attributeFactory.makeAttribute(CKA_PRIVATE, false));
        result.add(attributeFactory.makeAttribute(CKA_ENCRYPT, true));
        result.add(attributeFactory.makeAttribute(CKA_TOKEN, true));
        result.add(attributeFactory.makeAttribute(CKA_MODULUS_BITS, modulusBits));
        result.add(attributeFactory.makeAttribute(CKA_PUBLIC_EXPONENT, publicExponent));
        result.add(attributeFactory.makeAttribute(CKA_VERIFY, true));

        return result;
    }

    private static List<Pkcs11Attribute> makeRsaPrivateKeyTemplate(IPkcs11AttributeFactory attributeFactory,
                                                                   byte[] publicExponent, String label, byte[] id) {
        final var result = new ArrayList<Pkcs11Attribute>();

        result.add(attributeFactory.makeAttribute(CKA_CLASS, CKO_PRIVATE_KEY));
        result.add(attributeFactory.makeAttribute(CKA_KEY_TYPE, CKK_RSA));
        result.add(attributeFactory.makeAttribute(CKA_LABEL, label));
        result.add(attributeFactory.makeAttribute(CKA_ID, id));
        result.add(attributeFactory.makeAttribute(CKA_PRIVATE, true));
        result.add(attributeFactory.makeAttribute(CKA_DECRYPT, true));
        result.add(attributeFactory.makeAttribute(CKA_TOKEN, true));
        result.add(attributeFactory.makeAttribute(CKA_SIGN, true));
        result.add(attributeFactory.makeAttribute(CKA_PUBLIC_EXPONENT, publicExponent));

        return result;
    }
}
