package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkCInitializeArgs;
import ru.rutoken.pkcs11wrapper.main.IPkcs11MutexHandler;

class FakeCkCInitializeArgsImpl implements CkCInitializeArgs {
    @Nullable
    private IPkcs11MutexHandler mMutexHandler;

    @Nullable
    @Override
    public IPkcs11MutexHandler getMutexHandler() {
        return mMutexHandler;
    }

    @Override
    public void setMutexHandler(@Nullable IPkcs11MutexHandler mutexHandler) {
        mMutexHandler = mutexHandler;
    }

    @Override
    public long getFlags() {
        return 0;
    }

    @Override
    public void setFlags(long flags) {
    }

    @Override
    public Object getReserved() {
        return new Object();
    }
}
