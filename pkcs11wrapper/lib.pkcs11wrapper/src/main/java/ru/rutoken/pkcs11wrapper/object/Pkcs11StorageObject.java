/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_COPYABLE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_DESTROYABLE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_LABEL;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_MODIFIABLE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_PRIVATE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_TOKEN;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11BooleanAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11StringAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;

public class Pkcs11StorageObject extends Pkcs11BaseObject {
    private final Pkcs11BooleanAttribute mTokenAttribute = new Pkcs11BooleanAttribute(CKA_TOKEN);
    private final Pkcs11BooleanAttribute mPrivateAttribute = new Pkcs11BooleanAttribute(CKA_PRIVATE);
    private final Pkcs11StringAttribute mLabelAttribute = new Pkcs11StringAttribute(CKA_LABEL);
    private final Pkcs11BooleanAttribute mModifiableAttribute = new Pkcs11BooleanAttribute(CKA_MODIFIABLE);
    private final Pkcs11BooleanAttribute mCopyableAttribute = new Pkcs11BooleanAttribute(CKA_COPYABLE);
    private final Pkcs11BooleanAttribute mDestroyableAttribute = new Pkcs11BooleanAttribute(CKA_DESTROYABLE);

    protected Pkcs11StorageObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11BooleanAttribute getTokenAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mTokenAttribute);
    }

    public Pkcs11BooleanAttribute getPrivateAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mPrivateAttribute);
    }

    public Pkcs11StringAttribute getLabelAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mLabelAttribute);
    }

    public Pkcs11BooleanAttribute getModifiableAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mModifiableAttribute);
    }

    public Pkcs11BooleanAttribute getCopyableAttribute(Pkcs11Session session) {
        return getAttributeValue(session, mCopyableAttribute);
    }

    public Pkcs11BooleanAttribute getDestroyableAttribute(Pkcs11Session session) {
        return getAttributeValue(session, mDestroyableAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mTokenAttribute);
        registerAttribute(mPrivateAttribute);
        registerAttribute(mModifiableAttribute);
        registerAttribute(mLabelAttribute);
        registerAttribute(mCopyableAttribute);
        registerAttribute(mDestroyableAttribute);
    }
}
