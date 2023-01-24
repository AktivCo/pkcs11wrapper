/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.attribute;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11AbstractAttribute;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;

public class RtPkcs11LongArrayAttribute extends Pkcs11AbstractAttribute {
    @Nullable
    private List<Long> mValue;

    public RtPkcs11LongArrayAttribute(IPkcs11AttributeType type) {
        super(type);
    }

    public RtPkcs11LongArrayAttribute(IPkcs11AttributeType type, List<Long> value) {
        super(type);
        setLongArrayValue(value);
    }

    protected RtPkcs11LongArrayAttribute(IPkcs11AttributeType type, @NotNull Object value) {
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
        return getLongArrayValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setValue(@NotNull Object value) {
        setLongArrayValue((List<Long>) value);
    }

    public List<Long> getLongArrayValue() {
        requireNonEmpty();
        return mValue;
    }

    public void setLongArrayValue(List<Long> value) {
        mValue = Objects.requireNonNull(value);
    }

    @NotNull
    @Override
    protected List<Long> readCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory lowLevelFactory,
                                              IPkcs11AttributeFactory attributeFactory) {
        return attribute.getLongArrayValue();
    }

    @Override
    protected void writeCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory factory) {
        attribute.setLongArrayValue(getLongArrayValue());
    }
}
