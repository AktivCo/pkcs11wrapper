/*
 * Copyright (c) 2023, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.samples.utils;

import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;

import java.util.Arrays;
import java.util.List;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.*;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_PUBLIC_EXPONENT;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11CertificateCategory.CK_CERTIFICATE_CATEGORY_TOKEN_USER;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11CertificateType.CKC_X_509;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType.CKK_RSA;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ObjectClass.*;

public final class Pkcs11ObjectTemplates {
    private Pkcs11ObjectTemplates() {
    }

    public static List<Pkcs11Attribute> makeRsaPublicKeyTemplate(IPkcs11AttributeFactory attributeFactory,
                                                                 byte[] modulusBits, byte[] publicExponent) {
        return Arrays.asList(
                attributeFactory.makeAttribute(CKA_CLASS, CKO_PUBLIC_KEY),
                attributeFactory.makeAttribute(CKA_KEY_TYPE, CKK_RSA),
                attributeFactory.makeAttribute(CKA_MODULUS, modulusBits),
                attributeFactory.makeAttribute(CKA_PUBLIC_EXPONENT, publicExponent)
        );
    }

    public static List<Pkcs11Attribute> makeGostPublicKeyBaseTemplate(IPkcs11AttributeFactory attributeFactory,
                                                                      byte[] publicKeyValue) {
        return Arrays.asList(
                attributeFactory.makeAttribute(CKA_CLASS, CKO_PUBLIC_KEY),
                attributeFactory.makeAttribute(CKA_VALUE, publicKeyValue)
        );
    }

    public static List<Pkcs11Attribute> makeCertificateTemplate(IPkcs11AttributeFactory attributeFactory, byte[] id) {
        return Arrays.asList(
                attributeFactory.makeAttribute(CKA_CLASS, CKO_CERTIFICATE),
                attributeFactory.makeAttribute(CKA_CERTIFICATE_TYPE, CKC_X_509),
                attributeFactory.makeAttribute(CKA_ID, id),
                attributeFactory.makeAttribute(CKA_CERTIFICATE_CATEGORY,
                        CK_CERTIFICATE_CATEGORY_TOKEN_USER.getAsLong())
        );
    }

    public static List<Pkcs11Attribute> makePrivateKeyBaseTemplate(IPkcs11AttributeFactory attributeFactory,
                                                                   byte[] privateKeyId) {
        return Arrays.asList(
                attributeFactory.makeAttribute(CKA_CLASS, CKO_PRIVATE_KEY),
                attributeFactory.makeAttribute(CKA_ID, privateKeyId)
        );
    }
}
