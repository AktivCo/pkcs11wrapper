/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.attribute;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;

public abstract class Pkcs11AbstractAttribute implements Pkcs11Attribute {
    private final IPkcs11AttributeType mType;
    private boolean mSensitive = false;
    private boolean mPresent = false;

    protected Pkcs11AbstractAttribute(IPkcs11AttributeType type) {
        mType = Objects.requireNonNull(type);
    }

    @Override
    public IPkcs11AttributeType getType() {
        return mType;
    }

    @Override
    public boolean isPresent() {
        return mPresent;
    }

    @Override
    public void setPresent(boolean value) {
        mPresent = value;
    }

    @Override
    public boolean isSensitive() {
        return mSensitive;
    }

    @Override
    public void setSensitive(@SuppressWarnings("SameParameterValue") boolean value) {
        mSensitive = value;
    }

    @Override
    public void assignFromCkAttribute(CkAttribute from, IPkcs11LowLevelFactory lowLevelFactory,
                                      IPkcs11AttributeFactory attributeFactory) {
        if (from.getType() != mType.getAsLong())
            throw new IllegalArgumentException(
                    "different attribute type, trying to assign " + from.getType() + " to " + mType.getAsLong());
        if (!from.isEmpty())
            setValue(readCkAttributeValue(from, lowLevelFactory, attributeFactory));
    }

    @Override
    public CkAttribute toCkAttribute(IPkcs11LowLevelFactory factory) {
        final CkAttribute attribute = factory.makeAttribute();
        attribute.setType(getType().getAsLong());
        if (!isEmpty())
            writeCkAttributeValue(attribute, factory);
        return attribute;
    }

    /**
     * Reads and simply returns value from {@code attribute}, {@code attribute} value must be not empty.
     *
     * @param attribute        attribute to read value from
     * @param lowLevelFactory  factory for low-level objects creation
     * @param attributeFactory factory for attributes creation
     * @return value of the attribute; the type of the value depends on the implementation of the attribute
     */
    @NotNull
    abstract protected Object readCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory lowLevelFactory,
                                                   IPkcs11AttributeFactory attributeFactory);

    /**
     * Writes current attribute value into {@code attribute}. Current value must be not empty.
     *
     * @param attribute attribute to write value to
     * @param factory   factory for low-level objects creation
     */
    abstract protected void writeCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory factory);

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject instanceof Pkcs11AbstractAttribute) {
            Pkcs11AbstractAttribute other = (Pkcs11AbstractAttribute) otherObject;
            if (this == other)
                return true;

            return Objects.equals(mType, other.mType) &&
                    Objects.equals(getValue(), other.getValue()) &&
                    mSensitive == other.mSensitive && mPresent == other.mPresent;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int attributeHash = Objects.hash(mType, getValue());
        return Objects.hash(attributeHash, mType, mSensitive, mPresent);
    }

    protected final void requireNonEmpty() {
        if (isEmpty())
            throw new IllegalStateException(getType() + " attribute not set");
    }
}
