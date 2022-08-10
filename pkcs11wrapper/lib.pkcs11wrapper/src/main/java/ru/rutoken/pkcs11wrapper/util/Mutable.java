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
