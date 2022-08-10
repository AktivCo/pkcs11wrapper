package ru.rutoken.pkcs11wrapper.mechanism.parameter;

import java.util.Objects;

/**
 * A mechanism parameter representing an array of bytes.
 * It is frequently used by GOST algorithms.
 */
public class Pkcs11ByteArrayMechanismParams implements Pkcs11MechanismParams {
    private final byte[] mData;

    public Pkcs11ByteArrayMechanismParams(byte[] data) {
        mData = Objects.requireNonNull(data);
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }

    public byte[] getByteArray() {
        return mData;
    }
}
