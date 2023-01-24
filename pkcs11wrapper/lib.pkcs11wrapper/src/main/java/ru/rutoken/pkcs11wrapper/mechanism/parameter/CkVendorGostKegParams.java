/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.mechanism.parameter;

import java.util.Objects;

// FIXME: remove after updating PKCS to 2.3.0. See https://jira.aktivco.ru/browse/PKCSECP-1633 for more information.
public class CkVendorGostKegParams implements Pkcs11MechanismParams {
    private final byte[] mPublicData;
    private final byte[] mUkm;

    public CkVendorGostKegParams(byte[] publicData, byte[] ukm) {
        mPublicData = Objects.requireNonNull(publicData);
        mUkm = Objects.requireNonNull(ukm);
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }

    public byte[] getPublicData() {
        return mPublicData;
    }

    public byte[] getUkm() {
        return mUkm;
    }
}
