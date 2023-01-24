/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.attribute;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;

/**
 * Represents an array of {@link Pkcs11Attribute} instances. Used to pass an array of attributes as a single attribute.
 * This is a special kind of a single attribute,
 * if you need an array of attributes as a template, use List<Pkcs11Attribute>.
 */
public class Pkcs11ArrayAttribute extends Pkcs11AbstractAttribute {
    private List<Pkcs11Attribute> mValue = new ArrayList<>();

    public Pkcs11ArrayAttribute(IPkcs11AttributeType type) {
        super(type);
    }

    public Pkcs11ArrayAttribute(IPkcs11AttributeType type, List<Pkcs11Attribute> value) {
        super(type);
        setAttributesValue(value);
    }

    protected Pkcs11ArrayAttribute(IPkcs11AttributeType type, @NotNull Object value) {
        super(type);
        setValue(value);
    }

    @Override
    public boolean isEmpty() {
        return mValue.isEmpty();
    }

    @NotNull
    @Override
    public Object getValue() {
        return getAttributesValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setValue(@NotNull Object value) {
        setAttributesValue((List<Pkcs11Attribute>) value);
    }

    public List<Pkcs11Attribute> getAttributesValue() {
        requireNonEmpty();
        return mValue;
    }

    public void setAttributesValue(List<Pkcs11Attribute> attributes) {
        mValue = Objects.requireNonNull(attributes);
    }

    @NotNull
    @Override
    protected List<Pkcs11Attribute> readCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory lowLevelFactory,
                                                         IPkcs11AttributeFactory attributeFactory) {
        final List<Pkcs11Attribute> attributes = new ArrayList<>();
        for (CkAttribute ckAttribute : attribute.getAttributesValue(lowLevelFactory))
            attributes.add(attributeFactory.makeAttribute(
                    IPkcs11AttributeType.getInstance(ckAttribute.getType(), lowLevelFactory),
                    attribute.getValue()));

        return attributes;
    }

    @Override
    protected void writeCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory factory) {
        final List<CkAttribute> ckAttributes = new ArrayList<>();
        for (Pkcs11Attribute pkcs11Attribute : getAttributesValue())
            ckAttributes.add(pkcs11Attribute.toCkAttribute(factory));

        attribute.setAttributesValue(ckAttributes);
    }
}
