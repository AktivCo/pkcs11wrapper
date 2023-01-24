/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.fake;

import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkTokenInfoExtended;

public class FakeCkTokenInfoExtendedImpl implements CkTokenInfoExtended {
    private final long mTokenType;

    public FakeCkTokenInfoExtendedImpl(long tokenType) {
        mTokenType = tokenType;
    }

    @Deprecated
    @Override
    public long getTokenType() {
        return mTokenType;
    }

    @Override
    public long getProtocolNumber() {
        return 0;
    }

    @Override
    public long getMicrocodeNumber() {
        return 0;
    }

    @Override
    public long getOrderNumber() {
        return 0;
    }

    @Override
    public long getFlags() {
        return 0;
    }

    @Override
    public long getMaxAdminPinLen() {
        return 0;
    }

    @Override
    public long getMinAdminPinLen() {
        return 0;
    }

    @Override
    public long getMaxUserPinLen() {
        return 0;
    }

    @Override
    public long getMinUserPinLen() {
        return 0;
    }

    @Override
    public long getMaxAdminRetryCount() {
        return 0;
    }

    @Override
    public long getAdminRetryCountLeft() {
        return 0;
    }

    @Override
    public long getMaxUserRetryCount() {
        return 0;
    }

    @Override
    public long getUserRetryCountLeft() {
        return 0;
    }

    @Override
    public byte[] getSerialNumber() {
        return new byte[0];
    }

    @Override
    public long getTotalMemory() {
        return 0;
    }

    @Override
    public long getFreeMemory() {
        return 0;
    }

    @Override
    public byte[] getAtr() {
        return new byte[0];
    }

    @Override
    public long getAtrLen() {
        return 0;
    }

    @Override
    public long getTokenClass() {
        return 0;
    }

    @Override
    public long getBatteryVoltage() {
        return 0;
    }

    @Override
    public long getBodyColor() {
        return 0;
    }

    @Override
    public long getFirmwareChecksum() {
        return 0;
    }

    @Override
    public long getBatteryPercentage() {
        return 0;
    }

    @Override
    public long getBatteryFlags() {
        return 0;
    }
}
