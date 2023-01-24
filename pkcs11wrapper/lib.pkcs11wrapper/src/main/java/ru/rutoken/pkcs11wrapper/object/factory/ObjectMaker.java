/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.factory;

import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;

/**
 * Interface for creating instances of {@link Pkcs11Object}.
 */
interface ObjectMaker<Obj extends Pkcs11Object> {
    /**
     * @return Interface class of instance that is created in {@link #make(long)} method
     */
    Class<Obj> getObjectClass();

    Obj make(long objectHandle) throws ReflectiveOperationException;
}
