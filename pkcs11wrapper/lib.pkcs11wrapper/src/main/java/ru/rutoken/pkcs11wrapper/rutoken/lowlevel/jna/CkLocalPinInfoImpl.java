package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna;

import com.sun.jna.NativeLong;
import ru.rutoken.pkcs11jna.CK_LOCAL_PIN_INFO;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkLocalPinInfo;

import java.util.Objects;

class CkLocalPinInfoImpl implements CkLocalPinInfo {
    private final CK_LOCAL_PIN_INFO mData;

    CkLocalPinInfoImpl(CK_LOCAL_PIN_INFO data) {
        mData = Objects.requireNonNull(data);
    }

    CK_LOCAL_PIN_INFO getJnaValue() {
        return mData;
    }

    @Override
    public void setPinId(long pinId) {
        mData.ulPinID = new NativeLong(pinId);
    }

    @Override
    public long getPinId() {
        return mData.ulPinID.longValue();
    }

    @Override
    public long getMinSize() {
        return mData.ulMinSize.longValue();
    }

    @Override
    public long getMaxSize() {
        return mData.ulMaxSize.longValue();
    }

    @Override
    public long getMaxRetryCount() {
        return mData.ulMaxRetryCount.longValue();
    }

    @Override
    public long getCurrentRetryCount() {
        return mData.ulCurrentRetryCount.longValue();
    }

    @Override
    public long getFlags() {
        return mData.flags.longValue();
    }
}
