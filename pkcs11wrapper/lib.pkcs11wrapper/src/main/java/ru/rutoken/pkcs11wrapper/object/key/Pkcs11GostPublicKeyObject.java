/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.key;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_GOST28147_PARAMS;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_GOSTR3410_PARAMS;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_GOSTR3411_PARAMS;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_VALUE;

public class Pkcs11GostPublicKeyObject extends Pkcs11PublicKeyObject {
    private final Pkcs11ByteArrayAttribute mValueAttribute = new Pkcs11ByteArrayAttribute(CKA_VALUE);
    private final Pkcs11ByteArrayAttribute mGostR3410ParamsAttribute =
            new Pkcs11ByteArrayAttribute(CKA_GOSTR3410_PARAMS);
    private final Pkcs11ByteArrayAttribute mGostR3411ParamsAttribute =
            new Pkcs11ByteArrayAttribute(CKA_GOSTR3411_PARAMS);
    private final Pkcs11ByteArrayAttribute mGost28147ParamsAttribute =
            new Pkcs11ByteArrayAttribute(CKA_GOST28147_PARAMS);

    protected Pkcs11GostPublicKeyObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11ByteArrayAttribute getValueAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mValueAttribute);
    }

    public Pkcs11ByteArrayAttribute getGostR3410ParamsAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mGostR3410ParamsAttribute);
    }

    public Pkcs11ByteArrayAttribute getGostR3411ParamsAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mGostR3411ParamsAttribute);
    }

    public Pkcs11ByteArrayAttribute getGost28147ParamsAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mGost28147ParamsAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mValueAttribute);
        registerAttribute(mGostR3410ParamsAttribute);
        registerAttribute(mGostR3411ParamsAttribute);
        registerAttribute(mGost28147ParamsAttribute);
    }
}
