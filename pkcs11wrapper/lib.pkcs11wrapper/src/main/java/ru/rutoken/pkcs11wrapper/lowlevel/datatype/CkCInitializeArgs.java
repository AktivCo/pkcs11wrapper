package ru.rutoken.pkcs11wrapper.lowlevel.datatype;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11wrapper.main.IPkcs11MutexHandler;

public interface CkCInitializeArgs {
    @Nullable
    IPkcs11MutexHandler getMutexHandler();

    void setMutexHandler(@Nullable IPkcs11MutexHandler mutexHandler);

    long getFlags();

    void setFlags(long flags);

    Object getReserved();
}
