package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import static ru.rutoken.pkcs11wrapper.lowlevel.jna.Pkcs11JnaLowLevelApi.unsigned;

import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_ATTRIBUTE;
import ru.rutoken.pkcs11jna.CK_DATE;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkDate;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;

class CkAttributeImpl implements CkAttribute {
    private final CK_ATTRIBUTE mData;

    CkAttributeImpl(CK_ATTRIBUTE data) {
        mData = Objects.requireNonNull(data);
    }

    void copyToJnaAttribute(CK_ATTRIBUTE to) {
        to.type.setValue(getType());
        to.pValue = mData.pValue;
        to.ulValueLen.setValue(getValueLen());
    }

    void assignFromJnaAttribute(CK_ATTRIBUTE from) {
        setType(unsigned(from.type));
        // may be null if an attribute is empty
        mData.pValue = from.pValue;
        setValueLen(from.ulValueLen.longValue());
    }

    @Override
    public long getType() {
        return mData.type.longValue();
    }

    @Override
    public void setType(long type) {
        mData.type.setValue(type);
    }

    @NotNull
    @Override
    public Object getValue() {
        requireNonEmpty();
        return mData.pValue;
    }

    @Override
    public void setValue(@NotNull Object value) {
        mData.pValue = (Pointer) Objects.requireNonNull(value);
    }

    @Override
    public long getLongValue() {
        requireNonEmpty();
        return unsigned(mData.pValue.getNativeLong(0));
    }

    @Override
    public void setLongValue(long value) {
        mData.setAttr(mData.type, new NativeLong(value));
    }

    @Override
    public String getStringValue() {
        return new String(getByteArrayValue(), StandardCharsets.UTF_8);
    }

    @Override
    public void setStringValue(String value) {
        mData.setAttr(mData.type, value);
    }

    @Override
    public boolean getBooleanValue() {
        requireNonEmpty();
        return 0 != mData.pValue.getByte(0);
    }

    @Override
    public void setBooleanValue(boolean value) {
        mData.setAttr(mData.type, value);
    }

    @Override
    public byte[] getByteArrayValue() {
        requireNonEmpty();
        return mData.pValue.getByteArray(0, mData.ulValueLen.intValue());
    }

    @Override
    public void setByteArrayValue(byte[] value) {
        mData.setAttr(mData.type, value);
    }

    @Override
    public CkDate getDateValue(IPkcs11LowLevelFactory factory) {
        requireNonEmpty();
        return ((Pkcs11BaseJnaLowLevelFactory<?>) factory).makeDate(mData.pValue);
    }

    @Override
    public void setDateValue(CkDate value) {
        final CK_DATE date = ((CkDateImpl) value).getJnaValue();
        date.write();
        mData.setAttr(mData.type, date.getPointer(), new NativeLong(date.size()));
    }

    @Override
    public List<CkAttribute> getAttributesValue(IPkcs11LowLevelFactory factory) {
        requireNonEmpty();
        final int amount = (int) getValueLen() / mData.size();
        final List<CkAttribute> attributes = new ArrayList<>(amount);
        for (int a = 0; a < amount; a++) {
            final CkAttribute attribute = factory.makeAttribute();
            // TODO implement, fill attribute from mData
            attributes.add(attribute);
        }

        return attributes;
    }

    @Override
    public void setAttributesValue(List<CkAttribute> value) {
        allocate((long) value.size() * mData.size());
        for (int a = 0; a < value.size(); a++) {
            // TODO implement, fill mData memory with attributes
            //mData.pValue.write(a * mData.size(), );
        }
    }

    @Override
    public List<Long> getLongArrayValue() {
        requireNonEmpty();
        final List<Long> longs = new ArrayList<>();
        for (int offset = 0; offset < getValueLen(); offset += NativeLong.SIZE)
            longs.add(unsigned(mData.pValue.getNativeLong(offset)));

        return longs;
    }

    @Override
    public void setLongArrayValue(List<Long> value) {
        allocate((long) value.size() * NativeLong.SIZE);
        for (int t = 0; t < value.size(); t++)
            mData.pValue.setNativeLong((long) t * NativeLong.SIZE, new NativeLong(value.get(t), true));
    }

    @Override
    public long getValueLen() {
        return mData.ulValueLen.longValue();
    }

    private void setValueLen(long length) {
        mData.ulValueLen.setValue(length);
    }

    @Override
    public void allocate(long size) {
        mData.pValue = new Memory(size);
        setValueLen(size);
    }

    private void requireNonEmpty() {
        if (isEmpty())
            throw new IllegalStateException(getType() + " attribute not set");
    }
}
