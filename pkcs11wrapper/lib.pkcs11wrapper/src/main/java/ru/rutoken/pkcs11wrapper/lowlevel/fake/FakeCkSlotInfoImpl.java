/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkSlotInfo;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkVersion;

class FakeCkSlotInfoImpl implements CkSlotInfo {
    private final long mFlags;

    FakeCkSlotInfoImpl(long flags) {
        mFlags = flags;
    }

    @Override
    public byte[] getSlotDescription() {
        return "Fake Rutoken".getBytes();
    }

    @Override
    public byte[] getManufacturerID() {
        return "".getBytes();
    }

    @Override
    public long getFlags() {
        return mFlags;
    }

    @Override
    public CkVersion getHardwareVersion() {
        return new FakeCkVersionImpl((byte) 0, (byte) 0);
    }

    @Override
    public CkVersion getFirmwareVersion() {
        return new FakeCkVersionImpl((byte) 0, (byte) 0);
    }
}
