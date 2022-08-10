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
import ru.rutoken.pkcs11wrapper.manager.impl.Pkcs11DecryptionManagerImpl;
import ru.rutoken.pkcs11wrapper.manager.impl.Pkcs11DigestManagerImpl;
import ru.rutoken.pkcs11wrapper.manager.impl.Pkcs11DualFunctionManagerImpl;
import ru.rutoken.pkcs11wrapper.manager.impl.Pkcs11EncryptionManagerImpl;
import ru.rutoken.pkcs11wrapper.manager.impl.Pkcs11KeyManagerImpl;
import ru.rutoken.pkcs11wrapper.manager.impl.Pkcs11ObjectManagerImpl;
import ru.rutoken.pkcs11wrapper.manager.impl.Pkcs11RandomNumberManagerImpl;
import ru.rutoken.pkcs11wrapper.manager.impl.Pkcs11SignManagerImpl;
import ru.rutoken.pkcs11wrapper.manager.impl.Pkcs11VerifyManagerImpl;

public class Pkcs11HighLevelFactory implements IPkcs11HighLevelFactory {
    @Override
    public Pkcs11Slot makeSlot(IPkcs11Module module, long slotId) {
        return new Pkcs11SlotImpl(module, slotId);
    }

    @Override
    public Pkcs11Token makeToken(Pkcs11Slot slot) {
        return new Pkcs11TokenImpl(slot);
    }

    @Override
    public Pkcs11Session makeSession(Pkcs11Token token, long sessionHandle) {
        return new Pkcs11SessionImpl(token, sessionHandle);
    }

    @Override
    public Pkcs11ObjectManager makeObjectManager(Pkcs11Session session) {
        return new Pkcs11ObjectManagerImpl(session);
    }

    @Override
    public Pkcs11EncryptionManager makeEncryptionManager(Pkcs11Session session) {
        return new Pkcs11EncryptionManagerImpl(session);
    }

    @Override
    public Pkcs11DecryptionManager makeDecryptionManager(Pkcs11Session session) {
        return new Pkcs11DecryptionManagerImpl(session);
    }

    @Override
    public Pkcs11DigestManager makeDigestManager(Pkcs11Session session) {
        return new Pkcs11DigestManagerImpl(session);
    }

    @Override
    public Pkcs11SignManager makeSignManager(Pkcs11Session session) {
        return new Pkcs11SignManagerImpl(session);
    }

    @Override
    public Pkcs11VerifyManager makeVerifyManager(Pkcs11Session session) {
        return new Pkcs11VerifyManagerImpl(session);
    }

    @Override
    public Pkcs11DualFunctionManager makeDualFunctionManager(Pkcs11Session session) {
        return new Pkcs11DualFunctionManagerImpl(session);
    }

    @Override
    public Pkcs11KeyManager makeKeyManager(Pkcs11Session session) {
        return new Pkcs11KeyManagerImpl(session);
    }

    @Override
    public Pkcs11RandomNumberManager makeRandomNumberManager(Pkcs11Session session) {
        return new Pkcs11RandomNumberManagerImpl(session);
    }
}
