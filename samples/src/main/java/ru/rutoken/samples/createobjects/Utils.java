/*
 * Copyright (c) 2022, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.samples.createobjects;

import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;

import java.util.Arrays;
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
        return Arrays.asList(
                attributeFactory.makeAttribute(CKA_CLASS, CKO_CERTIFICATE),
                attributeFactory.makeAttribute(CKA_CERTIFICATE_TYPE, CKC_X_509),
                attributeFactory.makeAttribute(CKA_CERTIFICATE_CATEGORY,
                        CK_CERTIFICATE_CATEGORY_TOKEN_USER.getAsLong()),
                attributeFactory.makeAttribute(CKA_ID, id),
                attributeFactory.makeAttribute(CKA_TOKEN, true),
                attributeFactory.makeAttribute(CKA_PRIVATE, false),
                attributeFactory.makeAttribute(CKA_VALUE, value)
        );
    }

    public static List<Pkcs11Attribute> makeGostPublicKeyTemplate(IPkcs11AttributeFactory attributeFactory,
                                                                  GostKeyPairParams keyPairParams) {
        return Arrays.asList(
                attributeFactory.makeAttribute(CKA_CLASS, CKO_PUBLIC_KEY),
                attributeFactory.makeAttribute(CKA_KEY_TYPE, keyPairParams.getKeyType()),
                attributeFactory.makeAttribute(CKA_LABEL, keyPairParams.getPublicKeyLabel()),
                attributeFactory.makeAttribute(CKA_ID, keyPairParams.getId()),
                attributeFactory.makeAttribute(CKA_PRIVATE, false),
                attributeFactory.makeAttribute(CKA_GOSTR3410_PARAMS, keyPairParams.getParamset3410()),
                attributeFactory.makeAttribute(CKA_GOSTR3411_PARAMS, keyPairParams.getParamset3411()),
                attributeFactory.makeAttribute(CKA_DERIVE, true),
                attributeFactory.makeAttribute(CKA_TOKEN, true),
                attributeFactory.makeAttribute(CKA_VERIFY, true)
        );
    }

    public static List<Pkcs11Attribute> makeGostPrivateKeyTemplate(IPkcs11AttributeFactory attributeFactory,
                                                                   GostKeyPairParams keyPairParams) {
        return Arrays.asList(
                attributeFactory.makeAttribute(CKA_CLASS, CKO_PRIVATE_KEY),
                attributeFactory.makeAttribute(CKA_KEY_TYPE, keyPairParams.getKeyType()),
                attributeFactory.makeAttribute(CKA_LABEL, keyPairParams.getPrivateKeyLabel()),
                attributeFactory.makeAttribute(CKA_ID, keyPairParams.getId()),
                attributeFactory.makeAttribute(CKA_PRIVATE, true),
                attributeFactory.makeAttribute(CKA_GOSTR3410_PARAMS, keyPairParams.getParamset3410()),
                attributeFactory.makeAttribute(CKA_GOSTR3411_PARAMS, keyPairParams.getParamset3411()),
                attributeFactory.makeAttribute(CKA_DERIVE, true),
                attributeFactory.makeAttribute(CKA_TOKEN, true),
                attributeFactory.makeAttribute(CKA_SIGN, true)
        );
    }

    public static List<Pkcs11Attribute> makeRsaPublicKeyTemplate(IPkcs11AttributeFactory attributeFactory,
                                                                 int modulusBits, byte[] publicExponent, String label,
                                                                 byte[] id) {
        return Arrays.asList(
                attributeFactory.makeAttribute(CKA_CLASS, CKO_PUBLIC_KEY),
                attributeFactory.makeAttribute(CKA_KEY_TYPE, CKK_RSA),
                attributeFactory.makeAttribute(CKA_LABEL, label),
                attributeFactory.makeAttribute(CKA_ID, id),
                attributeFactory.makeAttribute(CKA_PRIVATE, false),
                attributeFactory.makeAttribute(CKA_ENCRYPT, true),
                attributeFactory.makeAttribute(CKA_TOKEN, true),
                attributeFactory.makeAttribute(CKA_MODULUS_BITS, modulusBits),
                attributeFactory.makeAttribute(CKA_PUBLIC_EXPONENT, publicExponent),
                attributeFactory.makeAttribute(CKA_VERIFY, true)
        );
    }

    public static List<Pkcs11Attribute> makeRsaPrivateKeyTemplate(IPkcs11AttributeFactory attributeFactory,
                                                                  byte[] publicExponent, String label, byte[] id) {
        return Arrays.asList(
                attributeFactory.makeAttribute(CKA_CLASS, CKO_PRIVATE_KEY),
                attributeFactory.makeAttribute(CKA_KEY_TYPE, CKK_RSA),
                attributeFactory.makeAttribute(CKA_LABEL, label),
                attributeFactory.makeAttribute(CKA_ID, id),
                attributeFactory.makeAttribute(CKA_PRIVATE, true),
                attributeFactory.makeAttribute(CKA_DECRYPT, true),
                attributeFactory.makeAttribute(CKA_TOKEN, true),
                attributeFactory.makeAttribute(CKA_SIGN, true),
                attributeFactory.makeAttribute(CKA_PUBLIC_EXPONENT, publicExponent)
        );
    }

    public static List<Pkcs11Attribute> makeEcdsaPublicKeyTemplate(IPkcs11AttributeFactory attributeFactory,
                                                                   byte[] ecParams, String label, byte[] id) {
        return Arrays.asList(
                attributeFactory.makeAttribute(CKA_CLASS, CKO_PUBLIC_KEY),
                attributeFactory.makeAttribute(CKA_KEY_TYPE, CKK_EC),
                attributeFactory.makeAttribute(CKA_LABEL, label),
                attributeFactory.makeAttribute(CKA_ID, id),
                attributeFactory.makeAttribute(CKA_PRIVATE, false),
                attributeFactory.makeAttribute(CKA_TOKEN, true),
                attributeFactory.makeAttribute(CKA_EC_PARAMS, ecParams),
                attributeFactory.makeAttribute(CKA_VERIFY, true)
        );
    }

    public static List<Pkcs11Attribute> makeEcdsaPrivateKeyTemplate(IPkcs11AttributeFactory attributeFactory,
                                                                    String label, byte[] id) {
        return Arrays.asList(
                attributeFactory.makeAttribute(CKA_CLASS, CKO_PRIVATE_KEY),
                attributeFactory.makeAttribute(CKA_KEY_TYPE, CKK_EC),
                attributeFactory.makeAttribute(CKA_LABEL, label),
                attributeFactory.makeAttribute(CKA_ID, id),
                attributeFactory.makeAttribute(CKA_PRIVATE, true),
                attributeFactory.makeAttribute(CKA_TOKEN, true),
                attributeFactory.makeAttribute(CKA_SIGN, true)
        );
    }
}
