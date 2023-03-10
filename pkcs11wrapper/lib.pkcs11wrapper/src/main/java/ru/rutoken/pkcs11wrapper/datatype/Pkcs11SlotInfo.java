/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.datatype;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11Flag;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkSlotInfo;

/**
 * Immutable high-level representation of {@link CkSlotInfo}.
 */
@SuppressWarnings("unused")
public class Pkcs11SlotInfo {
    private final String mSlotDescription;
    private final String mManufacturerId;
    private final Pkcs11Version mHardwareVersion;
    private final Pkcs11Version mFirmwareVersion;
    private final long mFlags;

    public Pkcs11SlotInfo(CkSlotInfo slotInfo) {
        mSlotDescription = new String(slotInfo.getSlotDescription());
        mManufacturerId = new String(slotInfo.getManufacturerID());
        mHardwareVersion = new Pkcs11Version(slotInfo.getHardwareVersion());
        mFirmwareVersion = new Pkcs11Version(slotInfo.getFirmwareVersion());
        mFlags = slotInfo.getFlags();
    }

    public String getSlotDescription() {
        return mSlotDescription;
    }

    public String getManufacturerId() {
        return mManufacturerId;
    }

    public Pkcs11Version getHardwareVersion() {
        return mHardwareVersion;
    }

    public Pkcs11Version getFirmwareVersion() {
        return mFirmwareVersion;
    }

    public boolean isTokenPresent() {
        return checkFlag(Pkcs11Flag.CKF_TOKEN_PRESENT);
    }

    public boolean isRemovableDevice() {
        return checkFlag(Pkcs11Flag.CKF_REMOVABLE_DEVICE);
    }

    public boolean isHwSlot() {
        return checkFlag(Pkcs11Flag.CKF_HW_SLOT);
    }

    public long getFlags() {
        return mFlags;
    }

    @Override
    public String toString() {
        return "Pkcs11SlotInfo{" +
                "mSlotDescription='" + mSlotDescription + '\'' +
                ", mManufacturerId='" + mManufacturerId + '\'' +
                ", mHardwareVersion=" + mHardwareVersion +
                ", mFirmwareVersion=" + mFirmwareVersion +
                ", mFlags=" + mFlags +
                '}';
    }

    private boolean checkFlag(Pkcs11Flag flag) {
        return (mFlags & flag.getAsLong()) != 0L;
    }
}
