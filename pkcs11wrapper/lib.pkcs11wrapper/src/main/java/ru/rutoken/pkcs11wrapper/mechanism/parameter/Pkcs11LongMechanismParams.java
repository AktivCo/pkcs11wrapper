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
