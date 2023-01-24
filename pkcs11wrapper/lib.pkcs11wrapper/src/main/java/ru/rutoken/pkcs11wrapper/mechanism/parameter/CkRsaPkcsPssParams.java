/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.mechanism.parameter;

public class CkRsaPkcsPssParams implements Pkcs11MechanismParams {
    private final long mHashAlgorithm;
    private final long mMgf;
    private final long mSaltLength;

    public CkRsaPkcsPssParams(long hashAlgorithm, long mgf, long saltLength) {
        mHashAlgorithm = hashAlgorithm;
        mMgf = mgf;
        mSaltLength = saltLength;
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }

    public long getHashAlgorithm() {
        return mHashAlgorithm;
    }

    public long getMgf() {
        return mMgf;
    }

    public long getSaltLength() {
        return mSaltLength;
    }
}
