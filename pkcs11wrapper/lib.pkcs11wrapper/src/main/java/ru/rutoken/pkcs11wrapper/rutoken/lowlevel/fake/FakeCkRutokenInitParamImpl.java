/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.fake;

import org.jetbrains.annotations.Nullable;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkRutokenInitParam;

class FakeCkRutokenInitParamImpl implements CkRutokenInitParam {
    @Override
    public void setUseRepairMode(long mode) {

    }

    @Override
    public void setNewAdminPin(byte[] adminPin) {

    }

    @Override
    public void setNewUserPin(byte[] userPin) {

    }

    @Override
    public void setChangeUserPinPolicy(long pinPolicy) {

    }

    @Override
    public void setMinAdminPinLen(long pinLen) {

    }

    @Override
    public void setMinUserPinLen(long pinLen) {

    }

    @Override
    public void setMaxAdminRetryCount(long retryCount) {

    }

    @Override
    public void setMaxUserRetryCount(long retryCount) {

    }

    @Override
    public void setTokenLabel(byte @Nullable [] label) {

    }

    @Override
    public void setSmMode(long mode) {

    }
}
