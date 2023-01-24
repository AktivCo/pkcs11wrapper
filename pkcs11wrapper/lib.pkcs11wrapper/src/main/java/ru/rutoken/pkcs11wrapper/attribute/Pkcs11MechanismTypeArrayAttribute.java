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
import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;

/**
 * This type of an attribute represents array of {@link IPkcs11MechanismType} instances.
 */
public class Pkcs11MechanismTypeArrayAttribute extends Pkcs11AbstractAttribute {
    private List<IPkcs11MechanismType> mValue = new ArrayList<>();

    public Pkcs11MechanismTypeArrayAttribute(IPkcs11AttributeType type) {
        super(type);
    }

    public Pkcs11MechanismTypeArrayAttribute(IPkcs11AttributeType type, List<IPkcs11MechanismType> value) {
        super(type);
        setMechanismTypesValue(value);
    }

    protected Pkcs11MechanismTypeArrayAttribute(IPkcs11AttributeType type, @NotNull Object value) {
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
        return getMechanismTypesValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setValue(@NotNull Object value) {
        setMechanismTypesValue((List<IPkcs11MechanismType>) value);
    }

    public List<IPkcs11MechanismType> getMechanismTypesValue() {
        requireNonEmpty();
        return mValue;
    }

    public void setMechanismTypesValue(List<IPkcs11MechanismType> mechanismTypes) {
        mValue = Objects.requireNonNull(mechanismTypes);
    }

    @NotNull
    @Override
    protected List<IPkcs11MechanismType> readCkAttributeValue(CkAttribute attribute,
                                                              IPkcs11LowLevelFactory lowLevelFactory,
                                                              IPkcs11AttributeFactory attributeFactory) {
        final List<IPkcs11MechanismType> mechanismTypes = new ArrayList<>();
        for (long type : attribute.getLongArrayValue())
            mechanismTypes.add(IPkcs11MechanismType.getInstance(type, lowLevelFactory));

        return mechanismTypes;
    }

    @Override
    protected void writeCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory factory) {
        final List<Long> mechanismTypes = new ArrayList<>();
        for (IPkcs11MechanismType type : getMechanismTypesValue())
            mechanismTypes.add(type.getAsLong());

        attribute.setLongArrayValue(mechanismTypes);
    }
}
