/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.manager.impl;

import ru.rutoken.pkcs11wrapper.main.Pkcs11Token;
import ru.rutoken.pkcs11wrapper.reference.TokenReference;

import java.util.Objects;

public class BaseTokenManager implements TokenReference {
    protected final Pkcs11Token mToken;

    protected BaseTokenManager(Pkcs11Token token) {
        mToken = Objects.requireNonNull(token);
    }

    @Override
    public Pkcs11Token getToken() {
        return mToken;
    }
}
