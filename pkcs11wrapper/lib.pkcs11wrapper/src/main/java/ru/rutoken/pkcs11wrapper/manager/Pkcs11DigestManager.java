package ru.rutoken.pkcs11wrapper.manager;

import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.reference.SessionReference;

public interface Pkcs11DigestManager extends SessionReference {
    void digestInit(Pkcs11Mechanism mechanism);

    byte[] digest(byte[] data);

    /**
     * Version for optimisation: instead of calling C_Digest twice -
     * one call to acquire digest length and the other with a properly
     * allocated memory for a result, there might be one call with presumably
     * enough allocated space.
     *
     * @param data            data to make a digest from
     * @param maxDigestLength maximum length of result digest
     * @return byte array of proper length (<= maxDigestLength) with digest
     */
    byte[] digest(byte[] data, int maxDigestLength);

    default byte[] digestAtOnce(byte[] data, Pkcs11Mechanism mechanism) {
        digestInit(mechanism);
        return digest(data);
    }

    void digestUpdate(byte[] part);

    byte[] digestFinal();
}
