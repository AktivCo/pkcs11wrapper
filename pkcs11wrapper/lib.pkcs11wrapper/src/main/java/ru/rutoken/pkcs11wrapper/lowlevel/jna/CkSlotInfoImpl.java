/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_SLOT_INFO;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkSlotInfo;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkVersion;

class CkSlotInfoImpl implements CkSlotInfo {
    private final CK_SLOT_INFO mData;

    CkSlotInfoImpl(CK_SLOT_INFO data) {
        mData = Objects.requireNonNull(data);
    }

    @Override
    public byte[] getSlotDescription() {
        return mData.slotDescription;
    }

    @Override
    public byte[] getManufacturerID() {
        return mData.manufacturerID;
    }

    @Override
    public long getFlags() {
        return mData.flags.longValue();
    }

    @Override
    public CkVersion getHardwareVersion() {
        return new CkVersionImpl(mData.hardwareVersion);
    }

    @Override
    public CkVersion getFirmwareVersion() {
        return new CkVersionImpl(mData.firmwareVersion);
    }
}
