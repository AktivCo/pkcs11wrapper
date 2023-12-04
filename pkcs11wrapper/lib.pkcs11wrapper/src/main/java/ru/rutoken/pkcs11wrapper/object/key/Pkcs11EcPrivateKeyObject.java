/*
 * Copyright (c) 2023, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.key;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_EC_PARAMS;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_VALUE;

public class Pkcs11EcPrivateKeyObject extends Pkcs11PrivateKeyObject {
    private final Pkcs11ByteArrayAttribute mEcParamsAttribute = new Pkcs11ByteArrayAttribute(CKA_EC_PARAMS);
    private final Pkcs11ByteArrayAttribute mValueAttribute = new Pkcs11ByteArrayAttribute(CKA_VALUE);

    protected Pkcs11EcPrivateKeyObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11ByteArrayAttribute getEcParamsAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mEcParamsAttribute);
    }

    public Pkcs11ByteArrayAttribute getValueAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mValueAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mEcParamsAttribute);
        registerAttribute(mValueAttribute);
    }
}
