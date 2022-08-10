package ru.rutoken.pkcs11wrapper.lowlevel.datatype;

public interface CkSlotInfo {

    byte[] getSlotDescription();

    byte[] getManufacturerID();

    long getFlags();

    CkVersion getHardwareVersion();

    CkVersion getFirmwareVersion();
}
