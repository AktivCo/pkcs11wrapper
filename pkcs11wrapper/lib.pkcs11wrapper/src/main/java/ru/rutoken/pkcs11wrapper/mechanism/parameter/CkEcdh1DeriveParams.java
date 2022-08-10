package ru.rutoken.pkcs11wrapper.mechanism.parameter;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CkEcdh1DeriveParams implements Pkcs11MechanismParams {
    private final long mKdf;
    private final byte[] mPublicKeyValue;
    private final byte @Nullable [] mSharedData;

    public CkEcdh1DeriveParams(long kdf, byte[] publicKeyValue, byte @Nullable [] sharedData) {
        mKdf = kdf;
        mPublicKeyValue = Objects.requireNonNull(publicKeyValue);
        mSharedData = sharedData;
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

    public byte @Nullable [] getSharedData() {
        return mSharedData;
    }
}