package ru.rutoken.pkcs11wrapper.attribute;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;

public class Pkcs11BooleanAttribute extends Pkcs11AbstractAttribute {
    @Nullable
    private Boolean mValue;

    public Pkcs11BooleanAttribute(IPkcs11AttributeType type) {
        super(type);
    }

    public Pkcs11BooleanAttribute(IPkcs11AttributeType type, boolean value) {
        super(type);
        setBooleanValue(value);
    }

    protected Pkcs11BooleanAttribute(IPkcs11AttributeType type, @NotNull Object value) {
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
        return getBooleanValue();
    }

    @Override
    public void setValue(@NotNull Object value) {
        setBooleanValue((Boolean) value);
    }

    public boolean getBooleanValue() {
        requireNonEmpty();
        return Objects.requireNonNull(mValue);
    }

    public void setBooleanValue(boolean value) {
        mValue = value;
    }

    @NotNull
    @Override
    protected Boolean readCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory lowLevelFactory,
                                           IPkcs11AttributeFactory attributeFactory) {
        return attribute.getBooleanValue();
    }

    @Override
    protected void writeCkAttributeValue(CkAttribute attribute, IPkcs11LowLevelFactory factory) {
        attribute.setBooleanValue(getBooleanValue());
    }
}
