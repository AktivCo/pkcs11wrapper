/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_TOKEN_INFO;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkTokenInfo;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkVersion;

class CkTokenInfoImpl implements CkTokenInfo {
    private final CK_TOKEN_INFO mData;

    CkTokenInfoImpl(CK_TOKEN_INFO data) {
        mData = Objects.requireNonNull(data);
    }

    @Override
    public byte[] getLabel() {
        return mData.label;
    }

    @Override
    public byte[] getManufacturerID() {
        return mData.manufacturerID;
    }

    @Override
    public byte[] getModel() {
        return mData.model;
    }

    @Override
    public byte[] getSerialNumber() {
        return mData.serialNumber;
    }

    @Override
    public long getFlags() {
        return mData.flags.longValue();
    }

    @Override
    public long getMaxSessionCount() {
        return mData.ulMaxSessionCount.longValue();
    }

    @Override
    public long getSessionCount() {
        return mData.ulSessionCount.longValue();
    }

    @Override
    public long getMaxRwSessionCount() {
        return mData.ulMaxRwSessionCount.longValue();
    }

    @Override
    public long getRwSessionCount() {
        return mData.ulRwSessionCount.longValue();
    }

    @Override
    public long getMaxPinLen() {
        return mData.ulMaxPinLen.longValue();
    }

    @Override
    public long getMinPinLen() {
        return mData.ulMinPinLen.longValue();
    }

    @Override
    public long getTotalPublicMemory() {
        return mData.ulTotalPublicMemory.longValue();
    }

    @Override
    public long getFreePublicMemory() {
        return mData.ulFreePublicMemory.longValue();
    }

    @Override
    public long getTotalPrivateMemory() {
        return mData.ulTotalPrivateMemory.longValue();
    }

    @Override
    public long getFreePrivateMemory() {
        return mData.ulFreePrivateMemory.longValue();
    }

    @Override
    public CkVersion getHardwareVersion() {
        return new CkVersionImpl(mData.hardwareVersion);
    }

    @Override
    public CkVersion getFirmwareVersion() {
        return new CkVersionImpl(mData.firmwareVersion);
    }

    @Override
    public byte[] getUtcTime() {
        return mData.utcTime;
    }
}
