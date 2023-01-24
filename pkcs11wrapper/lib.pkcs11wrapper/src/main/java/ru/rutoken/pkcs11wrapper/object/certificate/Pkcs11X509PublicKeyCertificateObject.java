/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.certificate;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_HASH_OF_ISSUER_PUBLIC_KEY;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_HASH_OF_SUBJECT_PUBLIC_KEY;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_ID;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_ISSUER;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_JAVA_MIDP_SECURITY_DOMAIN;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_NAME_HASH_ALGORITHM;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_SERIAL_NUMBER;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_SUBJECT;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_URL;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_VALUE;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11StringAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11LongAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11MechanismTypeAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;

public class Pkcs11X509PublicKeyCertificateObject extends Pkcs11CertificateObject {
    private final Pkcs11ByteArrayAttribute mSubjectAttribute = new Pkcs11ByteArrayAttribute(CKA_SUBJECT);
    private final Pkcs11ByteArrayAttribute mIdAttribute = new Pkcs11ByteArrayAttribute(CKA_ID);
    private final Pkcs11ByteArrayAttribute mIssuerAttribute = new Pkcs11ByteArrayAttribute(CKA_ISSUER);
    private final Pkcs11ByteArrayAttribute mSerialNumberAttribute = new Pkcs11ByteArrayAttribute(CKA_SERIAL_NUMBER);
    private final Pkcs11ByteArrayAttribute mValueAttribute = new Pkcs11ByteArrayAttribute(CKA_VALUE);
    private final Pkcs11StringAttribute mUrlAttribute = new Pkcs11StringAttribute(CKA_URL);
    private final Pkcs11ByteArrayAttribute mHashOfSubjectPublicKeyAttribute =
            new Pkcs11ByteArrayAttribute(CKA_HASH_OF_SUBJECT_PUBLIC_KEY);
    private final Pkcs11ByteArrayAttribute mHashOfIssuerPublicKeyAttribute =
            new Pkcs11ByteArrayAttribute(CKA_HASH_OF_ISSUER_PUBLIC_KEY);
    private final Pkcs11LongAttribute mJavaMidpSecurityDomainAttribute =
            new Pkcs11LongAttribute(CKA_JAVA_MIDP_SECURITY_DOMAIN);
    private final Pkcs11MechanismTypeAttribute mNameHashAlgorithmAttribute =
            new Pkcs11MechanismTypeAttribute(CKA_NAME_HASH_ALGORITHM);

    protected Pkcs11X509PublicKeyCertificateObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11ByteArrayAttribute getSubjectAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mSubjectAttribute);
    }

    public Pkcs11ByteArrayAttribute getIdAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mIdAttribute);
    }

    public Pkcs11ByteArrayAttribute getIssuerAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mIssuerAttribute);
    }

    public Pkcs11ByteArrayAttribute getSerialNumberAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mSerialNumberAttribute);
    }

    public Pkcs11ByteArrayAttribute getValueAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mValueAttribute);
    }

    public Pkcs11StringAttribute getUrlAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mUrlAttribute);
    }

    public Pkcs11ByteArrayAttribute getHashOfSubjectPublicKeyAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mHashOfSubjectPublicKeyAttribute);
    }

    public Pkcs11ByteArrayAttribute getHashOfIssuerPublicKeyAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mHashOfIssuerPublicKeyAttribute);
    }

    public Pkcs11LongAttribute getJavaMidpSecurityDomainAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mJavaMidpSecurityDomainAttribute);
    }

    public Pkcs11MechanismTypeAttribute getNameHashAlgorithmAttribute(Pkcs11Session session) {
        return getAttributeValue(session, mNameHashAlgorithmAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mSubjectAttribute);
        registerAttribute(mIdAttribute);
        registerAttribute(mIssuerAttribute);
        registerAttribute(mSerialNumberAttribute);
        registerAttribute(mValueAttribute);
        registerAttribute(mUrlAttribute);
        registerAttribute(mHashOfSubjectPublicKeyAttribute);
        registerAttribute(mHashOfIssuerPublicKeyAttribute);
        registerAttribute(mJavaMidpSecurityDomainAttribute);
        registerAttribute(mNameHashAlgorithmAttribute);
    }
}
