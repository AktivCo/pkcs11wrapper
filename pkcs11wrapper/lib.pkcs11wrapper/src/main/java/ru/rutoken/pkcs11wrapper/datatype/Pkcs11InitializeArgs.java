/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.datatype;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11Flag;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkCInitializeArgs;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.main.IPkcs11MutexHandler;

/**
 * Default implementation of initialization arguments.
 * Instances of this class are created using Builder pattern.
 */
public class Pkcs11InitializeArgs implements IPkcs11InitializeArgs {
    private final Builder mBuilder;

    private Pkcs11InitializeArgs(Builder builder) {
        mBuilder = Objects.requireNonNull(builder);
    }

    @Override
    public boolean isLibraryCantCreateOsThreads() {
        return checkFlag(Pkcs11Flag.CKF_LIBRARY_CANT_CREATE_OS_THREADS);
    }

    @Override
    public boolean isOsLockingOk() {
        return checkFlag(Pkcs11Flag.CKF_OS_LOCKING_OK);
    }

    @Override
    public CkCInitializeArgs toCkCInitializeArgs(IPkcs11LowLevelFactory factory) {
        final CkCInitializeArgs args = factory.makeCInitializeArgs();
        args.setMutexHandler(mBuilder.mutexHandler);
        args.setFlags(mBuilder.flags);
        return args;
    }

    private boolean checkFlag(Pkcs11Flag flag) {
        return (mBuilder.flags & flag.getAsLong()) != 0L;
    }

    public static class Builder {
        @Nullable
        private IPkcs11MutexHandler mutexHandler;
        private long flags;

        public Builder setMutexHandler(@Nullable IPkcs11MutexHandler mutexHandler) {
            this.mutexHandler = mutexHandler;
            return this;
        }

        public Builder setLibraryCantCreateOsThreads(boolean value) {
            setFlag(Pkcs11Flag.CKF_LIBRARY_CANT_CREATE_OS_THREADS, value);
            return this;
        }

        public Builder setOsLockingOk(boolean value) {
            setFlag(Pkcs11Flag.CKF_OS_LOCKING_OK, value);
            return this;
        }

        public Builder setFlags(long flags) {
            this.flags = flags;
            return this;
        }

        public Pkcs11InitializeArgs build() {
            return new Pkcs11InitializeArgs(this);
        }

        private void setFlag(Pkcs11Flag flag, boolean value) {
            if (value) {
                setFlags(flags | flag.getAsLong());
            } else {
                setFlags(flags & ~flag.getAsLong());
            }
        }
    }
}
