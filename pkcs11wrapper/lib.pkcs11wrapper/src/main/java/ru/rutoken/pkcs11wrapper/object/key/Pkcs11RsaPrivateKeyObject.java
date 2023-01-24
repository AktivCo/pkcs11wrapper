/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.key;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_COEFFICIENT;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_EXPONENT_1;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_EXPONENT_2;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_MODULUS;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_PRIME_1;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_PRIME_2;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_PRIVATE_EXPONENT;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_PUBLIC_EXPONENT;

public class Pkcs11RsaPrivateKeyObject extends Pkcs11PrivateKeyObject {
    private final Pkcs11ByteArrayAttribute mModulusAttribute = new Pkcs11ByteArrayAttribute(CKA_MODULUS);
    private final Pkcs11ByteArrayAttribute mPublicExponentAttribute = new Pkcs11ByteArrayAttribute(CKA_PUBLIC_EXPONENT);
    private final Pkcs11ByteArrayAttribute mPrivateExponentAttribute =
            new Pkcs11ByteArrayAttribute(CKA_PRIVATE_EXPONENT);
    private final Pkcs11ByteArrayAttribute mPrime1Attribute = new Pkcs11ByteArrayAttribute(CKA_PRIME_1);
    private final Pkcs11ByteArrayAttribute mPrime2Attribute = new Pkcs11ByteArrayAttribute(CKA_PRIME_2);
    private final Pkcs11ByteArrayAttribute mExponent1Attribute = new Pkcs11ByteArrayAttribute(CKA_EXPONENT_1);
    private final Pkcs11ByteArrayAttribute mExponent2Attribute = new Pkcs11ByteArrayAttribute(CKA_EXPONENT_2);
    private final Pkcs11ByteArrayAttribute mCoefficientAttribute = new Pkcs11ByteArrayAttribute(CKA_COEFFICIENT);

    protected Pkcs11RsaPrivateKeyObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11ByteArrayAttribute getModulusAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mModulusAttribute);
    }

    public Pkcs11ByteArrayAttribute getPublicExponentAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mPublicExponentAttribute);
    }

    public Pkcs11ByteArrayAttribute getPrivateExponentAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mPrivateExponentAttribute);
    }

    public Pkcs11ByteArrayAttribute getPrime1AttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mPrime1Attribute);
    }

    public Pkcs11ByteArrayAttribute getPrime2AttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mPrime2Attribute);
    }

    public Pkcs11ByteArrayAttribute getExponent1AttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mExponent1Attribute);
    }

    public Pkcs11ByteArrayAttribute getExponent2AttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mExponent2Attribute);
    }

    public Pkcs11ByteArrayAttribute getCoefficientAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mCoefficientAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mModulusAttribute);
        registerAttribute(mPublicExponentAttribute);
        registerAttribute(mPrivateExponentAttribute);
        registerAttribute(mPrime1Attribute);
        registerAttribute(mPrime2Attribute);
        registerAttribute(mExponent1Attribute);
        registerAttribute(mExponent2Attribute);
        registerAttribute(mCoefficientAttribute);
    }
}
