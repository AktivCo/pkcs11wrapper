package ru.rutoken.pkcs11wrapper.object.key;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_ENCRYPT;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_PUBLIC_KEY_INFO;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_SUBJECT;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_TRUSTED;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_VERIFY;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_VERIFY_RECOVER;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_WRAP;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_WRAP_TEMPLATE;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11BooleanAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;

public class Pkcs11PublicKeyObject extends Pkcs11KeyObject {
    private final Pkcs11ByteArrayAttribute mSubjectAttribute = new Pkcs11ByteArrayAttribute(CKA_SUBJECT);
    private final Pkcs11BooleanAttribute mEncryptAttribute = new Pkcs11BooleanAttribute(CKA_ENCRYPT);
    private final Pkcs11BooleanAttribute mVerifyAttribute = new Pkcs11BooleanAttribute(CKA_VERIFY);
    private final Pkcs11BooleanAttribute mVerifyRecoverAttribute = new Pkcs11BooleanAttribute(CKA_VERIFY_RECOVER);
    private final Pkcs11BooleanAttribute mWrapAttribute = new Pkcs11BooleanAttribute(CKA_WRAP);
    private final Pkcs11BooleanAttribute mTrustedAttribute = new Pkcs11BooleanAttribute(CKA_TRUSTED);
    private final Pkcs11ArrayAttribute mWrapTemplateAttribute = new Pkcs11ArrayAttribute(CKA_WRAP_TEMPLATE);
    private final Pkcs11ByteArrayAttribute mPublicKeyInfoAttribute = new Pkcs11ByteArrayAttribute(CKA_PUBLIC_KEY_INFO);

    protected Pkcs11PublicKeyObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11ByteArrayAttribute getSubjectAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mSubjectAttribute);
    }

    public Pkcs11BooleanAttribute getEncryptAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mEncryptAttribute);
    }

    public Pkcs11BooleanAttribute getVerifyAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mVerifyAttribute);
    }

    public Pkcs11BooleanAttribute getVerifyRecoverAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mVerifyRecoverAttribute);
    }

    public Pkcs11BooleanAttribute getWrapAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mWrapAttribute);
    }

    public Pkcs11BooleanAttribute getTrustedAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mTrustedAttribute);
    }

    public Pkcs11ArrayAttribute getWrapTemplateAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mWrapTemplateAttribute);
    }

    public Pkcs11ByteArrayAttribute getPublicKeyInfoAttribute(Pkcs11Session session) {
        return getAttributeValue(session, mPublicKeyInfoAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mSubjectAttribute);
        registerAttribute(mEncryptAttribute);
        registerAttribute(mVerifyAttribute);
        registerAttribute(mVerifyRecoverAttribute);
        registerAttribute(mWrapAttribute);
        registerAttribute(mTrustedAttribute);
        registerAttribute(mWrapTemplateAttribute);
        registerAttribute(mPublicKeyInfoAttribute);
    }
}
