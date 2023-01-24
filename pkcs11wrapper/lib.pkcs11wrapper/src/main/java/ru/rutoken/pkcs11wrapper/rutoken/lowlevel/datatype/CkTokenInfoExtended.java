/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype;

public interface CkTokenInfoExtended {
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

    long getAtrLen();

    long getTokenClass();

    long getBatteryVoltage();

    long getBodyColor();

    long getFirmwareChecksum();

    long getBatteryPercentage();

    long getBatteryFlags();
}
