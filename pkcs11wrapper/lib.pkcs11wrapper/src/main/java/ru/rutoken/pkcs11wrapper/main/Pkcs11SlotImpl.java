/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.main;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.datatype.Pkcs11SlotInfo;

/**
 * Default slot implementation.
 * This class may be extended to add vendor defined extended methods.
 */
public class Pkcs11SlotImpl implements Pkcs11Slot {
    private final IPkcs11Module mModule;
    private final long mId;
    private final Pkcs11Token mToken;

    public Pkcs11SlotImpl(IPkcs11Module module, long id) {
        mModule = Objects.requireNonNull(module);
        mId = id;
        mToken = getHighLevelFactory().makeToken(this);
    }

    @Override
    public IPkcs11Module getModule() {
        return mModule;
    }

    @Override
    public long getId() {
        return mId;
    }

    @Override
    public Pkcs11SlotInfo getSlotInfo() {
        return new Pkcs11SlotInfo(getApi().C_GetSlotInfo(mId));
    }

    @Override
    public Pkcs11Token getToken() {
        return mToken;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject instanceof Pkcs11SlotImpl) {
            Pkcs11SlotImpl other = (Pkcs11SlotImpl) otherObject;
            return (this == other) || Objects.equals(mModule, other.mModule) && mId == other.mId;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mModule, mId);
    }

    @Override
    public String toString() {
        return "Pkcs11SlotImpl{" +
                "mModule=" + mModule +
                ", mId=" + mId +
                '}';
    }
}
