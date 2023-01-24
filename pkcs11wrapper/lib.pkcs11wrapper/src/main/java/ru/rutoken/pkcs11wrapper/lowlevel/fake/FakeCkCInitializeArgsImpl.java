/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

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
