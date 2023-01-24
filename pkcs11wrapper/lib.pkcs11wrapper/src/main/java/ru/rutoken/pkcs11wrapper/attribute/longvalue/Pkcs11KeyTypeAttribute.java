/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.attribute.longvalue;

import org.jetbrains.annotations.NotNull;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11KeyType;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11VendorExtensions;

public class Pkcs11KeyTypeAttribute extends Pkcs11EnumLongAttribute<IPkcs11KeyType> {
    public Pkcs11KeyTypeAttribute(IPkcs11AttributeType type) {
        super(IPkcs11KeyType.class, type);
    }

    public Pkcs11KeyTypeAttribute(IPkcs11AttributeType type, IPkcs11KeyType value) {
        super(IPkcs11KeyType.class, type, value);
    }

    protected Pkcs11KeyTypeAttribute(IPkcs11AttributeType type, @NotNull Object value) {
        super(IPkcs11KeyType.class, type, value);
    }

    @Override
    public IPkcs11KeyType getEnumValue() {
        return IPkcs11KeyType.getInstance(getLongValue());
    }

    @Override
    public IPkcs11KeyType getEnumValue(IPkcs11VendorExtensions vendorExtensions) {
        return IPkcs11KeyType.getInstance(getLongValue(), vendorExtensions);
    }
}
