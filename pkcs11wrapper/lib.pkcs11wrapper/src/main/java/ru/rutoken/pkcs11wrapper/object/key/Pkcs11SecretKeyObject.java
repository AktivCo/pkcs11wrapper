/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.key;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11BooleanAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_ALWAYS_SENSITIVE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_CHECK_VALUE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_DECRYPT;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_ENCRYPT;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_EXTRACTABLE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_NEVER_EXTRACTABLE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_SENSITIVE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_SIGN;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_TRUSTED;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_UNWRAP;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_UNWRAP_TEMPLATE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_VERIFY;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_WRAP;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_WRAP_TEMPLATE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_WRAP_WITH_TRUSTED;

public class Pkcs11SecretKeyObject extends Pkcs11KeyObject {
    private final Pkcs11BooleanAttribute mSensitiveAttribute = new Pkcs11BooleanAttribute(CKA_SENSITIVE);
    private final Pkcs11BooleanAttribute mEncryptAttribute = new Pkcs11BooleanAttribute(CKA_ENCRYPT);
    private final Pkcs11BooleanAttribute mDecryptAttribute = new Pkcs11BooleanAttribute(CKA_DECRYPT);
    private final Pkcs11BooleanAttribute mSignAttribute = new Pkcs11BooleanAttribute(CKA_SIGN);
    private final Pkcs11BooleanAttribute mVerifyAttribute = new Pkcs11BooleanAttribute(CKA_VERIFY);
    private final Pkcs11BooleanAttribute mWrapAttribute = new Pkcs11BooleanAttribute(CKA_WRAP);
    private final Pkcs11BooleanAttribute mUnwrapAttribute = new Pkcs11BooleanAttribute(CKA_UNWRAP);
    private final Pkcs11BooleanAttribute mExtractableAttribute = new Pkcs11BooleanAttribute(CKA_EXTRACTABLE);
    private final Pkcs11BooleanAttribute mAlwaysSensitiveAttribute = new Pkcs11BooleanAttribute(CKA_ALWAYS_SENSITIVE);
    private final Pkcs11BooleanAttribute mNeverExtractableAttribute = new Pkcs11BooleanAttribute(CKA_NEVER_EXTRACTABLE);
    private final Pkcs11ByteArrayAttribute mCheckValueAttribute = new Pkcs11ByteArrayAttribute(CKA_CHECK_VALUE);
    private final Pkcs11BooleanAttribute mWrapWithTrustedAttribute = new Pkcs11BooleanAttribute(CKA_WRAP_WITH_TRUSTED);
    private final Pkcs11BooleanAttribute mTrustedAttribute = new Pkcs11BooleanAttribute(CKA_TRUSTED);
    private final Pkcs11ArrayAttribute mWrapTemplateAttribute = new Pkcs11ArrayAttribute(CKA_WRAP_TEMPLATE);
    private final Pkcs11ArrayAttribute mUnwrapTemplateAttribute = new Pkcs11ArrayAttribute(CKA_UNWRAP_TEMPLATE);

    protected Pkcs11SecretKeyObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11BooleanAttribute getSensitiveAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mSensitiveAttribute);
    }

    public Pkcs11BooleanAttribute getEncryptAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mEncryptAttribute);
    }

    public Pkcs11BooleanAttribute getDecryptAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mDecryptAttribute);
    }

    public Pkcs11BooleanAttribute getSignAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mSignAttribute);
    }

    public Pkcs11BooleanAttribute getVerifyAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mVerifyAttribute);
    }

    public Pkcs11BooleanAttribute getWrapAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mWrapAttribute);
    }

    public Pkcs11BooleanAttribute getUnwrapAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mUnwrapAttribute);
    }

    public Pkcs11BooleanAttribute getExtractableAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mExtractableAttribute);
    }

    public Pkcs11BooleanAttribute getAlwaysSensitiveAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mAlwaysSensitiveAttribute);
    }

    public Pkcs11BooleanAttribute getNeverExtractableAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mNeverExtractableAttribute);
    }

    public Pkcs11ByteArrayAttribute getCheckValueAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mCheckValueAttribute);
    }

    public Pkcs11BooleanAttribute getWrapWithTrustedAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mWrapWithTrustedAttribute);
    }

    public Pkcs11BooleanAttribute getTrustedAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mTrustedAttribute);
    }

    public Pkcs11ArrayAttribute getWrapTemplateAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mWrapTemplateAttribute);
    }

    public Pkcs11ArrayAttribute getUnwrapTemplateAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mUnwrapTemplateAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mSensitiveAttribute);
        registerAttribute(mEncryptAttribute);
        registerAttribute(mDecryptAttribute);
        registerAttribute(mSignAttribute);
        registerAttribute(mVerifyAttribute);
        registerAttribute(mWrapAttribute);
        registerAttribute(mUnwrapAttribute);
        registerAttribute(mExtractableAttribute);
        registerAttribute(mAlwaysSensitiveAttribute);
        registerAttribute(mNeverExtractableAttribute);
        registerAttribute(mCheckValueAttribute);
        registerAttribute(mWrapWithTrustedAttribute);
        registerAttribute(mTrustedAttribute);
        registerAttribute(mWrapTemplateAttribute);
        registerAttribute(mUnwrapTemplateAttribute);
    }
}
