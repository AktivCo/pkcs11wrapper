package ru.rutoken.pkcs11wrapper.manager.impl;

import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11RandomNumberManager;

public class Pkcs11RandomNumberManagerImpl extends BaseManager implements Pkcs11RandomNumberManager {
    public Pkcs11RandomNumberManagerImpl(Pkcs11Session session) {
        super(session);
    }

    @Override
    public void seedRandom(byte[] seed) {
        getApi().C_SeedRandom(mSession.getSessionHandle(), seed);
    }

    @Override
    public byte[] generateRandom(int length) {
        return getApi().C_GenerateRandom(getSession().getSessionHandle(), length);
    }
}
