/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.certificate;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_CERTIFICATE_CATEGORY;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_CERTIFICATE_TYPE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_CHECK_VALUE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_END_DATE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_PUBLIC_KEY_INFO;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_START_DATE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_TRUSTED;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11BooleanAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11DateAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11CertificateTypeAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11LongAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.object.Pkcs11StorageObject;

public class Pkcs11CertificateObject extends Pkcs11StorageObject {
    private final Pkcs11CertificateTypeAttribute mCertificateTypeAttribute =
            new Pkcs11CertificateTypeAttribute(CKA_CERTIFICATE_TYPE);
    private final Pkcs11BooleanAttribute mTrustedAttribute = new Pkcs11BooleanAttribute(CKA_TRUSTED);
    private final Pkcs11LongAttribute mCertificateCategoryAttribute = new Pkcs11LongAttribute(CKA_CERTIFICATE_CATEGORY);
    private final Pkcs11ByteArrayAttribute mCheckValueAttribute = new Pkcs11ByteArrayAttribute(CKA_CHECK_VALUE);
    private final Pkcs11DateAttribute mStartDateAttribute = new Pkcs11DateAttribute(CKA_START_DATE);
    private final Pkcs11DateAttribute mEndDateAttribute = new Pkcs11DateAttribute(CKA_END_DATE);
    private final Pkcs11ByteArrayAttribute mPublicKeyInfoAttribute = new Pkcs11ByteArrayAttribute(CKA_PUBLIC_KEY_INFO);

    protected Pkcs11CertificateObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11CertificateTypeAttribute getCertificateTypeAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mCertificateTypeAttribute);
    }

    public Pkcs11BooleanAttribute getTrustedAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mTrustedAttribute);
    }

    public Pkcs11LongAttribute getCertificateCategoryAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mCertificateCategoryAttribute);
    }

    public Pkcs11ByteArrayAttribute getCheckValueAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mCheckValueAttribute);
    }

    public Pkcs11DateAttribute getStartDateAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mStartDateAttribute);
    }

    public Pkcs11DateAttribute getEndDateAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mEndDateAttribute);
    }

    public Pkcs11ByteArrayAttribute getPublicKeyInfoAttribute(Pkcs11Session session) {
        return getAttributeValue(session, mPublicKeyInfoAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mCertificateTypeAttribute);
        registerAttribute(mTrustedAttribute);
        registerAttribute(mCertificateCategoryAttribute);
        registerAttribute(mCheckValueAttribute);
        registerAttribute(mStartDateAttribute);
        registerAttribute(mEndDateAttribute);
        registerAttribute(mPublicKeyInfoAttribute);
    }
}
