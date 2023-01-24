/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant;

/**
 * Interface for getting value of pkcs11 constant represented with enum.
 */
@FunctionalInterface
public interface LongValueSupplier {
    long getAsLong();
}
