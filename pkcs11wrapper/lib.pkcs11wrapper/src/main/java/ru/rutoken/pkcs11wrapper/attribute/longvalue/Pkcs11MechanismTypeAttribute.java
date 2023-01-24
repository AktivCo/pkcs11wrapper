/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.attribute.longvalue;

import org.jetbrains.annotations.NotNull;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11VendorExtensions;

public class Pkcs11MechanismTypeAttribute extends Pkcs11EnumLongAttribute<IPkcs11MechanismType> {
    public Pkcs11MechanismTypeAttribute(IPkcs11AttributeType type) {
        super(IPkcs11MechanismType.class, type);
    }

    public Pkcs11MechanismTypeAttribute(IPkcs11AttributeType type, IPkcs11MechanismType value) {
        super(IPkcs11MechanismType.class, type, value);
    }

    protected Pkcs11MechanismTypeAttribute(IPkcs11AttributeType type, @NotNull Object value) {
        super(IPkcs11MechanismType.class, type, value);
    }

    @Override
    public IPkcs11MechanismType getEnumValue() {
        return IPkcs11MechanismType.getInstance(getLongValue());
    }

    @Override
    public IPkcs11MechanismType getEnumValue(IPkcs11VendorExtensions vendorExtensions) {
        return IPkcs11MechanismType.getInstance(getLongValue(), vendorExtensions);
    }
}
