package ru.rutoken.pkcs11wrapper.attribute.longvalue;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11AbstractAttribute;
import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;

/**
 * Represents a high-level attribute of long type.
 * Supports values of {@link Long} and {@link Integer} Java types.
 */
public class Pkcs11LongAttribute extends Pkcs11AbstractAttribute {
    @Nullable
    private Long mValue;

    public Pkcs11LongAttribute(IPkcs11AttributeType type) {
        super(type);
    }

    public Pkcs11LongAttribute(IPkcs11AttributeType type, long value) {
        super(type);
        setLongValue(value);
    }

    protected Pkcs11LongAttribute(IPkcs11AttributeType type, @NotNull Object value) {
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
        return getLongValue();
    }

    @Override
    public void setValue(@NotNull Object value) {
        if (value instanceof Integer)
            setLongValue(((Integer) value).longValue());
        else
            setLongValue((Long) value);
    }

    public long getLongValue() {
        requireNonEmpty();
        return Objects.requireNonNull(mValue);
    }

    public void setLongValue(long value) {
        mValue = value;
    }

    @NotNull
    @Override
    protected Long readCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory lowLevelFactory,
                                        IPkcs11AttributeFactory attributeFactory) {
        return attribute.getLongValue();
    }

    @Override
    protected void writeCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory factory) {
        attribute.setLongValue(getLongValue());
    }
}
