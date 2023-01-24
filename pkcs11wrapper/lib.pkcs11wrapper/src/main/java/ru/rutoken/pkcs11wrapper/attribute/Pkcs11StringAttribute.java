/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.attribute;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;

public class Pkcs11StringAttribute extends Pkcs11AbstractAttribute {
    @Nullable
    private String mValue;

    public Pkcs11StringAttribute(IPkcs11AttributeType type) {
        super(type);
    }

    public Pkcs11StringAttribute(IPkcs11AttributeType type, String value) {
        super(type);
        setStringValue(value);
    }

    protected Pkcs11StringAttribute(IPkcs11AttributeType type, @NotNull Object value) {
        super(type);
        setValue(value);
    }

    @Override
    public boolean isEmpty() {
        return null == mValue;
    }

    @NotNull
    @Override
    public Object getValue() {
        return getStringValue();
    }

    @Override
    public void setValue(@NotNull Object value) {
        setStringValue((String) value);
    }

    public String getStringValue() {
        requireNonEmpty();
        return mValue;
    }

    public void setStringValue(String value) {
        mValue = Objects.requireNonNull(value);
    }

    @NotNull
    @Override
    protected String readCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory lowLevelFactory,
                                          IPkcs11AttributeFactory attributeFactory) {
        return attribute.getStringValue();
    }

    @Override
    protected void writeCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory factory) {
        attribute.setStringValue(getStringValue());
    }
}
