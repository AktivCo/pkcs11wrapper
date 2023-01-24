/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.reference;

import ru.rutoken.pkcs11wrapper.object.factory.IPkcs11ObjectFactory;

public interface ObjectFactoryReference {
    IPkcs11ObjectFactory getObjectFactory();
}
