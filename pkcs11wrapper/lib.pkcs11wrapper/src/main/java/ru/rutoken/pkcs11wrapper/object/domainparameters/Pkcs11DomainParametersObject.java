/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.domainparameters;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11BooleanAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11KeyTypeAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.object.Pkcs11StorageObject;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_KEY_TYPE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_LOCAL;

public class Pkcs11DomainParametersObject extends Pkcs11StorageObject {
    private final Pkcs11KeyTypeAttribute mKeyTypeAttribute = new Pkcs11KeyTypeAttribute(CKA_KEY_TYPE);
    private final Pkcs11BooleanAttribute mLocalAttribute = new Pkcs11BooleanAttribute(CKA_LOCAL);

    protected Pkcs11DomainParametersObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11KeyTypeAttribute getKeyTypeAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mKeyTypeAttribute);
    }

    public Pkcs11BooleanAttribute getLocalAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mLocalAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mKeyTypeAttribute);
        registerAttribute(mLocalAttribute);
    }
}
