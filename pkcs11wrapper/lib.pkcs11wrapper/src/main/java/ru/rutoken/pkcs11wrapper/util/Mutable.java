/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.util;

import org.jetbrains.annotations.Nullable;

/**
 * Mutable reference. Used to get results from a function without usage of a return value.
 */
public class Mutable<T> {
    @Nullable
    public T value;

    public Mutable() {
        this(null);
    }

    public Mutable(@Nullable T value) {
        this.value = value;
    }
}
