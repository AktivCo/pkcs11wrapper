/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11VendorExtensions;

/**
 * This interface helps to support vendor defined values.
 */
public interface IPkcs11AttributeType extends LongValueSupplier {
    VendorFactoryHelper<IPkcs11AttributeType> FACTORY_HELPER =
            new VendorFactoryHelper<>(Pkcs11AttributeType::nullableFromValue, UnknownValue::new);

    static IPkcs11AttributeType getInstance(long value) {
        return FACTORY_HELPER.instanceByValue(value);
    }

    static IPkcs11AttributeType getInstance(long value, IPkcs11VendorExtensions vendorExtensions) {
        return FACTORY_HELPER.instanceByValue(value, vendorExtensions.getAttributeTypeVendorFactory());
    }

    interface VendorFactory extends VendorFactoryHelper.VendorFactory<IPkcs11AttributeType> {
    }

    class UnknownValue extends UnknownValueSupplier implements IPkcs11AttributeType {
        UnknownValue(long value) {
            super(value);
        }
    }
}
