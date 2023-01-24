/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.util.callconvention;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.util.MutableLong;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue.CKR_BUFFER_TOO_SMALL;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue.CKR_OK;

/**
 * Simplifies usage of pkcs11 conventions for functions returning output in a variable-length buffer
 */
public abstract class ByteArrayCallConvention {
    private final String mName;

    protected ByteArrayCallConvention(String name) {
        mName = Objects.requireNonNull(name);
    }

    protected abstract int getLength();

    protected abstract IPkcs11ReturnValue fillValues(byte[] values, MutableLong length);

    public CallResult call(IPkcs11ReturnValue... allowedReturnCodes) {
        byte @NotNull [] values;
        @NotNull MutableLong filledLength;
        @NotNull IPkcs11ReturnValue res;
        do {
            final int expectedLength = getLength();
            if (expectedLength <= 0)
                return new CallResult(CKR_OK, new byte[0]);
            values = new byte[expectedLength];
            filledLength = new MutableLong(expectedLength);
            res = fillValues(values, filledLength);
        } while (res == CKR_BUFFER_TOO_SMALL);

        // result may be shorter that was expected due to concurrent modification
        values = filledLength.value < values.length ?
                Arrays.copyOf(values, (int) filledLength.value) : values;

        for (IPkcs11ReturnValue allowed : allowedReturnCodes)
            if (allowed == res)
                return new CallResult(res, values);

        Pkcs11Exception.throwIfNotOk(res, mName + " failed");
        return new CallResult(res, values);
    }

    public static class CallResult {
        public final IPkcs11ReturnValue result;
        public final byte[] values;

        CallResult(IPkcs11ReturnValue result, byte[] values) {
            this.result = Objects.requireNonNull(result);
            this.values = Objects.requireNonNull(values);
        }
    }
}
