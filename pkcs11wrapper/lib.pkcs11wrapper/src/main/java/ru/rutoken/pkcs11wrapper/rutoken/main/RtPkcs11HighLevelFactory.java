/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.main;

import ru.rutoken.pkcs11wrapper.main.Pkcs11HighLevelFactory;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Slot;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Token;

public class RtPkcs11HighLevelFactory extends Pkcs11HighLevelFactory {
    @Override
    public RtPkcs11Token makeToken(Pkcs11Slot slot) {
        return new RtPkcs11Token(slot);
    }

    @Override
    public RtPkcs11Session makeSession(Pkcs11Token token, long sessionHandle) {
        return new RtPkcs11Session((RtPkcs11Token) token, sessionHandle);
    }
}
