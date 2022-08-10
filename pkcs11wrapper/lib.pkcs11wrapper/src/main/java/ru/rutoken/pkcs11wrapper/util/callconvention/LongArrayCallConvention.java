package ru.rutoken.pkcs11wrapper.util.callconvention;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.util.MutableLong;

/**
 * Simplifies usage of pkcs11 conventions for functions returning output in a variable-length buffer
 */
public abstract class LongArrayCallConvention {
    private final String mName;

    protected LongArrayCallConvention(String name) {
        mName = Objects.requireNonNull(name);
    }

    protected abstract int getLength();

    protected abstract IPkcs11ReturnValue fillValues(long[] values, MutableLong length);

    public long[] call() {
        long @NotNull [] values;
        @NotNull MutableLong filledLength;
        @NotNull IPkcs11ReturnValue res;
        do {
            final int expectedLength = getLength();
            if (expectedLength <= 0)
                return new long[0];
            values = new long[expectedLength];
            filledLength = new MutableLong(expectedLength);
            res = fillValues(values, filledLength);
        } while (res == Pkcs11ReturnValue.CKR_BUFFER_TOO_SMALL);
        Pkcs11Exception.throwIfNotOk(res, mName + " failed");

        // result may be shorter that was expected due to concurrent modification
        return filledLength.value < values.length ?
                Arrays.copyOf(values, (int) filledLength.value) : values;
    }
}
