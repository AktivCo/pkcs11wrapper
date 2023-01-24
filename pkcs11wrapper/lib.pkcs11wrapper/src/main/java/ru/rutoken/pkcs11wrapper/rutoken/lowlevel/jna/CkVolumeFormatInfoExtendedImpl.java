/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna;

import com.sun.jna.NativeLong;
import ru.rutoken.pkcs11jna.CK_VOLUME_FORMAT_INFO_EXTENDED;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVolumeFormatInfoExtended;

import java.util.Objects;

class CkVolumeFormatInfoExtendedImpl implements CkVolumeFormatInfoExtended {
    private final CK_VOLUME_FORMAT_INFO_EXTENDED mData;

    CkVolumeFormatInfoExtendedImpl(CK_VOLUME_FORMAT_INFO_EXTENDED data) {
        mData = Objects.requireNonNull(data);
    }

    void copyToJnaStructure(CK_VOLUME_FORMAT_INFO_EXTENDED to) {
        to.ulVolumeSize.setValue(mData.ulVolumeSize.longValue());
        to.accessMode.setValue(mData.accessMode.longValue());
        to.volumeOwner.setValue(mData.volumeOwner.longValue());
        to.flags.setValue(mData.flags.longValue());
    }

    @Override
    public void setVolumeSize(long volumeSize) {
        mData.ulVolumeSize = new NativeLong(volumeSize);
    }

    @Override
    public void setAccessMode(long accessMode) {
        mData.accessMode = new NativeLong(accessMode);
    }

    @Override
    public void setVolumeOwner(long volumeOwner) {
        mData.volumeOwner = new NativeLong(volumeOwner);
    }

    @Override
    public void setFlags(long flags) {
        mData.flags = new NativeLong(flags);
    }
}
