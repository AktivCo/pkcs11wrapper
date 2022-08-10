package ru.rutoken.pkcs11wrapper.rutoken.attribute;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11AbstractAttribute;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;

public class RtPkcs11ByteAttribute extends Pkcs11AbstractAttribute {
    @Nullable
    private Byte mValue;

    public RtPkcs11ByteAttribute(IPkcs11AttributeType type) {
        super(type);
    }

    public RtPkcs11ByteAttribute(IPkcs11AttributeType type, byte value) {
        super(type);
        setByteValue(value);
    }

    protected RtPkcs11ByteAttribute(IPkcs11AttributeType type, @NotNull Object value) {
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
        return getByteValue();
    }

    @Override
    public void setValue(@NotNull Object value) {
        setByteValue((Byte) value);
    }

    public byte getByteValue() {
        requireNonEmpty();
        return Objects.requireNonNull(mValue);
    }

    public void setByteValue(byte value) {
        mValue = value;
    }

    @NotNull
    @Override
    protected Byte readCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory lowLevelFactory,
                                        IPkcs11AttributeFactory attributeFactory) {
        return attribute.getByteArrayValue()[0];
    }

    @Override
    protected void writeCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory factory) {
        attribute.setByteArrayValue(new byte[]{getByteValue()});
    }
}
