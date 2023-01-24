/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

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
