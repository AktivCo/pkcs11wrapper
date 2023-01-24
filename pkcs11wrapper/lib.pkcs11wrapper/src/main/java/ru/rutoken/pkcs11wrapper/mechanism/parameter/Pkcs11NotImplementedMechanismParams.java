/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.mechanism.parameter;

/**
 * Stub class for not implemented mechanism parameter.
 */
public class Pkcs11NotImplementedMechanismParams implements Pkcs11MechanismParams {
    public Pkcs11NotImplementedMechanismParams() {
        throw new RuntimeException("This mechanism parameter is not implemented yet");
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
    }
}
