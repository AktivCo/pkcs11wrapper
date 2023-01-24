/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna;

import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_TOKEN_INFO_EXTENDED;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkTokenInfoExtended;

class CkTokenInfoExtendedImpl implements CkTokenInfoExtended {
    private final CK_TOKEN_INFO_EXTENDED mData;

    CkTokenInfoExtendedImpl(CK_TOKEN_INFO_EXTENDED data) {
        mData = Objects.requireNonNull(data);
    }

    @Deprecated
    @Override
    public long getTokenType() {
        //noinspection deprecation
        return mData.ulTokenType.longValue();
    }

    @Override
    public long getProtocolNumber() {
        return mData.ulProtocolNumber.longValue();
    }

    @Override
    public long getMicrocodeNumber() {
        return mData.ulMicrocodeNumber.longValue();
    }

    @Override
    public long getOrderNumber() {
        return mData.ulOrderNumber.longValue();
    }

    @Override
    public long getFlags() {
        return mData.flags.longValue();
    }

    @Override
    public long getMaxAdminPinLen() {
        return mData.ulMaxAdminPinLen.longValue();
    }

    @Override
    public long getMinAdminPinLen() {
        return mData.ulMinAdminPinLen.longValue();
    }

    @Override
    public long getMaxUserPinLen() {
        return mData.ulMaxUserPinLen.longValue();
    }

    @Override
    public long getMinUserPinLen() {
        return mData.ulMinUserPinLen.longValue();
    }

    @Override
    public long getMaxAdminRetryCount() {
        return mData.ulMaxAdminRetryCount.longValue();
    }

    @Override
    public long getAdminRetryCountLeft() {
        return mData.ulAdminRetryCountLeft.longValue();
    }

    @Override
    public long getMaxUserRetryCount() {
        return mData.ulMaxUserRetryCount.longValue();
    }

    @Override
    public long getUserRetryCountLeft() {
        return mData.ulUserRetryCountLeft.longValue();
    }

    @Override
    public byte[] getSerialNumber() {
        return mData.serialNumber;
    }

    @Override
    public long getTotalMemory() {
        return mData.ulTotalMemory.longValue();
    }

    @Override
    public long getFreeMemory() {
        return mData.ulFreeMemory.longValue();
    }

    @Override
    public byte[] getAtr() {
        return mData.ATR;
    }

    @Override
    public long getAtrLen() {
        return mData.ulATRLen.longValue();
    }

    @Override
    public long getTokenClass() {
        return mData.ulTokenClass.longValue();
    }

    @Override
    public long getBatteryVoltage() {
        return mData.ulBatteryVoltage.longValue();
    }

    @Override
    public long getBodyColor() {
        return mData.ulBodyColor.longValue();
    }

    @Override
    public long getFirmwareChecksum() {
        return mData.ulFirmwareChecksum.longValue();
    }

    @Override
    public long getBatteryPercentage() {
        return mData.ulBatteryPercentage.longValue();
    }

    @Override
    public long getBatteryFlags() {
        return mData.ulBatteryFlags.longValue();
    }
}
