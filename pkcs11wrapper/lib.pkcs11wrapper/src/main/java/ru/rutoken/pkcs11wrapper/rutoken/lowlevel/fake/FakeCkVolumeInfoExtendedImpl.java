/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.fake;

import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVolumeInfoExtended;

class FakeCkVolumeInfoExtendedImpl implements CkVolumeInfoExtended {
    @Override
    public long getVolumeId() {
        return 0;
    }

    @Override
    public long getVolumeSize() {
        return 0;
    }

    @Override
    public long getAccessMode() {
        return 0;
    }

    @Override
    public long getVolumeOwner() {
        return 0;
    }

    @Override
    public long getFlags() {
        return 0;
    }
}
