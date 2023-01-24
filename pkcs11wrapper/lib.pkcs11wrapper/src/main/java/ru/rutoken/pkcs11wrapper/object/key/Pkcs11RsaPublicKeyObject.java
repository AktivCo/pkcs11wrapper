/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.key;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11LongAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_MODULUS;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_MODULUS_BITS;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_PUBLIC_EXPONENT;

public class Pkcs11RsaPublicKeyObject extends Pkcs11PublicKeyObject {
    private final Pkcs11ByteArrayAttribute mModulusAttribute = new Pkcs11ByteArrayAttribute(CKA_MODULUS);
    private final Pkcs11LongAttribute mModulusBitsAttribute = new Pkcs11LongAttribute(CKA_MODULUS_BITS);
    private final Pkcs11ByteArrayAttribute mPublicExponentAttribute = new Pkcs11ByteArrayAttribute(CKA_PUBLIC_EXPONENT);

    protected Pkcs11RsaPublicKeyObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11ByteArrayAttribute getModulusAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mModulusAttribute);
    }

    public Pkcs11LongAttribute getModulusBitsAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mModulusBitsAttribute);
    }

    public Pkcs11ByteArrayAttribute getPublicExponentAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mPublicExponentAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mModulusAttribute);
        registerAttribute(mModulusBitsAttribute);
        registerAttribute(mPublicExponentAttribute);
    }
}
