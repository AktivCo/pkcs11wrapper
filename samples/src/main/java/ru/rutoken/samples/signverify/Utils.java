/*
 * Copyright (c) 2022, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.samples.signverify;

import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PublicKeyObject;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Session;

import java.util.Arrays;
import java.util.List;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.*;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ObjectClass.*;
import static ru.rutoken.samples.utils.Pkcs11ObjectTemplates.makePrivateKeyBaseTemplate;
import static ru.rutoken.samples.utils.Pkcs11Operations.findFirstObject;

final class Utils {
    private Utils() {
    }

    public static Pkcs11PrivateKeyObject findPrivateKeyById(RtPkcs11Session session, byte[] id) {
        final var privateKeyTemplate = makePrivateKeyBaseTemplate(session.getAttributeFactory(), id);
        // For simplicity, we find first object matching template, in production you should generally check that
        // only single object matches template.
        return findFirstObject(session, Pkcs11PrivateKeyObject.class, privateKeyTemplate);
    }

    public static Pkcs11PublicKeyObject findPublicKeyById(RtPkcs11Session session, byte[] id) {
        final var publicKeyTemplate = makePublicKeyTemplate(session.getAttributeFactory(), id);
        // For simplicity, we find first object matching template, in production you should generally check that
        // only single object matches template.
        return findFirstObject(session, Pkcs11PublicKeyObject.class, publicKeyTemplate);
    }

    private static List<Pkcs11Attribute> makePublicKeyTemplate(IPkcs11AttributeFactory attributeFactory,
                                                               byte[] publicKeyId) {
        return Arrays.asList(
                attributeFactory.makeAttribute(CKA_CLASS, CKO_PUBLIC_KEY),
                attributeFactory.makeAttribute(CKA_ID, publicKeyId)
        );
    }
}
