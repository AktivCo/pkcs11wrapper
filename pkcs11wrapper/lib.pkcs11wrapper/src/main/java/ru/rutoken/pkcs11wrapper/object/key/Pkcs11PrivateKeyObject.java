package ru.rutoken.pkcs11wrapper.object.key;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_ALWAYS_AUTHENTICATE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_ALWAYS_SENSITIVE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_DECRYPT;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_EXTRACTABLE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_NEVER_EXTRACTABLE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_PUBLIC_KEY_INFO;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_SENSITIVE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_SIGN;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_SIGN_RECOVER;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_SUBJECT;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_UNWRAP;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_UNWRAP_TEMPLATE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_WRAP_WITH_TRUSTED;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11BooleanAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;

public class Pkcs11PrivateKeyObject extends Pkcs11KeyObject {
    private final Pkcs11ByteArrayAttribute mSubjectAttribute = new Pkcs11ByteArrayAttribute(CKA_SUBJECT);
    private final Pkcs11BooleanAttribute mSensitiveAttribute = new Pkcs11BooleanAttribute(CKA_SENSITIVE);
    private final Pkcs11BooleanAttribute mDecryptAttribute = new Pkcs11BooleanAttribute(CKA_DECRYPT);
    private final Pkcs11BooleanAttribute mSignAttribute = new Pkcs11BooleanAttribute(CKA_SIGN);
    private final Pkcs11BooleanAttribute mSignRecoverAttribute = new Pkcs11BooleanAttribute(CKA_SIGN_RECOVER);
    private final Pkcs11BooleanAttribute mUnwrapAttribute = new Pkcs11BooleanAttribute(CKA_UNWRAP);
    private final Pkcs11BooleanAttribute mExtractableAttribute = new Pkcs11BooleanAttribute(CKA_EXTRACTABLE);
    private final Pkcs11BooleanAttribute mAlwaysSensitiveAttribute = new Pkcs11BooleanAttribute(CKA_ALWAYS_SENSITIVE);
    private final Pkcs11BooleanAttribute mNeverExtractableAttribute = new Pkcs11BooleanAttribute(CKA_NEVER_EXTRACTABLE);
    private final Pkcs11BooleanAttribute mWrapWithTrustedAttribute = new Pkcs11BooleanAttribute(CKA_WRAP_WITH_TRUSTED);
    private final Pkcs11ArrayAttribute mUnwrapTemplateAttribute = new Pkcs11ArrayAttribute(CKA_UNWRAP_TEMPLATE);
    private final Pkcs11BooleanAttribute mAlwaysAuthenticateAttribute =
            new Pkcs11BooleanAttribute(CKA_ALWAYS_AUTHENTICATE);
    private final Pkcs11ByteArrayAttribute mPublicKeyInfoAttribute = new Pkcs11ByteArrayAttribute(CKA_PUBLIC_KEY_INFO);

    protected Pkcs11PrivateKeyObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11ByteArrayAttribute getSubjectAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mSubjectAttribute);
    }

    public Pkcs11BooleanAttribute getSensitiveAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mSensitiveAttribute);
    }

    public Pkcs11BooleanAttribute getDecryptAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mDecryptAttribute);
    }

    public Pkcs11BooleanAttribute getSignAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mSignAttribute);
    }

    public Pkcs11BooleanAttribute getSignRecoverAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mSignRecoverAttribute);
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

    public Pkcs11BooleanAttribute getWrapWithTrustedAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mWrapWithTrustedAttribute);
    }

    public Pkcs11ArrayAttribute getUnwrapTemplateAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mUnwrapTemplateAttribute);
    }

    public Pkcs11BooleanAttribute getAlwaysAuthenticateAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mAlwaysAuthenticateAttribute);
    }

    public Pkcs11ByteArrayAttribute getPublicKeyInfoAttribute(Pkcs11Session session) {
        return getAttributeValue(session, mPublicKeyInfoAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mSubjectAttribute);
        registerAttribute(mSensitiveAttribute);
        registerAttribute(mDecryptAttribute);
        registerAttribute(mSignAttribute);
        registerAttribute(mSignRecoverAttribute);
        registerAttribute(mUnwrapAttribute);
        registerAttribute(mExtractableAttribute);
        registerAttribute(mAlwaysSensitiveAttribute);
        registerAttribute(mNeverExtractableAttribute);
        registerAttribute(mWrapWithTrustedAttribute);
        registerAttribute(mUnwrapTemplateAttribute);
        registerAttribute(mAlwaysAuthenticateAttribute);
        registerAttribute(mPublicKeyInfoAttribute);
    }
}
