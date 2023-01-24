/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.reference;

import ru.rutoken.pkcs11wrapper.main.Pkcs11Slot;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Token;

public interface TokenReference extends SlotReference {
    @Override
    default Pkcs11Slot getSlot() {
        return getToken().getSlot();
    }

    Pkcs11Token getToken();
}
