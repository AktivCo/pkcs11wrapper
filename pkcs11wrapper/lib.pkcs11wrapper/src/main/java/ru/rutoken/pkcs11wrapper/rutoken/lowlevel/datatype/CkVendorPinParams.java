/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype;

public interface CkVendorPinParams {
    void setUserType(long userType);

    void setPin(byte[] pin);
}
