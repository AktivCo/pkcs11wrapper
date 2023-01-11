package ru.rutoken.samples.createobjects;

import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;

import java.util.ArrayList;
import java.util.List;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.*;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11CertificateCategory.CK_CERTIFICATE_CATEGORY_TOKEN_USER;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11CertificateType.CKC_X_509;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType.CKK_EC;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType.CKK_RSA;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ObjectClass.*;

public class Utils {
    private Utils() {
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

    public static List<Pkcs11Attribute> makeGostPublicKeyTemplate(IPkcs11AttributeFactory attributeFactory,
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

    public static List<Pkcs11Attribute> makeGostPrivateKeyTemplate(IPkcs11AttributeFactory attributeFactory,
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

    public static List<Pkcs11Attribute> makeRsaPublicKeyTemplate(IPkcs11AttributeFactory attributeFactory,
                                                                 int modulusBits, byte[] publicExponent, String label,
                                                                 byte[] id) {
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

    public static List<Pkcs11Attribute> makeRsaPrivateKeyTemplate(IPkcs11AttributeFactory attributeFactory,
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

    public static List<Pkcs11Attribute> makeEcdsaPublicKeyTemplate(IPkcs11AttributeFactory attributeFactory,
                                                                   byte[] ecParams, String label, byte[] id) {
        final var result = new ArrayList<Pkcs11Attribute>();

        result.add(attributeFactory.makeAttribute(CKA_CLASS, CKO_PUBLIC_KEY));
        result.add(attributeFactory.makeAttribute(CKA_KEY_TYPE, CKK_EC));
        result.add(attributeFactory.makeAttribute(CKA_LABEL, label));
        result.add(attributeFactory.makeAttribute(CKA_ID, id));
        result.add(attributeFactory.makeAttribute(CKA_PRIVATE, false));
        result.add(attributeFactory.makeAttribute(CKA_TOKEN, true));
        result.add(attributeFactory.makeAttribute(CKA_EC_PARAMS, ecParams));
        result.add(attributeFactory.makeAttribute(CKA_VERIFY, true));

        return result;
    }

    public static List<Pkcs11Attribute> makeEcdsaPrivateKeyTemplate(IPkcs11AttributeFactory attributeFactory,
                                                                    String label, byte[] id) {
        final var result = new ArrayList<Pkcs11Attribute>();

        result.add(attributeFactory.makeAttribute(CKA_CLASS, CKO_PRIVATE_KEY));
        result.add(attributeFactory.makeAttribute(CKA_KEY_TYPE, CKK_EC));
        result.add(attributeFactory.makeAttribute(CKA_LABEL, label));
        result.add(attributeFactory.makeAttribute(CKA_ID, id));
        result.add(attributeFactory.makeAttribute(CKA_PRIVATE, true));
        result.add(attributeFactory.makeAttribute(CKA_TOKEN, true));
        result.add(attributeFactory.makeAttribute(CKA_SIGN, true));

        return result;
    }
}
