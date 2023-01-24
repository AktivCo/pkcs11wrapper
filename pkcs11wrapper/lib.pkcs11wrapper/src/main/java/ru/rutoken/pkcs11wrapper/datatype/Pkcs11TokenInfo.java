package ru.rutoken.pkcs11wrapper.datatype;

import org.jetbrains.annotations.Nullable;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11Flag;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkTokenInfo;
import ru.rutoken.pkcs11wrapper.util.Pkcs11Utility;

import java.util.Date;

/**
 * Immutable high-level representation of {@link CkTokenInfo}.
 */
@SuppressWarnings("unused")
public class Pkcs11TokenInfo {
    private final String mLabel;
    private final String mManufacturerId;
    private final String mModel;
    private final String mSerialNumber;
    private final long mMaxSessionCount;
    private final long mSessionCount;
    private final long mMaxRwSessionCount;
    private final long mRwSessionCount;
    private final long mMaxPinLen;
    private final long mMinPinLen;
    private final long mTotalPublicMemory;
    private final long mFreePublicMemory;
    private final long mTotalPrivateMemory;
    private final long mFreePrivateMemory;
    private final Pkcs11Version mHardwareVersion;
    private final Pkcs11Version mFirmwareVersion;
    private final long mFlags;
    @Nullable
    private Date mTime;

    public Pkcs11TokenInfo(CkTokenInfo tokenInfo) {
        mLabel = new String(tokenInfo.getLabel());
        mManufacturerId = new String(tokenInfo.getManufacturerID());
        mModel = new String(tokenInfo.getModel());
        mSerialNumber = new String(tokenInfo.getSerialNumber());
        mMaxSessionCount = tokenInfo.getMaxSessionCount();
        mSessionCount = tokenInfo.getSessionCount();
        mMaxRwSessionCount = tokenInfo.getMaxRwSessionCount();
        mRwSessionCount = tokenInfo.getRwSessionCount();
        mMaxPinLen = tokenInfo.getMaxPinLen();
        mMinPinLen = tokenInfo.getMinPinLen();
        mTotalPublicMemory = tokenInfo.getTotalPublicMemory();
        mFreePublicMemory = tokenInfo.getFreePublicMemory();
        mTotalPrivateMemory = tokenInfo.getTotalPrivateMemory();
        mFreePrivateMemory = tokenInfo.getFreePrivateMemory();
        mHardwareVersion = new Pkcs11Version(tokenInfo.getHardwareVersion());
        mFirmwareVersion = new Pkcs11Version(tokenInfo.getFirmwareVersion());
        mFlags = tokenInfo.getFlags();
        if (isClockOnToken() && null != tokenInfo.getUtcTime())
            mTime = Pkcs11Utility.parseTime(tokenInfo.getUtcTime());
    }

    public String getLabel() {
        return mLabel;
    }

    public String getManufacturerId() {
        return mManufacturerId;
    }

    public String getModel() {
        return mModel;
    }

    public String getSerialNumber() {
        return mSerialNumber;
    }

    public long getMaxSessionCount() {
        return mMaxSessionCount;
    }

    public long getSessionCount() {
        return mSessionCount;
    }

    public long getMaxRwSessionCount() {
        return mMaxRwSessionCount;
    }

    public long getRwSessionCount() {
        return mRwSessionCount;
    }

    public long getMaxPinLen() {
        return mMaxPinLen;
    }

    public long getMinPinLen() {
        return mMinPinLen;
    }

    public long getTotalPublicMemory() {
        return mTotalPublicMemory;
    }

    public long getFreePublicMemory() {
        return mFreePublicMemory;
    }

    public long getTotalPrivateMemory() {
        return mTotalPrivateMemory;
    }

    public long getFreePrivateMemory() {
        return mFreePrivateMemory;
    }

    public Pkcs11Version getHardwareVersion() {
        return mHardwareVersion;
    }

    public Pkcs11Version getFirmwareVersion() {
        return mFirmwareVersion;
    }

    @Nullable
    public Date getTime() {
        return mTime;
    }

    public boolean isRng() {
        return checkFlag(Pkcs11Flag.CKF_RNG);
    }

