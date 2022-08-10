package ru.rutoken.pkcs11wrapper.manager;

import ru.rutoken.pkcs11wrapper.reference.SessionReference;

public interface Pkcs11DualFunctionManager extends SessionReference {
    byte[] digestEncryptUpdate(byte[] part);

    byte[] decryptDigestUpdate(byte[] part);

    byte[] signEncryptUpdate(byte[] part);

    byte[] decryptVerifyUpdate(byte[] encryptedPart);
}
