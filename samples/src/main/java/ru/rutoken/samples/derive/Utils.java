/*
 * Copyright (c) 2023, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.samples.derive;

import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;

import java.util.Arrays;
import java.util.List;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.*;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType.CKK_GOST28147;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ObjectClass.*;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyType.CKK_MAGMA;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyType.CKK_MAGMA_TWIN_KEY;

final class Utils {
    private Utils() {
    }

    static List<Pkcs11Attribute> makeMagmaTwinSecretKeyTemplate(IPkcs11AttributeFactory attributeFactory) {
        return Arrays.asList(
                attributeFactory.makeAttribute(CKA_CLASS, CKO_SECRET_KEY),
                attributeFactory.makeAttribute(CKA_KEY_TYPE, CKK_MAGMA_TWIN_KEY),
                attributeFactory.makeAttribute(CKA_TOKEN, false),
                attributeFactory.makeAttribute(CKA_WRAP, true),
                attributeFactory.makeAttribute(CKA_UNWRAP, true),
                attributeFactory.makeAttribute(CKA_PRIVATE, true),
                attributeFactory.makeAttribute(CKA_SENSITIVE, true),
                attributeFactory.makeAttribute(CKA_EXTRACTABLE, true)
        );
    }

    static List<Pkcs11Attribute> makeMagmaSecretKeyTemplate(IPkcs11AttributeFactory attributeFactory) {
        return Arrays.asList(
                attributeFactory.makeAttribute(CKA_CLASS, CKO_SECRET_KEY),
                attributeFactory.makeAttribute(CKA_KEY_TYPE, CKK_MAGMA),
                attributeFactory.makeAttribute(CKA_TOKEN, false),
                attributeFactory.makeAttribute(CKA_ENCRYPT, true),
                attributeFactory.makeAttribute(CKA_DECRYPT, true),
                attributeFactory.makeAttribute(CKA_PRIVATE, true),
                attributeFactory.makeAttribute(CKA_SENSITIVE, true),
                attributeFactory.makeAttribute(CKA_EXTRACTABLE, true)
        );
    }

    static List<Pkcs11Attribute> makeGost28147SecretKeyTemplate(IPkcs11AttributeFactory attributeFactory) {
        return Arrays.asList(
                attributeFactory.makeAttribute(CKA_CLASS, CKO_SECRET_KEY),
                attributeFactory.makeAttribute(CKA_KEY_TYPE, CKK_GOST28147),
                attributeFactory.makeAttribute(CKA_TOKEN, false),
                attributeFactory.makeAttribute(CKA_ENCRYPT, true),
                attributeFactory.makeAttribute(CKA_DECRYPT, true),
                attributeFactory.makeAttribute(CKA_PRIVATE, true),
                attributeFactory.makeAttribute(CKA_SENSITIVE, false),
                attributeFactory.makeAttribute(CKA_EXTRACTABLE, true)
        );
    }
}