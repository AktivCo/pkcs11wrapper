package ru.rutoken.pkcs11wrapper.constant;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;

/**
 * Helper class for implementing pkcs11 constant enums that may be extended by vendor defined values.
 */
class VendorFactoryHelper<I extends LongValueSupplier> {
    private final Function<Long, I> mNullableStandardValueSupplier;
    private final Function<Long, I> mUnknownValueSupplier;

    VendorFactoryHelper(Function<Long, I> nullableStandardValueSupplier,
                        Function<Long, I> unknownValueSupplier) {
        mNullableStandardValueSupplier = Objects.requireNonNull(nullableStandardValueSupplier);
        mUnknownValueSupplier = Objects.requireNonNull(unknownValueSupplier);
    }

    /**
     * Supports only standard pkcs11 values
     */
    I instanceByValue(long value) {
        final I standardValue = mNullableStandardValueSupplier.apply(value);
        return null != standardValue ? standardValue : mUnknownValueSupplier.apply(value);
    }

    /**
     * Supports standard pkcs11 values and vendor defined values
     */
    I instanceByValue(long value, VendorFactory<I> factory) {
        final I standardValue = mNullableStandardValueSupplier.apply(value);
        if (null != standardValue) {
            return standardValue;
        } else {
            final I vendorValue = factory.nullableFromValue(value);
            return null != vendorValue ? vendorValue : mUnknownValueSupplier.apply(value);
        }
    }

    interface VendorFactory<R> {
        @Nullable
        R nullableFromValue(long value);
    }
}
