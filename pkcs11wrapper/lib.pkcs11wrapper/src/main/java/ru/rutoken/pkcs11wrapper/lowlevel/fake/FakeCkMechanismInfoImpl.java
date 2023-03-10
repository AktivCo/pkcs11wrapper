/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanismInfo;

class FakeCkMechanismInfoImpl implements CkMechanismInfo {
    @Override
    public long getMinKeySize() {
        return 0;
    }

    @Override
    public long getMaxKeySize() {
        return 256;
    }

    @Override
    public long getFlags() {
        return 0;
    }
}
