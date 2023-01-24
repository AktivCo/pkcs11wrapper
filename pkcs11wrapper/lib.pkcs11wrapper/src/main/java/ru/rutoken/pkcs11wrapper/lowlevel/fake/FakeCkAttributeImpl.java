/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkDate;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;

class FakeCkAttributeImpl implements CkAttribute {
    private static final int NATIVE_LONG_BYTES = 4; // just hardcoding the value to avoid jna dependency
    private IPkcs11AttributeType mType;
    private Object mValue;
    private long mValueLen;

    IPkcs11AttributeType getPkcs11Type() {
        return mType;
    }

    @Override
    public long getType() {
        return mType.getAsLong();
    }

    @Override
    public void setType(long type) {
        mType = IPkcs11AttributeType.getInstance(type);
    }

    @NotNull
    @Override
    public Object getValue() {
        return mValue;
    }

    @Override
    public void setValue(@NotNull Object value) {
        mValue = Objects.requireNonNull(value);
    }

    @Override
    public long getLongValue() {
        return (long) mValue;
    }

    @Override
    public void setLongValue(long value) {
        mValue = value;
        mValueLen = NATIVE_LONG_BYTES;
    }

    @Override
    public String getStringValue() {
        return (String) mValue;
    }

    @Override
    public void setStringValue(String value) {
        mValue = Objects.requireNonNull(value);
        mValueLen = value.length();
    }

    @Override
    public boolean getBooleanValue() {
        return (boolean) mValue;
    }

    @Override
    public void setBooleanValue(boolean value) {
        mValue = value;
        mValueLen = 1;
    }

    @Override
    public byte[] getByteArrayValue() {
        return (byte[]) mValue;
    }

    @Override
    public void setByteArrayValue(byte[] value) {
        mValue = Objects.requireNonNull(value);
        mValueLen = value.length;
    }

    @Override
    public CkDate getDateValue(IPkcs11LowLevelFactory factory) {
        return (CkDate) mValue;
    }

    @Override
    public void setDateValue(CkDate value) {
        mValue = Objects.requireNonNull(value);
        mValueLen = 4 + 2 + 2;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CkAttribute> getAttributesValue(IPkcs11LowLevelFactory factory) {
        return (List<CkAttribute>) mValue;
    }

    @Override
    public void setAttributesValue(List<CkAttribute> value) {
        mValue = Objects.requireNonNull(value);
        mValueLen = value.size(); // FIXME * sizeof(attribute)
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> getLongArrayValue() {
        return (List<Long>) mValue;
    }

    @Override
    public void setLongArrayValue(List<Long> value) {
        mValue = Objects.requireNonNull(value);
        mValueLen = (long) value.size() * NATIVE_LONG_BYTES;
    }

    @Override
    public long getValueLen() {
        return mValueLen;
    }

    void setValueLen(long length) {
        mValueLen = length;
    }

    @Override
    public void allocate(long size) {
        mValueLen = size;
    }
}
