package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype;

public interface CkTokenInfoExtended {
    long getSizeofThisStructure();

    @Deprecated
    long getTokenType();

    long getProtocolNumber();

    long getMicrocodeNumber();

    long getOrderNumber();

    long getFlags();

    long getMaxAdminPinLen();

    long getMinAdminPinLen();

    long getMaxUserPinLen();

    long getMinUserPinLen();

    long getMaxAdminRetryCount();

    long getAdminRetryCountLeft();

    long getMaxUserRetryCount();

    long getUserRetryCountLeft();

    byte[] getSerialNumber();

    long getTotalMemory();

    long getFreeMemory();

    byte[] getAtr();

    long getTokenClass();

    long getBatteryVoltage();

    long getBodyColor();

    long getFirmwareChecksum();

    long getBatteryPercentage();

    long getBatteryFlags();
}
