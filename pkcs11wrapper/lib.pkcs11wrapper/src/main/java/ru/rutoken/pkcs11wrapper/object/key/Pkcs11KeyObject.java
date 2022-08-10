package ru.rutoken.pkcs11wrapper.object.key;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11BooleanAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11DateAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11MechanismTypeArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11KeyTypeAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11MechanismTypeAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.object.Pkcs11StorageObject;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_ALLOWED_MECHANISMS;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_DERIVE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_END_DATE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_ID;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_KEY_GEN_MECHANISM;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_KEY_TYPE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_LOCAL;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_START_DATE;

public class Pkcs11KeyObject extends Pkcs11StorageObject {
    private final Pkcs11KeyTypeAttribute mKeyTypeAttribute = new Pkcs11KeyTypeAttribute(CKA_KEY_TYPE);
    private final Pkcs11ByteArrayAttribute mIdAttribute = new Pkcs11ByteArrayAttribute(CKA_ID);
    private final Pkcs11DateAttribute mStartDateAttribute = new Pkcs11DateAttribute(CKA_START_DATE);
    private final Pkcs11DateAttribute mEndDateAttribute = new Pkcs11DateAttribute(CKA_END_DATE);
    private final Pkcs11BooleanAttribute mDeriveAttribute = new Pkcs11BooleanAttribute(CKA_DERIVE);
    private final Pkcs11BooleanAttribute mLocalAttribute = new Pkcs11BooleanAttribute(CKA_LOCAL);
    private final Pkcs11MechanismTypeAttribute mKeyGenMechanismAttribute =
            new Pkcs11MechanismTypeAttribute(CKA_KEY_GEN_MECHANISM);
    private final Pkcs11MechanismTypeArrayAttribute mAllowedMechanismsAttribute =
            new Pkcs11MechanismTypeArrayAttribute(CKA_ALLOWED_MECHANISMS);

    protected Pkcs11KeyObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11KeyTypeAttribute getKeyTypeAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mKeyTypeAttribute);
    }

    public Pkcs11ByteArrayAttribute getIdAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mIdAttribute);
    }

    public Pkcs11DateAttribute getStartDateAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mStartDateAttribute);
    }

    public Pkcs11DateAttribute getEndDateAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mEndDateAttribute);
    }

    public Pkcs11BooleanAttribute getDeriveAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mDeriveAttribute);
    }

    public Pkcs11BooleanAttribute getLocalAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mLocalAttribute);
    }

    public Pkcs11MechanismTypeAttribute getKeyGenMechanismAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mKeyGenMechanismAttribute);
    }

    public Pkcs11MechanismTypeArrayAttribute getAllowedMechanismsAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mAllowedMechanismsAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mKeyTypeAttribute);
        registerAttribute(mIdAttribute);
        registerAttribute(mStartDateAttribute);
        registerAttribute(mEndDateAttribute);
        registerAttribute(mDeriveAttribute);
        registerAttribute(mLocalAttribute);
        registerAttribute(mKeyGenMechanismAttribute);
        registerAttribute(mAllowedMechanismsAttribute);
    }
}
