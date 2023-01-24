/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.main;

/**
 * Provides callbacks used by the a pkcs11 library for mutex locking.
 */
public interface IPkcs11MutexHandler {
    static IPkcs11MutexHandler make() {
        return new Pkcs11MutexHandlerImpl();
    }

    Pkcs11Mutex createMutex();

    void destroyMutex(Pkcs11Mutex mutex);

    void lockMutex(Pkcs11Mutex mutex);

    void unlockMutex(Pkcs11Mutex mutex);

    /**
     * Marker interface for mutex objects
     */
    interface Pkcs11Mutex {
    }
}
