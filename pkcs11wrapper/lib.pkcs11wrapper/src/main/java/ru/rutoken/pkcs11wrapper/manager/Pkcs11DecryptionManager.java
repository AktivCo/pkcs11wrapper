package ru.rutoken.pkcs11wrapper.manager;

import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11KeyObject;
import ru.rutoken.pkcs11wrapper.reference.SessionReference;

public interface Pkcs11DecryptionManager extends SessionReference {
    void decryptInit(Pkcs11Mechanism mechanism, Pkcs11KeyObject key);

    byte[] decrypt(byte[] data);

    default byte[] decryptAtOnce(byte[] data, Pkcs11Mechanism mechanism, Pkcs11KeyObject key) {
        decryptInit(mechanism, key);
        return decrypt(data);
    }

    byte[] decryptUpdate(byte[] part);

    byte[] decryptFinal();
}
