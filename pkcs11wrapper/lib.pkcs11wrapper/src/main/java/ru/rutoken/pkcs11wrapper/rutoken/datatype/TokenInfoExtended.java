/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.datatype;

import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;
import ru.rutoken.pkcs11wrapper.constant.standard.EnumFromValueHelper;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkTokenInfoExtended;

import java.util.Arrays;

public class TokenInfoExtended {
    @Deprecated
    private final long mTokenType;
    private final long mProtocolNumber;
    private final long mMicrocodeNumber;
    private final long mOrderNumber;
    private final long mFlags;
    private final long mMaxAdminPinLen;
    private final long mMinAdminPinLen;
    private final long mMaxUserPinLen;
    private final long mMinUserPinLen;
    private final long mMaxAdminRetryCount;
    private final long mAdminRetryCountLeft;
    private final long mMaxUserRetryCount;
    private final long mUserRetryCountLeft;
    private final byte[] mSerialNumber;
    private final long mTotalMemory;
    private final long mFreeMemory;
    private final byte[] mAtr;
    private final TokenClass mTokenClass;
    private final long mBatteryVoltage;
    private final TokenBodyColor mBodyColor;
    private final long mFirmwareChecksum;
    private final long mBatteryPercentage;
    private final long mBatteryFlags;

    public TokenInfoExtended(CkTokenInfoExtended tokenInfo) {
        mTokenType = tokenInfo.getTokenType();
        mProtocolNumber = tokenInfo.getProtocolNumber();
        mMicrocodeNumber = tokenInfo.getMicrocodeNumber();
        mOrderNumber = tokenInfo.getOrderNumber();
        mFlags = tokenInfo.getFlags();
        mMaxAdminPinLen = tokenInfo.getMaxAdminPinLen();
        mMinAdminPinLen = tokenInfo.getMinAdminPinLen();
        mMaxUserPinLen = tokenInfo.getMaxUserPinLen();
        mMinUserPinLen = tokenInfo.getMinUserPinLen();
        mMaxAdminRetryCount = tokenInfo.getMaxAdminRetryCount();
        mAdminRetryCountLeft = tokenInfo.getAdminRetryCountLeft();
        mMaxUserRetryCount = tokenInfo.getMaxUserRetryCount();
        mUserRetryCountLeft = tokenInfo.getUserRetryCountLeft();
        mSerialNumber = tokenInfo.getSerialNumber();
        mTotalMemory = tokenInfo.getTotalMemory();
        mFreeMemory = tokenInfo.getFreeMemory();
        mAtr = Arrays.copyOf(tokenInfo.getAtr(), (int) tokenInfo.getAtrLen());
        mTokenClass = TokenClass.fromValue(tokenInfo.getTokenClass());
        mBatteryVoltage = tokenInfo.getBatteryVoltage();
        mBodyColor = TokenBodyColor.fromValue(tokenInfo.getBodyColor());
        mFirmwareChecksum = tokenInfo.getFirmwareChecksum();
        mBatteryPercentage = tokenInfo.getBatteryPercentage();
        mBatteryFlags = tokenInfo.getBatteryFlags();
    }

    @Deprecated
    public long getTokenType() {
        return mTokenType;
    }

    public long getProtocolNumber() {
        return mProtocolNumber;
    }

    public long getMicrocodeNumber() {
        return mMicrocodeNumber;
    }

    public long getOrderNumber() {
        return mOrderNumber;
    }

    public long getFlags() {
        return mFlags;
    }

    public long getMaxAdminPinLen() {
        return mMaxAdminPinLen;
    }

    public long getMinAdminPinLen() {
        return mMinAdminPinLen;
    }

    public long getMaxUserPinLen() {
        return mMaxUserPinLen;
    }

    public long getMinUserPinLen() {
        return mMinUserPinLen;
    }

    public long getMaxAdminRetryCount() {
        return mMaxAdminRetryCount;
    }

    public long getAdminRetryCountLeft() {
        return mAdminRetryCountLeft;
    }

    public long getMaxUserRetryCount() {
        return mMaxUserRetryCount;
    }

    public long getUserRetryCountLeft() {
        return mUserRetryCountLeft;
    }

    public byte[] getSerialNumber() {
        return mSerialNumber;
    }

    /**
     * @return size of all memory in bytes
     */
    public long getTotalMemory() {
        return mTotalMemory;
    }

    /**
     * @return size of free memory in bytes
     */
    public long getFreeMemory() {
        return mFreeMemory;
    }

    public byte[] getAtr() {
        return mAtr;
    }

    public TokenClass getTokenClass() {
        return mTokenClass;
    }

    public long getBatteryVoltage() {
        return mBatteryVoltage;
    }

    public TokenBodyColor getBodyColor() {
        return mBodyColor;
    }

    public long getFirmwareChecksum() {
        return mFirmwareChecksum;
    }

    public long getBatteryPercentage() {
        return mBatteryPercentage;
    }

    public long getBatteryFlags() {
        return mBatteryFlags;
    }

    public enum TokenClass implements LongValueSupplier {
        TOKEN_CLASS_UNKNOWN(0xFFFFFFFFL),
        TOKEN_CLASS_S(0x00L),
        TOKEN_CLASS_ECP(0x01L),
        TOKEN_CLASS_LITE(0x02L),
        TOKEN_CLASS_ECP_BT(0x09L),
        @Deprecated
        TOKEN_CLASS_WEB(0x03L),
        @Deprecated
        TOKEN_CLASS_PINPAD(0x04L),
        @Deprecated
        TOKEN_CLASS_ECPDUAL(TOKEN_CLASS_ECP_BT.getAsLong());

        private static final EnumFromValueHelper<TokenClass> FROM_VALUE_HELPER = new EnumFromValueHelper<>();

        private final long mValue;

        TokenClass(long value) {
            mValue = value;
        }

        public static TokenClass fromValue(long value) {
            return FROM_VALUE_HELPER.fromValue(value, TokenClass.class);
        }

        @Override
        public long getAsLong() {
            return mValue;
        }
    }

    public enum TokenBodyColor implements LongValueSupplier {
        TOKEN_BODY_COLOR_UNKNOWN(0L),
        TOKEN_BODY_COLOR_WHITE(1L),
        TOKEN_BODY_COLOR_BLACK(2L);

        private static final EnumFromValueHelper<TokenBodyColor> FROM_VALUE_HELPER = new EnumFromValueHelper<>();

        private final long mValue;

        TokenBodyColor(long value) {
            mValue = value;
        }

        public static TokenBodyColor fromValue(long value) {
            return FROM_VALUE_HELPER.fromValue(value, TokenBodyColor.class);
        }

        @Override
        public long getAsLong() {
            return mValue;
        }
    }
}
