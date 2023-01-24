/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.datatype;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkInfo;

/**
 * Immutable high-level representation of {@link CkInfo}.
 */
public class Pkcs11Info {
    private final Pkcs11Version mCryptokiVersion;
    private final String mManufacturerId;
    private final long mFlags;
    private final String mLibraryDescription;
    private final Pkcs11Version mLibraryVersion;

    public Pkcs11Info(CkInfo info) {
        mCryptokiVersion = new Pkcs11Version(info.getCryptokiVersion());
        mManufacturerId = new String(info.getManufacturerID());
        mFlags = info.getFlags();
        mLibraryDescription = new String(info.getLibraryDescription());
        mLibraryVersion = new Pkcs11Version(info.getLibraryVersion());
    }

    @Override
    public String toString() {
        return "Pkcs11Info{" +
                "mCryptokiVersion=" + mCryptokiVersion +
                ", mManufacturerId='" + mManufacturerId + '\'' +
                ", mFlags=" + mFlags +
                ", mLibraryDescription='" + mLibraryDescription + '\'' +
                ", mLibraryVersion=" + mLibraryVersion +
                '}';
    }

    public Pkcs11Version getCryptokiVersion() {
        return mCryptokiVersion;
    }

    public String getManufacturerId() {
        return mManufacturerId;
    }

    public long getFlags() {
        return mFlags;
    }

    public String getLibraryDescription() {
        return mLibraryDescription;
    }

    public Pkcs11Version getLibraryVersion() {
        return mLibraryVersion;
    }
}
