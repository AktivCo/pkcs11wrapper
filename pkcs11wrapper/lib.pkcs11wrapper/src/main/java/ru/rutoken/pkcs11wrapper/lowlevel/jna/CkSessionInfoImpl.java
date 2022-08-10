package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_SESSION_INFO;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkSessionInfo;

class CkSessionInfoImpl implements CkSessionInfo {
    private final CK_SESSION_INFO mData;

    CkSessionInfoImpl(CK_SESSION_INFO data) {
        mData = Objects.requireNonNull(data);
    }

    @Override
    public long getSlotID() {
        return mData.slotID.longValue();
    }

    @Override
    public long getState() {
        return mData.state.longValue();
    }

    @Override
    public long getFlags() {
        return mData.flags.longValue();
    }

    @Override
    public long getDeviceError() {
        return mData.ulDeviceError.longValue();
    }
}
