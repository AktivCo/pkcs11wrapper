/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.mechanism;

import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11MechanismTypeAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.object.Pkcs11BaseObject;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_MECHANISM_TYPE;

public class Pkcs11MechanismObject extends Pkcs11BaseObject {
    private final Pkcs11MechanismTypeAttribute mMechanismTypeAttribute =
            new Pkcs11MechanismTypeAttribute(CKA_MECHANISM_TYPE);

    protected Pkcs11MechanismObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11MechanismTypeAttribute getMechanismTypeAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mMechanismTypeAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mMechanismTypeAttribute);
    }
}
