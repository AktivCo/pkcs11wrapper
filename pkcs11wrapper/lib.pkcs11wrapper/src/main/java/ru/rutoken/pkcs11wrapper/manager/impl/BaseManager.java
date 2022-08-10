package ru.rutoken.pkcs11wrapper.manager.impl;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.reference.SessionReference;

public class BaseManager implements SessionReference {
    protected final Pkcs11Session mSession;

    protected BaseManager(Pkcs11Session session) {
        mSession = Objects.requireNonNull(session);
    }

    @Override
    public Pkcs11Session getSession() {
        return mSession;
    }
}
