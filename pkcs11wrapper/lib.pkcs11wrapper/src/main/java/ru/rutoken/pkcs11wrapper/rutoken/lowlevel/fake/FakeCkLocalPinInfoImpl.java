/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.fake;

import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkLocalPinInfo;

class FakeCkLocalPinInfoImpl implements CkLocalPinInfo {
    @Override
    public void setPinId(long pinId) {

    }

    @Override
    public long getPinId() {
        return 0;
    }

    @Override
    public long getMinSize() {
        return 0;
    }

    @Override
    public long getMaxSize() {
        return 0;
    }

    @Override
    public long getMaxRetryCount() {
        return 0;
    }

    @Override
    public long getCurrentRetryCount() {
        return 0;
    }

    @Override
    public long getFlags() {
        return 0;
    }
}
