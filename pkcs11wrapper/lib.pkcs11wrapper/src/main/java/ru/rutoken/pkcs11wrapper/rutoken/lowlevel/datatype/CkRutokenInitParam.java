/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype;

import org.jetbrains.annotations.Nullable;

public interface CkRutokenInitParam {
    void setUseRepairMode(long mode);

    void setNewAdminPin(byte[] adminPin);

    void setNewUserPin(byte[] userPin);

    void setChangeUserPinPolicy(long pinPolicy);

    void setMinAdminPinLen(long pinLen);

    void setMinUserPinLen(long pinLen);

    void setMaxAdminRetryCount(long retryCount);

    void setMaxUserRetryCount(long retryCount);

    void setTokenLabel(byte @Nullable [] label);

    void setSmMode(long mode);
}
