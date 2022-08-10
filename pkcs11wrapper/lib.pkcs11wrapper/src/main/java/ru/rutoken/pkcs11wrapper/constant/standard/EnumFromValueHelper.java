package ru.rutoken.pkcs11wrapper.constant.standard;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;

/**
 * Helper class for getting enum constant by long value.
 */
public class EnumFromValueHelper<E extends Enum<E> & LongValueSupplier> {
    @Nullable
    public E nullableFromValue(long value, Class<E> enumClass) {
        for (E val : Objects.requireNonNull(enumClass.getEnumConstants())) {
            if (val.getAsLong() == value)
                return val;
        }
        return null;
    }

    public E fromValue(long value, Class<E> enumClass) {
        final E val = nullableFromValue(value, enumClass);
        if (null == val)
            throw new IllegalArgumentException(String.format("value: 0x%08X", value));
        return val;
    }
}
