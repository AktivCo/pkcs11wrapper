/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object;

import java.util.List;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11BooleanAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11DateAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11StringAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11LongAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11ObjectClassAttribute;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.object.factory.AttributeRegistrator;

/**
 * Represents pkcs11 object.
 * Provides generic API for all kind of objects,
 *
 * @see Pkcs11BaseObject for more info.
 */
public interface Pkcs11Object extends AttributeRegistrator {

    long getHandle();

    Pkcs11ObjectClassAttribute getClassAttributeValue(Pkcs11Session session);

    /**
     * Read one attribute from token.
     * There is no checks that specified attribute type is a member/property of this object.
     */
    default Pkcs11Attribute getAttributeValue(Pkcs11Session session, IPkcs11AttributeType type) {
        return getAttributeValue(Pkcs11Attribute.class, session, type);
    }

    /**
     * Read one attribute of specified class from token.
     * There is no checks that specified attribute type is a member/property of this object.
     */
    <Attr extends Pkcs11Attribute> Attr getAttributeValue(Class<Attr> attributeClass,
                                                          Pkcs11Session session,
                                                          IPkcs11AttributeType type);

    default Pkcs11LongAttribute getLongAttributeValue(Pkcs11Session session, IPkcs11AttributeType type) {
        return getAttributeValue(Pkcs11LongAttribute.class, session, type);
    }

    default Pkcs11StringAttribute getStringAttributeValue(Pkcs11Session session, IPkcs11AttributeType type) {
        return getAttributeValue(Pkcs11StringAttribute.class, session, type);
    }

    default Pkcs11BooleanAttribute getBooleanAttributeValue(Pkcs11Session session, IPkcs11AttributeType type) {
        return getAttributeValue(Pkcs11BooleanAttribute.class, session, type);
    }

    default Pkcs11ByteArrayAttribute getByteArrayAttributeValue(Pkcs11Session session, IPkcs11AttributeType type) {
        return getAttributeValue(Pkcs11ByteArrayAttribute.class, session, type);
    }

    default Pkcs11DateAttribute getDateAttributeValue(Pkcs11Session session, IPkcs11AttributeType type) {
        return getAttributeValue(Pkcs11DateAttribute.class, session, type);
    }

    /**
     * Read specified attributes from token
     */
    List<Pkcs11Attribute> getAttributeValues(Pkcs11Session session, List<Pkcs11Attribute> attributes);

    /**
     * Read all attributes from token
     */
    List<Pkcs11Attribute> getAttributeValues(Pkcs11Session session);

    void setAttributeValues(Pkcs11Session session, List<Pkcs11Attribute> attributes);
}
