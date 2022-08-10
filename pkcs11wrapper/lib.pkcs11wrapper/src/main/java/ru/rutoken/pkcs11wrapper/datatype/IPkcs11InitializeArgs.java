package ru.rutoken.pkcs11wrapper.datatype;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkCInitializeArgs;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.main.IPkcs11Module;
import ru.rutoken.pkcs11wrapper.main.IPkcs11MutexHandler;

/**
 * Immutable high-level representation of {@link CkCInitializeArgs}.
 * This is a structure for pkcs11 library initialization.
 *
 * @see IPkcs11Module#initializeModule(IPkcs11InitializeArgs) method.
 */
public interface IPkcs11InitializeArgs {
    @Nullable
    IPkcs11MutexHandler getMutexHandler();

    boolean isLibraryCantCreateOsThreads();

    boolean isOsLockingOk();

    long getFlags();

    CkCInitializeArgs toCkCInitializeArgs(IPkcs11LowLevelFactory factory);
}
