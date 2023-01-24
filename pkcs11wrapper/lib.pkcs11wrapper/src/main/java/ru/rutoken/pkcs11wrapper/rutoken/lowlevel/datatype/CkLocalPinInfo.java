/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype;

public interface CkLocalPinInfo {
    void setPinId(long pinId);

    long getPinId();

    long getMinSize();

    long getMaxSize();

    long getMaxRetryCount();

    long getCurrentRetryCount();

    long getFlags();
}
