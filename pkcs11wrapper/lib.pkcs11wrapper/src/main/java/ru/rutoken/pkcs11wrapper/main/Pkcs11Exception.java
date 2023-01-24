/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.main;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11VendorExtensions;
import ru.rutoken.pkcs11wrapper.reference.VendorExtensionsReference;

/**
 * Base class for pkcs11-level errors.
 * All methods of pkcs11wrapper may throw this exception if
 * pkcs11 method call returns anything except {@link Pkcs11ReturnValue#CKR_OK}.
 * If you need more control in handling return codes, it is always possible to get {@link IPkcs11LowLevelApi}
 * object and handle return codes manually.
 * This may be helpful to prevent the use of exception to control an execution path.
 */
public class Pkcs11Exception extends RuntimeException {
    private final IPkcs11ReturnValue mCode;

    protected Pkcs11Exception(IPkcs11ReturnValue code, String message) {
        super(generateMessage(code, message));
        mCode = Objects.requireNonNull(code);
    }

    public static void throwIfNotOk(IPkcs11ReturnValue code, String message) {
        if (Pkcs11ReturnValue.CKR_OK != code)
            throw new Pkcs11Exception(code, message);
    }

    public static void throwIfNotOk(long code, String message, IPkcs11VendorExtensions vendorExtensions) {
        throwIfNotOk(IPkcs11ReturnValue.getInstance(code, vendorExtensions), message);
    }

    public static void throwIfNotOk(long code, String message, VendorExtensionsReference reference) {
        throwIfNotOk(code, message, reference.getVendorExtensions());
    }

    private static String generateMessage(IPkcs11ReturnValue code, String message) {
        return message + " [code: " + String.format("0x%08X", code.getAsLong()) + ", name: " + code + "]";
    }

    public IPkcs11ReturnValue getCode() {
        return mCode;
    }
}
