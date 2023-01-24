/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.datatype;

public interface CkMechanismInfo {

    long getMinKeySize();

    long getMaxKeySize();

    long getFlags();
}
