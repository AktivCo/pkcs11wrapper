package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype;

public interface CkLocalPinInfo {
    void setPinId(long pinId);

    long getPinId();

    long getMinSize();

    long getMaxSize();

    long getMaxRetryCount();

    long getCurrentRetryCount();

    long getFlags();
}
