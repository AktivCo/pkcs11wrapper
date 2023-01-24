/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.datatype;

import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVolumeFormatInfoExtended;
import ru.rutoken.pkcs11wrapper.rutoken.manager.RtPkcs11FlashManager.AccessMode;

public class VolumeFormatInfoExtended {
    private final long mVolumeSize;
    private final AccessMode mAccessMode;
    private final long mVolumeOwner;
    private final long mFlags;

    public VolumeFormatInfoExtended(long volumeSize, AccessMode accessMode, long volumeOwner, long flags) {
        mVolumeSize = volumeSize;
        mAccessMode = accessMode;
        mVolumeOwner = volumeOwner;
        mFlags = flags;
    }

    public CkVolumeFormatInfoExtended toCkVolumeFormatInfoExtended(IRtPkcs11LowLevelFactory factory) {
        final CkVolumeFormatInfoExtended info = factory.makeVolumeFormatInfoExtended();
        info.setVolumeSize(mVolumeSize);
        info.setAccessMode(mAccessMode.getAsLong());
        info.setVolumeOwner(mVolumeOwner);
        info.setFlags(mFlags);
        return info;
    }
}
