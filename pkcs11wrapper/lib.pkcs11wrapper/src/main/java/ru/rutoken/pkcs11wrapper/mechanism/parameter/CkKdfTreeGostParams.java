package ru.rutoken.pkcs11wrapper.mechanism.parameter;

import java.util.Objects;

public class CkKdfTreeGostParams implements Pkcs11MechanismParams {
    private final byte[] mLabel;
    private final byte[] mSeed;
    private final long mR;
    private final long mL;
    private final long mOffset;

    public CkKdfTreeGostParams(byte[] label, byte[] seed, long r, long l, long offset) {
        mLabel = Objects.requireNonNull(label);
        mSeed = Objects.requireNonNull(seed);
        mR = r;
        mL = l;
        mOffset = offset;
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }

    public byte[] getLabel() {
        return mLabel;
    }

    public byte[] getSeed() {
        return mSeed;
    }

    public long getR() {
        return mR;
    }

    public long getL() {
        return mL;
    }

    public long getOffset() {
        return mOffset;
    }
}
