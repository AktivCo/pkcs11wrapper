package ru.rutoken.pkcs11wrapper.lowlevel.datatype;

public interface CkSessionInfo {

    long getSlotID();

    long getState();

    long getFlags();

    long getDeviceError();
}
