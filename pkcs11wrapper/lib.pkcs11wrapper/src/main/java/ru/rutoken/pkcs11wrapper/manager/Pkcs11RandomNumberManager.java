package ru.rutoken.pkcs11wrapper.manager;

import ru.rutoken.pkcs11wrapper.reference.SessionReference;

public interface Pkcs11RandomNumberManager extends SessionReference {
    void seedRandom(byte[] seed);

    byte[] generateRandom(int length);
}
