/*
 * Copyright (c) 2025, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.mechanism.parameter;

public class CkVendorBip32DeriveParams implements Pkcs11MechanismParams {
    private final long[] mDerivationPath;

    public CkVendorBip32DeriveParams(long[] derivationPath) {
        mDerivationPath = derivationPath;
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }

    public long[] getDerivationPath() {
        return mDerivationPath;
    }
}
