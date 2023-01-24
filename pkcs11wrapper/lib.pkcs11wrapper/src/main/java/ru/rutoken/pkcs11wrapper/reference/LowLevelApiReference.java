/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.reference;

import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;

public interface LowLevelApiReference extends LowLevelFactoryReference {
    @Override
    default IPkcs11LowLevelFactory getLowLevelFactory() {
        return getLowLevelApi().getLowLevelFactory();
    }

    IPkcs11LowLevelApi getLowLevelApi();
}
