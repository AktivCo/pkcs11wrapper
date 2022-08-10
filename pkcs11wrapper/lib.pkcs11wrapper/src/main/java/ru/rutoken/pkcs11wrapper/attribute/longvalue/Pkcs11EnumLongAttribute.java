package ru.rutoken.pkcs11wrapper.attribute.longvalue;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.AttributeLongValueSupplier;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11VendorExtensions;
import ru.rutoken.pkcs11wrapper.reference.VendorExtensionsReference;

/**
 * Designed to be used as a base class for attributes which values are represented with enums.
 * Supports enums (generally any type) that implements {@link AttributeLongValueSupplier} interface.
 *
 * @param <V> enum type or its supertype
 */
public abstract class Pkcs11EnumLongAttribute<V extends AttributeLongValueSupplier> extends Pkcs11LongAttribute {
    private final Class<V> mValueType;

    protected Pkcs11EnumLongAttribute(Class<V> valueType, IPkcs11AttributeType type) {
        super(type);
        mValueType = Objects.requireNonNull(valueType);
    }

    protected Pkcs11EnumLongAttribute(Class<V> valueType, IPkcs11AttributeType type, @NotNull Object value) {
        super(type);
        mValueType = Objects.requireNonNull(valueType);
        setValue(value);
    }

    public void setEnumValue(V value) {
        setLongValue(value.getAsLong());
    }

    abstract public V getEnumValue();

    abstract public V getEnumValue(IPkcs11VendorExtensions vendorExtensions);

    public V getEnumValue(VendorExtensionsReference reference) {
        return getEnumValue(reference.getVendorExtensions());
    }

    /**
     * Handles enum values correctly
     */
    @Override
    public void setValue(@NotNull Object value) {
        if (mValueType.isAssignableFrom(value.getClass())) {
            @SuppressWarnings("unchecked")
            V typedValue = (V) value;
            setEnumValue(typedValue);
        } else {
            super.setValue(value);
        }
    }
}
