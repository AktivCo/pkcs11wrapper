/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.reference;

import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.main.IPkcs11Api;

public interface ApiReference extends LowLevelApiReference {
    @Override
    default IPkcs11LowLevelApi getLowLevelApi() {
        return getApi().getLowLevelApi();
    }

    IPkcs11Api getApi();
}
