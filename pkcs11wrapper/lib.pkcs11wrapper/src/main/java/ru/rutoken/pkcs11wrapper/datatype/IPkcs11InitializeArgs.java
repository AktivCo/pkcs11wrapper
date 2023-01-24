/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.datatype;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkCInitializeArgs;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.main.IPkcs11Module;

/**
 * Immutable high-level representation of {@link CkCInitializeArgs}.
 * This is a structure for pkcs11 library initialization.
 *
 * @see IPkcs11Module#initializeModule(IPkcs11InitializeArgs) method.
 */
public interface IPkcs11InitializeArgs {
    boolean isLibraryCantCreateOsThreads();

    boolean isOsLockingOk();

    CkCInitializeArgs toCkCInitializeArgs(IPkcs11LowLevelFactory factory);
}
