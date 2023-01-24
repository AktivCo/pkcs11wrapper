/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.manager;

import ru.rutoken.pkcs11wrapper.reference.SessionReference;

public interface Pkcs11RandomNumberManager extends SessionReference {
    void seedRandom(byte[] seed);

    byte[] generateRandom(int length);
}
