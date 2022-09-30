package ru.rutoken.pkcs11wrapper.lowlevel.datatype;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11wrapper.main.IPkcs11MutexHandler;

public interface CkCInitializeArgs {
    void setMutexHandler(@Nullable IPkcs11MutexHandler mutexHandler);

    void setFlags(long flags);
}
