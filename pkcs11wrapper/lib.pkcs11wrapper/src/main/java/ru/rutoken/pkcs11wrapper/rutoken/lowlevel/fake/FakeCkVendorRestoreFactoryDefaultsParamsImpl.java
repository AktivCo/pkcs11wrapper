/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.fake;

import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkRutokenInitParam;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorRestoreFactoryDefaultsParams;

class FakeCkVendorRestoreFactoryDefaultsParamsImpl implements CkVendorRestoreFactoryDefaultsParams {
    @Override
    public void setAdminPin(byte[] adminPin) {

    }

    @Override
    public void setInitParam(CkRutokenInitParam initParam) {

    }

    @Override
    public void setNewEmitentKey(byte[] newEmitentKey) {

    }

    @Override
    public void setNewEmitentKeyRetryCount(long newEmitentKeyRetryCount) {

    }
}
