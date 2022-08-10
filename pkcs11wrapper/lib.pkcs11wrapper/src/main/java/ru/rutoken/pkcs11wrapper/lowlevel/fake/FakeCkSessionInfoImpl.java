package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkSessionInfo;

class FakeCkSessionInfoImpl implements CkSessionInfo {
    private final long mSlotID;
    private final long mState;
    private final long mFlags;
    private final long mDeviceError;

    FakeCkSessionInfoImpl(long slotID, long state, long flags, long deviceError) {
        mSlotID = slotID;
        mState = state;
        mFlags = flags;
        mDeviceError = deviceError;
    }

    FakeCkSessionInfoImpl copyWithState(long state) {
        return new FakeCkSessionInfoImpl(mSlotID, state, mFlags, mDeviceError);
    }

    @Override
    public long getSlotID() {
        return mSlotID;
    }

    @Override
    public long getState() {
        return mState;
    }

    @Override
    public long getFlags() {
        return mFlags;
    }

    @Override
    public long getDeviceError() {
        return mDeviceError;
    }
}
