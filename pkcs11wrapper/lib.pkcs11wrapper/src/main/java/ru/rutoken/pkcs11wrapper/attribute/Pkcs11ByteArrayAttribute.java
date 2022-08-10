package ru.rutoken.pkcs11wrapper.attribute;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;

public class Pkcs11ByteArrayAttribute extends Pkcs11AbstractAttribute {
    private byte @Nullable [] mValue;

    public Pkcs11ByteArrayAttribute(IPkcs11AttributeType type) {
        super(type);
    }

    public Pkcs11ByteArrayAttribute(IPkcs11AttributeType type, byte[] value) {
        super(type);
        setByteArrayValue(value);
    }

    protected Pkcs11ByteArrayAttribute(IPkcs11AttributeType type, @NotNull Object value) {
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
        return getByteArrayValue();
    }

    @Override
    public void setValue(@NotNull Object value) {
        setByteArrayValue((byte[]) value);
    }

    public byte[] getByteArrayValue() {
        requireNonEmpty();
        return mValue;
    }

    public void setByteArrayValue(byte[] value) {
        mValue = Objects.requireNonNull(value);
    }

    @Override
    protected byte @NotNull [] readCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory lowLevelFactory,
                                                    IPkcs11AttributeFactory attributeFactory) {
        return attribute.getByteArrayValue();
    }

    @Override
    protected void writeCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory factory) {
        attribute.setByteArrayValue(getByteArrayValue());
    }
}
