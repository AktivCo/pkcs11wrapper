package ru.rutoken.pkcs11wrapper.datatype;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11Flag;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11SessionState;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkSessionInfo;

/**
 * Immutable high-level representation of {@link CkSessionInfo}.
 */
@SuppressWarnings("unused")
public class Pkcs11SessionInfo {
    private final long mSlotId;
    private final Pkcs11SessionState mState;
    private final long mDeviceError;
    private final long mFlags;

    public Pkcs11SessionInfo(CkSessionInfo sessionInfo) {
        mSlotId = sessionInfo.getSlotID();
        mState = Pkcs11SessionState.fromValue(sessionInfo.getState());
        mDeviceError = sessionInfo.getDeviceError();
        mFlags = sessionInfo.getFlags();
    }

    public Pkcs11SessionInfo(long slotId, Pkcs11SessionState state, long deviceError, long flags) {
        mSlotId = slotId;
        mState = Objects.requireNonNull(state);
        mDeviceError = deviceError;
        mFlags = flags;
    }

    public long getSlotId() {
        return mSlotId;
    }

    public Pkcs11SessionState getState() {
        return mState;
    }

    public long getDeviceError() {
        return mDeviceError;
    }

    public boolean isRwSession() {
        return checkFlag(Pkcs11Flag.CKF_RW_SESSION);
    }

    public boolean isSerialSession() {
        return checkFlag(Pkcs11Flag.CKF_SERIAL_SESSION);
    }

    public long getFlags() {
        return mFlags;
    }

    @Override
    public String toString() {
        return "Pkcs11SessionInfo{" +
                "mSlotId=" + mSlotId +
                ", mState=" + mState +
                ", mDeviceError=" + mDeviceError +
                ", mFlags=" + mFlags +
                '}';
    }

    private boolean checkFlag(Pkcs11Flag flag) {
        return (mFlags & flag.getAsLong()) != 0L;
    }
}
