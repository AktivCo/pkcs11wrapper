package ru.rutoken.pkcs11wrapper.mechanism.parameter;

/**
 * Stub class for not implemented mechanism parameter.
 */
public class Pkcs11NotImplementedMechanismParams implements Pkcs11MechanismParams {
    public Pkcs11NotImplementedMechanismParams() {
        throw new RuntimeException("This mechanism parameter is not implemented yet");
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
    }
}
