package ru.rutoken.pkcs11wrapper.rutoken.datatype;

import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVolumeInfoExtended;
import ru.rutoken.pkcs11wrapper.rutoken.manager.RtPkcs11FlashManager.AccessMode;

public class VolumeInfoExtended {
    private final long mId;
    private final long mSize;
    private final AccessMode mAccessMode;
    private final long mOwner;
    private final long mFlags;

    public VolumeInfoExtended(CkVolumeInfoExtended volumeInfo) {
        mId = volumeInfo.getVolumeId();
        mSize = volumeInfo.getVolumeSize();
        mAccessMode = AccessMode.fromValue(volumeInfo.getAccessMode());
        mOwner = volumeInfo.getVolumeOwner();
        mFlags = volumeInfo.getFlags();
    }

    public long getId() {
        return mId;
    }

    public long getSize() {
        return mSize;
    }

    public AccessMode getAccessMode() {
        return mAccessMode;
    }

    public long getOwner() {
        return mOwner;
    }

    public long getFlags() {
        return mFlags;
    }
}
