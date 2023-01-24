/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype;

public interface CkVendorRestoreFactoryDefaultsParams {
    void setAdminPin(byte[] adminPin);

    void setInitParam(CkRutokenInitParam initParam);

    void setNewEmitentKey(byte[] newEmitentKey);

    void setNewEmitentKeyRetryCount(long newEmitentKeyRetryCount);
}
