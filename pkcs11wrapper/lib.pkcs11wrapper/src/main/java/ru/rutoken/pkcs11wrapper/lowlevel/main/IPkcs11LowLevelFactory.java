/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.main;

import org.jetbrains.annotations.NotNull;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkCInitializeArgs;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkDate;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanism;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.Pkcs11MechanismParams;

/**
 * A factory for low-level objects creation and conversion.
 * A user provides implementation of this interface to a pkcs11wrapper and should not use it directly.
 * These objects require a factory as pkcs11wrapper creates them internally to pass into a C pkcs11 library.
 */
public interface IPkcs11LowLevelFactory extends IPkcs11VendorExtensions {
    CkCInitializeArgs makeCInitializeArgs();

    CkAttribute makeAttribute();

    CkMechanism makeMechanism();

    CkDate makeDate();

    /**
     * Converts Pkcs11MechanismParameter to low level representation
     *
     * @param parameter high-level mechanism parameters (of base interface type)
     * @return low-level mechanism parameter in underlying implementation format
     */
    @NotNull
    CkMechanism.Parameter convertMechanismParams(Pkcs11MechanismParams parameter);
}
