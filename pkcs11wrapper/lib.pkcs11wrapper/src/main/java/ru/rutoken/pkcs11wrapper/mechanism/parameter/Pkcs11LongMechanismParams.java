/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.mechanism.parameter;

public class Pkcs11LongMechanismParams implements Pkcs11MechanismParams {
    private final long mValue;

    public Pkcs11LongMechanismParams(long value) {
        mValue = value;
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }

    public long getValue() {
        return mValue;
    }
}
