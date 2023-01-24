/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna;

import ru.rutoken.pkcs11jna.CK_VOLUME_INFO_EXTENDED;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVolumeInfoExtended;

import java.util.Objects;

class CkVolumeInfoExtendedImpl implements CkVolumeInfoExtended {
    private final CK_VOLUME_INFO_EXTENDED mData;

    CkVolumeInfoExtendedImpl(CK_VOLUME_INFO_EXTENDED data) {
        mData = Objects.requireNonNull(data);
    }

    void copyToJnaStructure(CK_VOLUME_INFO_EXTENDED to) {
        to.idVolume.setValue(getVolumeId());
        to.volumeSize.setValue(getVolumeSize());
        to.accessMode.setValue(getAccessMode());
        to.volumeOwner.setValue(getVolumeOwner());
        to.flags.setValue(getFlags());
    }

    @Override
    public long getVolumeId() {
        return mData.idVolume.longValue();
    }

    @Override
    public long getVolumeSize() {
        return mData.volumeSize.longValue();
    }

    @Override
    public long getAccessMode() {
        return mData.accessMode.longValue();
    }

    @Override
    public long getVolumeOwner() {
        return mData.volumeOwner.longValue();
    }

    @Override
    public long getFlags() {
        return mData.flags.longValue();
    }
}
