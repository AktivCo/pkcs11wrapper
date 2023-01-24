/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.mechanism;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanism;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.Pkcs11MechanismParams;

class Pkcs11MechanismImpl implements Pkcs11Mechanism {
    private final IPkcs11MechanismType mMechanismType;
    @Nullable
    private final Pkcs11MechanismParams mParameter;

    Pkcs11MechanismImpl(IPkcs11MechanismType type, @Nullable Pkcs11MechanismParams parameter) {
        mMechanismType = Objects.requireNonNull(type);
        mParameter = parameter;
    }

    @Override
    public IPkcs11MechanismType getMechanismType() {
        return mMechanismType;
    }

    @Override
    @Nullable
    public Pkcs11MechanismParams getParameter() {
        return mParameter;
    }

    @Override
    public CkMechanism toCkMechanism(IPkcs11LowLevelFactory factory) {
        final CkMechanism mechanism = factory.makeMechanism();
        mechanism.setMechanism(mMechanismType.getAsLong(),
                mParameter != null ? factory.convertMechanismParams(mParameter) : null);
        return mechanism;
    }
}
