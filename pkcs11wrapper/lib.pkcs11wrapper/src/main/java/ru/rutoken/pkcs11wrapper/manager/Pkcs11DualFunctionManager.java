/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.manager;

import ru.rutoken.pkcs11wrapper.reference.SessionReference;

public interface Pkcs11DualFunctionManager extends SessionReference {
    byte[] digestEncryptUpdate(byte[] part);

    byte[] decryptDigestUpdate(byte[] part);

    byte[] signEncryptUpdate(byte[] part);

    byte[] decryptVerifyUpdate(byte[] encryptedPart);
}
