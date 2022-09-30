package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype;

public interface CkVolumeFormatInfoExtended {
    void setVolumeSize(long volumeSize);

    void setAccessMode(long accessMode);

    void setVolumeOwner(long volumeOwner);

    void setFlags(long flags);
}
