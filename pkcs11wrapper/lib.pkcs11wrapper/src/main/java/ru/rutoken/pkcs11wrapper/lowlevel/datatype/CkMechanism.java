/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.datatype;

import org.jetbrains.annotations.Nullable;

public interface CkMechanism {
    /**
     * @param parameter in underlying implementation format
     */
    void setMechanism(long mechanismType, @Nullable Parameter parameter);

    /**
     * Marker interface for mechanism parameter in underlying implementation format
     */
    interface Parameter {
    }
}
