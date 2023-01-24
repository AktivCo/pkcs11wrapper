/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.reference;

import ru.rutoken.pkcs11wrapper.main.IPkcs11Module;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Slot;

public interface SlotReference extends ModuleReference {
    @Override
    default IPkcs11Module getModule() {
        return getSlot().getModule();
    }

    Pkcs11Slot getSlot();
}

