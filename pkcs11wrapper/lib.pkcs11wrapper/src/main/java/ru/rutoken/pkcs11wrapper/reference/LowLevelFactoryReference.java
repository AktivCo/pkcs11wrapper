/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.reference;

import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11VendorExtensions;

public interface LowLevelFactoryReference extends VendorExtensionsReference {
    @Override
    default IPkcs11VendorExtensions getVendorExtensions() {
        return getLowLevelFactory();
    }

    IPkcs11LowLevelFactory getLowLevelFactory();
}
