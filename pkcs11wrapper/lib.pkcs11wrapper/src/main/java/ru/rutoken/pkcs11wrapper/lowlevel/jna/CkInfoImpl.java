/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_INFO;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkInfo;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkVersion;

class CkInfoImpl implements CkInfo {
    private final CK_INFO mData;

    CkInfoImpl(CK_INFO data) {
        mData = Objects.requireNonNull(data);
    }

    @Override
    public CkVersion getCryptokiVersion() {
        return new CkVersionImpl(mData.cryptokiVersion);
    }

    @Override
    public byte[] getManufacturerID() {
        return mData.manufacturerID;
    }

    @Override
    public long getFlags() {
        return mData.flags.longValue();
    }

    @Override
    public byte[] getLibraryDescription() {
        return mData.libraryDescription;
    }

    @Override
    public CkVersion getLibraryVersion() {
        return new CkVersionImpl(mData.libraryVersion);
    }
}
