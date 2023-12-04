/*
 * Copyright (c) 2023, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.key;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_EC_PARAMS;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_EC_POINT;

public class Pkcs11EcPublicKeyObject extends Pkcs11PublicKeyObject {
    private final Pkcs11ByteArrayAttribute mEcPointAttribute = new Pkcs11ByteArrayAttribute(CKA_EC_POINT);
    private final Pkcs11ByteArrayAttribute mEcParamsAttribute = new Pkcs11ByteArrayAttribute(CKA_EC_PARAMS);

    protected Pkcs11EcPublicKeyObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11ByteArrayAttribute getEcPointAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mEcPointAttribute);
    }

    public Pkcs11ByteArrayAttribute getEcParamsAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mEcParamsAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mEcPointAttribute);
        registerAttribute(mEcParamsAttribute);
    }
}