    public boolean isWriteProtected() {
        return checkFlag(Pkcs11Flag.CKF_WRITE_PROTECTED);
    }

    public boolean isLoginRequired() {
        return checkFlag(Pkcs11Flag.CKF_LOGIN_REQUIRED);
    }

    public boolean isUserPinInitialized() {
        return checkFlag(Pkcs11Flag.CKF_USER_PIN_INITIALIZED);
    }

    public boolean isRestoreKeyNotNeeded() {
        return checkFlag(Pkcs11Flag.CKF_RESTORE_KEY_NOT_NEEDED);
    }

    public boolean isClockOnToken() {
        return checkFlag(Pkcs11Flag.CKF_CLOCK_ON_TOKEN);
    }

    public boolean isProtectedAuthenticationPath() {
        return checkFlag(Pkcs11Flag.CKF_PROTECTED_AUTHENTICATION_PATH);
    }

    public boolean isDualCryptoOperations() {
        return checkFlag(Pkcs11Flag.CKF_DUAL_CRYPTO_OPERATIONS);
    }

    public boolean isTokenInitialized() {
        return checkFlag(Pkcs11Flag.CKF_TOKEN_INITIALIZED);
    }

    @Deprecated
    public boolean isSecondaryAuthentication() {
        //noinspection deprecation
        return checkFlag(Pkcs11Flag.CKF_SECONDARY_AUTHENTICATION);
    }

    public boolean isUserPinCountLow() {
        return checkFlag(Pkcs11Flag.CKF_USER_PIN_COUNT_LOW);
    }

    public boolean isUserPinFinalTry() {
        return checkFlag(Pkcs11Flag.CKF_USER_PIN_FINAL_TRY);
    }

    public boolean isUserPinLocked() {
        return checkFlag(Pkcs11Flag.CKF_USER_PIN_LOCKED);
    }

    public boolean isUserPinToBeChanged() {
        return checkFlag(Pkcs11Flag.CKF_USER_PIN_TO_BE_CHANGED);
    }

    public boolean isSoPinCountLow() {
        return checkFlag(Pkcs11Flag.CKF_SO_PIN_COUNT_LOW);
    }

    public boolean isSoPinFinalTry() {
        return checkFlag(Pkcs11Flag.CKF_SO_PIN_FINAL_TRY);
    }

    public boolean isSoPinLocked() {
        return checkFlag(Pkcs11Flag.CKF_SO_PIN_LOCKED);
    }

    public boolean isSoPinToBeChanged() {
        return checkFlag(Pkcs11Flag.CKF_SO_PIN_TO_BE_CHANGED);
    }

    public boolean isErrorState() {
        return checkFlag(Pkcs11Flag.CKF_ERROR_STATE);
    }

    public long getFlags() {
        return mFlags;
    }

    @Override
    public String toString() {
        return "Pkcs11TokenInfo{" +
                "mLabel='" + mLabel + '\'' +
                ", mManufacturerId='" + mManufacturerId + '\'' +
                ", mModel='" + mModel + '\'' +
                ", mSerialNumber='" + mSerialNumber + '\'' +
                ", mMaxSessionCount=" + mMaxSessionCount +
                ", mSessionCount=" + mSessionCount +
                ", mMaxRwSessionCount=" + mMaxRwSessionCount +
                ", mRwSessionCount=" + mRwSessionCount +
                ", mMaxPinLen=" + mMaxPinLen +
                ", mMinPinLen=" + mMinPinLen +
                ", mTotalPublicMemory=" + mTotalPublicMemory +
                ", mFreePublicMemory=" + mFreePublicMemory +
                ", mTotalPrivateMemory=" + mTotalPrivateMemory +
                ", mFreePrivateMemory=" + mFreePrivateMemory +
                ", mHardwareVersion=" + mHardwareVersion +
                ", mFirmwareVersion=" + mFirmwareVersion +
                ", mFlags=" + mFlags +
                ", mTime=" + mTime +
                '}';
    }

    private boolean checkFlag(Pkcs11Flag flag) {
        return (mFlags & flag.getAsLong()) != 0L;
    }
}
