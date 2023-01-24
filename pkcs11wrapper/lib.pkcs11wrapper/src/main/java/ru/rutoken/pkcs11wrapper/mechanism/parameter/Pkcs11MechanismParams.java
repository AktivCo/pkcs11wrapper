/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.mechanism.parameter;

import org.jetbrains.annotations.NotNull;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanism;

/**
 * This is base interface for all mechanism parameters.
 * Derived classes should follow naming convention from pkcs11 standard for simplicity.
 * Mechanism parameters are not divided to low-level and high-level like many other pkcs11 data types.
 * {@link LowLevelConverterVisitor} is used for conversion from Java classes
 * to underling pkcs11 implementation.
 */
public interface Pkcs11MechanismParams {
    void acceptVisitor(Visitor visitor);

    /**
     * User can extend this interface to add support of vendor defined {@link Pkcs11MechanismParams}
     * or params that are not currently defined in pkcs11wrapper.
     */
    // TODO implement all mechanism parameters from pkcs11 standard
    interface Visitor {
        void visit(Pkcs11ByteArrayMechanismParams parameter);

        void visit(CkGostR3410DeriveParams parameter);

        void visit(CkGostR3410KeyWrapParams parameter);

        void visit(CkKdfTreeGostParams parameter);

        void visit(CkVendorGostKegParams parameter);

        void visit(Pkcs11LongMechanismParams parameter);

        void visit(CkVendorVkoGostR3410_2012_512Params parameter);

        void visit(CkEcdh1DeriveParams parameter);

        void visit(CkRsaPkcsPssParams ckRsaPkcsPssParams);
    }

    interface LowLevelConverterVisitor extends Visitor {
        @NotNull
        CkMechanism.Parameter getConverted();

        interface Factory {
            LowLevelConverterVisitor make();
        }
    }
}
