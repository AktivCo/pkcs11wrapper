package ru.rutoken.pkcs11wrapper.util;

public final class MutableLong {
    public long value;

    public MutableLong() {
        this(0);
    }

    public MutableLong(long value) {
        this.value = value;
    }
}
