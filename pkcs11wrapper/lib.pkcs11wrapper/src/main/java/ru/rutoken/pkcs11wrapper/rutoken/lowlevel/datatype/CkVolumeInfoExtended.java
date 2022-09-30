package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype;

public interface CkVolumeInfoExtended {
    long getVolumeId();

    long getVolumeSize();

    long getAccessMode();

    long getVolumeOwner();

    long getFlags();
}
