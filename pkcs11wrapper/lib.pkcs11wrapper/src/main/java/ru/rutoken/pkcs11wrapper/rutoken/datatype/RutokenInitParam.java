package ru.rutoken.pkcs11wrapper.rutoken.datatype;

import org.jetbrains.annotations.Nullable;
import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkRutokenInitParam;

public class RutokenInitParam {
    private final boolean mUseRepairMode;
    private final String mNewAdminPin;
    private final String mNewUserPin;
    private final ChangeUserPinPolicy mChangeUserPinPolicy;
    private final long mMinAdminPinLen;
    private final long mMinUserPinLen;
    private final long mMaxAdminRetryCount;
    private final long mMaxUserRetryCount;
    @Nullable
    private final String mTokenLabel;
    @Nullable
    private final SmMode mSmMode;

    public RutokenInitParam(boolean useRepairMode, String newAdminPin, String newUserPin,
                            ChangeUserPinPolicy changeUserPinPolicy, long minAdminPinLen, long minUserPinLen,
                            long maxAdminRetryCount, long maxUserRetryCount, @Nullable String tokenLabel,
                            @Nullable SmMode smMode) {
        mUseRepairMode = useRepairMode;
        mNewAdminPin = newAdminPin;
        mNewUserPin = newUserPin;
        mChangeUserPinPolicy = changeUserPinPolicy;
        mMinAdminPinLen = minAdminPinLen;
        mMinUserPinLen = minUserPinLen;
        mMaxAdminRetryCount = maxAdminRetryCount;
        mMaxUserRetryCount = maxUserRetryCount;
        mTokenLabel = tokenLabel;
        mSmMode = smMode;
    }

    public CkRutokenInitParam toCkRutokenInitParam(IRtPkcs11LowLevelFactory factory) {
        final CkRutokenInitParam param = factory.makeRutokenInitParam();
        param.setUseRepairMode(mUseRepairMode ? 1 : 0);
        param.setNewAdminPin(mNewAdminPin.getBytes());
        param.setNewUserPin(mNewUserPin.getBytes());
        param.setChangeUserPinPolicy(mChangeUserPinPolicy.getAsLong());
        param.setMinAdminPinLen(mMinAdminPinLen);
        param.setMinUserPinLen(mMinUserPinLen);
        param.setMaxAdminRetryCount(mMaxAdminRetryCount);
        param.setMaxUserRetryCount(mMaxUserRetryCount);
        param.setTokenLabel(mTokenLabel != null ? mTokenLabel.getBytes() : null);
        param.setSmMode(mSmMode == null ? 0 : mSmMode.getAsLong());
        return param;
    }

    public enum ChangeUserPinPolicy implements LongValueSupplier {
        TOKEN_FLAGS_ADMIN_CHANGE_USER_PIN(0x00000001L),
        TOKEN_FLAGS_USER_CHANGE_USER_PIN(0x00000002L),
        TOKEN_FLAGS_ADMIN_AND_USER_CHANGE_USER_PIN(TOKEN_FLAGS_ADMIN_CHANGE_USER_PIN.getAsLong() |
                TOKEN_FLAGS_USER_CHANGE_USER_PIN.getAsLong());

        private final long mValue;

        ChangeUserPinPolicy(long value) {
            mValue = value;
        }

        @Override
        public long getAsLong() {
            return mValue;
        }
    }

    @Deprecated
    public enum SmMode implements LongValueSupplier {
        SECURE_MESSAGING_DEFAULT(0x00L),
        SECURE_MESSAGING_BUILT_IN(0x01L),
        SECURE_MESSAGING_GOST(0x02L),
        SECURE_MESSAGING_ENHANCED_GOST(0x03L),
        SECURE_MESSAGING_UNSUPPORTED(0xffL);

        private final long mValue;

        SmMode(long value) {
            mValue = value;
        }

        @Override
        public long getAsLong() {
            return mValue;
        }
    }
}
