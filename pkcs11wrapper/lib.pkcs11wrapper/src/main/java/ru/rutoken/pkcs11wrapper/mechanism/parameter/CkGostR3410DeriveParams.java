/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.mechanism.parameter;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyDerivationFunction;

/**
 * Might be used for GOST 2001 and 2012.
 * <p>
 * For GOST 2001 use {@link RtPkcs11KeyDerivationFunction#CKD_CPDIVERSIFY_KDF_JRT} and
 * {@link RtPkcs11KeyDerivationFunction#CKD_NULL_KDF_JRT} values.
 */
public class CkGostR3410DeriveParams implements Pkcs11MechanismParams {
    private final long mKdf;
    private final byte[] mPublicKeyValue;
    private final byte[] mUkm;

    public CkGostR3410DeriveParams(long kdf, byte[] publicKeyValue, byte[] ukm) {
        mKdf = kdf;
        mPublicKeyValue = Objects.requireNonNull(publicKeyValue);
        mUkm = Objects.requireNonNull(ukm);
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }

    public long getKdf() {
        return mKdf;
    }

    public byte[] getPublicKeyValue() {
        return mPublicKeyValue;
    }

    public byte[] getUkm() {
        return mUkm;
    }
}
