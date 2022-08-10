package ru.rutoken.pkcs11wrapper.main;

import ru.rutoken.pkcs11wrapper.manager.Pkcs11DecryptionManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11DigestManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11DualFunctionManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11EncryptionManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11KeyManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11ObjectManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11RandomNumberManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11SignManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11VerifyManager;

/**
 * This factory interface is used by pkcs11wrapper to create high-level objects.
 * User provides implementation of this interface to pkcs11wrapper and should not use it directly.
 */
public interface IPkcs11HighLevelFactory {
    Pkcs11Slot makeSlot(IPkcs11Module module, long slotId);

    Pkcs11Token makeToken(Pkcs11Slot slot);

    Pkcs11Session makeSession(Pkcs11Token token, long sessionHandle);

    Pkcs11ObjectManager makeObjectManager(Pkcs11Session session);

    Pkcs11EncryptionManager makeEncryptionManager(Pkcs11Session session);

    Pkcs11DecryptionManager makeDecryptionManager(Pkcs11Session session);

    Pkcs11DigestManager makeDigestManager(Pkcs11Session session);

    Pkcs11SignManager makeSignManager(Pkcs11Session session);

    Pkcs11VerifyManager makeVerifyManager(Pkcs11Session session);

    Pkcs11DualFunctionManager makeDualFunctionManager(Pkcs11Session session);

    Pkcs11KeyManager makeKeyManager(Pkcs11Session session);

    Pkcs11RandomNumberManager makeRandomNumberManager(Pkcs11Session session);
}
