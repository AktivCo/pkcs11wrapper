/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.main;

import org.jetbrains.annotations.Nullable;

import java.io.Closeable;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11SessionInfo;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11TokenInfo;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11DecryptionManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11DigestManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11DualFunctionManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11EncryptionManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11KeyManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11ObjectManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11RandomNumberManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11SignManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11VerifyManager;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11KeyObject;
import ru.rutoken.pkcs11wrapper.reference.TokenReference;

public interface Pkcs11Session extends TokenReference, Closeable {
    @Override
    void close();

    long getSessionHandle();

    Pkcs11ObjectManager getObjectManager();

    Pkcs11EncryptionManager getEncryptionManager();

    Pkcs11DecryptionManager getDecryptionManager();

    Pkcs11DigestManager getDigestManager();

    Pkcs11SignManager getSignManager();

    Pkcs11VerifyManager getVerifyManager();

    Pkcs11DualFunctionManager getDualFunctionManager();

    Pkcs11KeyManager getKeyManager();

    Pkcs11RandomNumberManager getRandomNumberManager();

    Pkcs11SessionInfo getSessionInfo();

    void initPin(@Nullable String pin);

    void setPin(@Nullable String oldPin, @Nullable String newPin);

    byte[] getOperationState();

    void setOperationState(byte[] operationState, Pkcs11KeyObject encryptionKey, Pkcs11Object authenticationKey);

    /**
     * Makes all token sessions to be logged in.
     *
     * @param userType user type
     * @param pin      can be null if {@link Pkcs11TokenInfo#isProtectedAuthenticationPath()} is true
     * @return closeable LoginGuard object
     */
    LoginGuard login(Pkcs11UserType userType, @Nullable String pin);

    /**
     * Helper interface for automatic logout.
     */
    interface LoginGuard extends Closeable {
        @Override
        void close();
    }
}
