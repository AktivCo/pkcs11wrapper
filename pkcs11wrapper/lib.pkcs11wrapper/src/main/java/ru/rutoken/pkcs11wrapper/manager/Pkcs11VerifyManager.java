package ru.rutoken.pkcs11wrapper.manager;

import ru.rutoken.pkcs11wrapper.datatype.Pkcs11VerifyRecoverResult;
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11KeyObject;
import ru.rutoken.pkcs11wrapper.reference.SessionReference;

public interface Pkcs11VerifyManager extends SessionReference {
    void verifyInit(Pkcs11Mechanism mechanism, Pkcs11KeyObject key);

    boolean verify(byte[] data, byte[] signature);

    default boolean verifyAtOnce(byte[] data, byte[] signature, Pkcs11Mechanism mechanism, Pkcs11KeyObject key) {
        verifyInit(mechanism, key);
        return verify(data, signature);
    }

    void requireVerifyAtOnce(byte[] data, byte[] signature, Pkcs11Mechanism mechanism, Pkcs11KeyObject key);

    void verifyUpdate(byte[] part);

    boolean verifyFinal(byte[] signature);

    void requireVerifyFinal(byte[] signature);

    void verifyRecoverInit(Pkcs11Mechanism mechanism, Pkcs11KeyObject key);

    Pkcs11VerifyRecoverResult verifyRecover(byte[] signature);

    default Pkcs11VerifyRecoverResult verifyRecoverAtOnce(byte[] signature, Pkcs11Mechanism mechanism,
                                                          Pkcs11KeyObject key) {
        verifyRecoverInit(mechanism, key);
        return verifyRecover(signature);
    }

    byte[] requireVerifyRecoverAtOnce(byte[] signature, Pkcs11Mechanism mechanism, Pkcs11KeyObject key);
}
