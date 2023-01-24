/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.mechanism.parameter;

import java.util.Objects;

public class CkGostR3410KeyWrapParams implements Pkcs11MechanismParams {
    private final byte[] mWrapOid;
    private final byte[] mUkm;
    private final long mKeyHandle;

    public CkGostR3410KeyWrapParams(byte[] wrapOid, byte[] ukm, long keyHandle) {
        mWrapOid = Objects.requireNonNull(wrapOid);
        mUkm = Objects.requireNonNull(ukm);
        mKeyHandle = keyHandle;
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }

    public byte[] getWrapOid() {
        return mWrapOid;
    }

    public byte[] getUkm() {
        return mUkm;
    }

    public long getKeyHandle() {
        return mKeyHandle;
    }
}
