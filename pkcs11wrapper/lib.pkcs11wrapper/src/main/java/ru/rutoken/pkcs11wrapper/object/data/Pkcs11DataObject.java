/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.data;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11StringAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.object.Pkcs11StorageObject;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_APPLICATION;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_OBJECT_ID;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_VALUE;

public class Pkcs11DataObject extends Pkcs11StorageObject {
    private final Pkcs11StringAttribute mApplicationAttribute = new Pkcs11StringAttribute(CKA_APPLICATION);
    private final Pkcs11ByteArrayAttribute mObjectIdAttribute = new Pkcs11ByteArrayAttribute(CKA_OBJECT_ID);
    private final Pkcs11ByteArrayAttribute mValueAttribute = new Pkcs11ByteArrayAttribute(CKA_VALUE);

    protected Pkcs11DataObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11StringAttribute getApplicationAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mApplicationAttribute);
    }

    public Pkcs11ByteArrayAttribute getObjectIdAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mObjectIdAttribute);
    }

    public Pkcs11ByteArrayAttribute getValueAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mValueAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mApplicationAttribute);
        registerAttribute(mObjectIdAttribute);
        registerAttribute(mValueAttribute);
    }
}
