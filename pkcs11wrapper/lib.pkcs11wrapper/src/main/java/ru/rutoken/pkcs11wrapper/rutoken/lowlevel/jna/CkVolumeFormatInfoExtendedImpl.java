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

    CK_VOLUME_FORMAT_INFO_EXTENDED getJnaValue() {
        return mData;
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
