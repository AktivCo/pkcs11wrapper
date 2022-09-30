package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkCInitializeArgs;
import ru.rutoken.pkcs11wrapper.main.IPkcs11MutexHandler;

class FakeCkCInitializeArgsImpl implements CkCInitializeArgs {
    @Override
    public void setMutexHandler(@Nullable IPkcs11MutexHandler mutexHandler) {
    }

    @Override
    public void setFlags(long flags) {
    }
}
