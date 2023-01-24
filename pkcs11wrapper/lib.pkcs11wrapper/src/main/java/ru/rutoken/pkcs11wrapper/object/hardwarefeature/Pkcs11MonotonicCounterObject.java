/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.hardwarefeature;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11BooleanAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_HAS_RESET;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_RESET_ON_INIT;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_VALUE;

public class Pkcs11MonotonicCounterObject extends Pkcs11HardwareFeatureObject {
    private final Pkcs11BooleanAttribute mResetOnInitAttribute = new Pkcs11BooleanAttribute(CKA_RESET_ON_INIT);
    private final Pkcs11BooleanAttribute mHasResetAttribute = new Pkcs11BooleanAttribute(CKA_HAS_RESET);
    private final Pkcs11ByteArrayAttribute mValueAttribute = new Pkcs11ByteArrayAttribute(CKA_VALUE);

    protected Pkcs11MonotonicCounterObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11BooleanAttribute getResetOnInitAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mResetOnInitAttribute);
    }

    public Pkcs11BooleanAttribute getHasResetAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mHasResetAttribute);
    }

    public Pkcs11ByteArrayAttribute getValueAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mValueAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mResetOnInitAttribute);
        registerAttribute(mHasResetAttribute);
        registerAttribute(mValueAttribute);
    }
}


