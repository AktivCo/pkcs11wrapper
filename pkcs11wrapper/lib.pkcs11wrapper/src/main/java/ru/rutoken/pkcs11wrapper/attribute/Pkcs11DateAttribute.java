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
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11Date;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;

public class Pkcs11DateAttribute extends Pkcs11AbstractAttribute {
    @Nullable
    private Pkcs11Date mValue;

    public Pkcs11DateAttribute(IPkcs11AttributeType type) {
        super(type);
    }

    public Pkcs11DateAttribute(IPkcs11AttributeType type, Pkcs11Date value) {
        super(type);
        setDateValue(value);
    }

    protected Pkcs11DateAttribute(IPkcs11AttributeType type, @NotNull Object value) {
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
        return getDateValue();
    }

    @Override
    public void setValue(@NotNull Object value) {
        setDateValue((Pkcs11Date) value);
    }

    public Pkcs11Date getDateValue() {
        requireNonEmpty();
        return mValue;
    }

    public void setDateValue(Pkcs11Date date) {
        mValue = Objects.requireNonNull(date);
    }

    @NotNull
    @Override
    protected Pkcs11Date readCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory lowLevelFactory,
                                              IPkcs11AttributeFactory attributeFactory) {
        return new Pkcs11Date(attribute.getDateValue(lowLevelFactory));
    }

    @Override
    protected void writeCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory factory) {
        attribute.setDateValue(getDateValue().toCkDate(factory));
    }
}
