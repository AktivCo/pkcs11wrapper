package ru.rutoken.pkcs11wrapper.main;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue;

/**
 * Implements external mutex locking for pkcs11.
 * This class must be thread safe.
 */
class Pkcs11MutexHandlerImpl implements IPkcs11MutexHandler {
    private final Set<MutexHolder> mMutexHolders = Collections.synchronizedSet(new HashSet<>());

    Pkcs11MutexHandlerImpl() {
    }

    @Override
    public Pkcs11Mutex createMutex() {
        final MutexHolder mutexHolder = new MutexHolder();
        mMutexHolders.add(mutexHolder);
        return mutexHolder;
    }

    @Override
    public void destroyMutex(Pkcs11Mutex mutex) {
        final MutexHolder mutexHolder = cast(mutex);
        if (mutexHolder.mutex.isLocked())
            throw new Pkcs11MutexException(Pkcs11ReturnValue.CKR_MUTEX_BAD, "Mutex is still locked");
        if (!mMutexHolders.remove(mutexHolder))
            throw new Pkcs11MutexException(Pkcs11ReturnValue.CKR_MUTEX_BAD, "Unknown mutex object");
    }

    @Override
    public void lockMutex(Pkcs11Mutex mutex) {
        final MutexHolder holder = cast(mutex);
        if (!mMutexHolders.contains(holder))
            throw new Pkcs11MutexException(Pkcs11ReturnValue.CKR_MUTEX_BAD, "Unknown mutex object");
        holder.mutex.lock();
    }

    @Override
    public void unlockMutex(Pkcs11Mutex mutex) {
        final MutexHolder holder = cast(mutex);
        if (!mMutexHolders.contains(holder))
            throw new Pkcs11MutexException(Pkcs11ReturnValue.CKR_MUTEX_BAD, "Unknown mutex object");
        try {
            holder.mutex.unlock();
        } catch (IllegalMonitorStateException e) {
            throw new Pkcs11MutexException(Pkcs11ReturnValue.CKR_MUTEX_NOT_LOCKED, "Mutex is not locked");
        }
    }

    private MutexHolder cast(Pkcs11Mutex mutex) {
        if (mutex instanceof MutexHolder)
            return (MutexHolder) mutex;
        else
            throw new Pkcs11MutexException(Pkcs11ReturnValue.CKR_MUTEX_BAD, "Bad mutex object");
    }

    private static class MutexHolder implements Pkcs11Mutex {
        final ReentrantLock mutex = new ReentrantLock();
    }

    private static class Pkcs11MutexException extends Pkcs11Exception {
        Pkcs11MutexException(IPkcs11ReturnValue code, String message) {
            super(code, message);
        }
    }
}
