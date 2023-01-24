/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.datatype;

public interface CkTokenInfo {

    byte[] getLabel();

    byte[] getManufacturerID();

    byte[] getModel();

    byte[] getSerialNumber();

    long getFlags();

    long getMaxSessionCount();

    long getSessionCount();

    long getMaxRwSessionCount();

    long getRwSessionCount();

    long getMaxPinLen();

    long getMinPinLen();

    long getTotalPublicMemory();

    long getFreePublicMemory();

    long getTotalPrivateMemory();

    long getFreePrivateMemory();

    CkVersion getHardwareVersion();

    CkVersion getFirmwareVersion();

    byte[] getUtcTime();
}
