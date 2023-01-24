/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_VERSION;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkVersion;

class CkVersionImpl implements CkVersion {
    private final CK_VERSION mData;

    CkVersionImpl(CK_VERSION data) {
        mData = Objects.requireNonNull(data);
    }

    @Override
    public byte getMajor() {
        return mData.major;
    }

    @Override
    public byte getMinor() {
        return mData.minor;
    }
}
