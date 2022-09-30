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
