/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_CLASS;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.rutoken.pkcs11wrapper.attribute.Attributes;
import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11ObjectClassAttribute;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.reference.AttributeFactoryReference;

/**
 * Base implementation for pkcs11 objects.
 * This is concrete class. Every pkcs11 object may be represented by this class or by more concrete implementation.
 * Using this class with its generic API a user can manipulate all kinds of objects.
 * Subclasses provide specific methods for concrete object type.
 * Subclasses should not be abstract, so a user can instantiate them to work through their interface.
 */
public class Pkcs11BaseObject implements Pkcs11Object {
    private final long mObjectHandle;
    private final Map<IPkcs11AttributeType, Pkcs11Attribute> mAttributes = new HashMap<>();
    private final Pkcs11ObjectClassAttribute mClassAttribute = new Pkcs11ObjectClassAttribute(CKA_CLASS);

    /**
     * Constructor for object creation by reading attributes from token
     *
     * @param objectHandle token's object handle
     */
    protected Pkcs11BaseObject(long objectHandle) {
        mObjectHandle = objectHandle;
    }

    private static <Attr extends Pkcs11Attribute> Attr makeAttribute(Class<Attr> attributeClass,
                                                                     IPkcs11AttributeFactory factory,
                                                                     IPkcs11AttributeType type) {
        return factory.makeAttribute(attributeClass, type);
    }

    @Override
    public long getHandle() {
        return mObjectHandle;
    }

    @Override
    public Pkcs11ObjectClassAttribute getClassAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mClassAttribute);
    }

    @Override
    public <Attr extends Pkcs11Attribute> Attr getAttributeValue(Class<Attr> attributeClass,
                                                                 Pkcs11Session session,
                                                                 IPkcs11AttributeType type) {
        final Attr attribute = findAttribute(attributeClass, type);
        return getAttributeValue(session, attribute != null ? attribute : makeAttribute(attributeClass,
                session.getAttributeFactory(), type));
    }

    @Override
    public List<Pkcs11Attribute> getAttributeValues(Pkcs11Session session, List<Pkcs11Attribute> attributes) {
        Attributes.getAttributeValues(session, mObjectHandle, attributes);
        return attributes;
    }

    @Override
    public List<Pkcs11Attribute> getAttributeValues(Pkcs11Session session) {
        return getAttributeValues(session, getAttributes());
    }

    @Override
    public void setAttributeValues(Pkcs11Session session, List<Pkcs11Attribute> attributes) {
        final List<CkAttribute> ckAttributes = Attributes.makeCkAttributesList(
                attributes, session.getLowLevelFactory());
        session.getApi().C_SetAttributeValue(session.getSessionHandle(), mObjectHandle, ckAttributes);
    }

    /**
     * Makes template from all object attributes. Values of attributes are not set.
     *
     * @param factory factory for attributes creation
     * @return attributes template
     */
    public List<Pkcs11Attribute> makeClearTemplate(IPkcs11AttributeFactory factory) {
        final List<Pkcs11Attribute> template = new ArrayList<>();
        for (Pkcs11Attribute attribute : getAttributes()) {
            template.add(factory.makeAttribute(attribute.getType()));
        }
        return template;
    }

    /**
     * Makes template from all object attributes. Values of attributes are not set.
     *
     * @param reference reference-class containing factory for attributes creation
     * @return attributes template
     */
    public List<Pkcs11Attribute> makeClearTemplate(AttributeFactoryReference reference) {
        return makeClearTemplate(reference.getAttributeFactory());
    }

    @Override
    public String toString() {
        return "Pkcs11BaseObject{" +
                "mObjectHandle=" + mObjectHandle +
                ", mAttributes=" + mAttributes +
                '}';
    }

    /**
     * For derived classes to register their own attributes it is necessary to override this method, call super class
     * implementation and register desired attributes.
     */
    @Override
    public void registerAttributes() {
        registerAttribute(mClassAttribute);
    }

    protected void registerAttribute(Pkcs11Attribute attribute) {
        if (mAttributes.containsKey(attribute.getType()))
            throw new IllegalArgumentException("attribute already registered " + attribute);
        mAttributes.put(attribute.getType(), attribute);
    }

    protected <A extends Pkcs11Attribute> A getAttributeValue(Pkcs11Session session, A attribute) {
        Attributes.getAttributeValue(session, mObjectHandle, attribute);
        return attribute;
    }

    /**
     * Get attribute casting it to specified class. Type check is performed.
     *
     * @param attributeClass class of the attribute to cast to
     * @param type           type of the attribute
     * @return found attribute
     * @throws ClassCastException exception will be thrown if user passes inconsistent type and class
     */
    @Nullable
    private <Attr extends Pkcs11Attribute> Attr findAttribute(Class<Attr> attributeClass,
                                                              IPkcs11AttributeType type) {
        final Pkcs11Attribute attribute = mAttributes.get(type);
        if (attribute == null)
            return null;

        if (!attributeClass.isInstance(attribute))
            throw new ClassCastException("attribute class is invalid " + attributeClass);

        @SuppressWarnings("unchecked") final Attr result = (Attr) attribute;
        return result;
    }

    private List<Pkcs11Attribute> getAttributes() {
        return new ArrayList<>(mAttributes.values());
    }
}
