/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.attribute.longvalue;

import org.jetbrains.annotations.NotNull;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11HardwareFeatureType;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11VendorExtensions;

public class Pkcs11HardwareFeatureTypeAttribute extends Pkcs11EnumLongAttribute<IPkcs11HardwareFeatureType> {
    public Pkcs11HardwareFeatureTypeAttribute(IPkcs11AttributeType type) {
        super(IPkcs11HardwareFeatureType.class, type);
    }

    public Pkcs11HardwareFeatureTypeAttribute(IPkcs11AttributeType type, IPkcs11HardwareFeatureType value) {
        super(IPkcs11HardwareFeatureType.class, type, value);
    }

    protected Pkcs11HardwareFeatureTypeAttribute(IPkcs11AttributeType type, @NotNull Object value) {
        super(IPkcs11HardwareFeatureType.class, type, value);
    }

    @Override
    public IPkcs11HardwareFeatureType getEnumValue() {
        return IPkcs11HardwareFeatureType.getInstance(getLongValue());
    }

    @Override
    public IPkcs11HardwareFeatureType getEnumValue(IPkcs11VendorExtensions vendorExtensions) {
        return IPkcs11HardwareFeatureType.getInstance(getLongValue(), vendorExtensions);
    }
}
