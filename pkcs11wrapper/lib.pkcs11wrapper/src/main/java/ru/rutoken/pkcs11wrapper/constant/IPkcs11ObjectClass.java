/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ObjectClass;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11VendorExtensions;

/**
 * This interface helps to support vendor defined values.
 */
public interface IPkcs11ObjectClass extends AttributeLongValueSupplier {
    VendorFactoryHelper<IPkcs11ObjectClass> FACTORY_HELPER = new VendorFactoryHelper<>(
            Pkcs11ObjectClass::nullableFromValue, UnknownValue::new);

    static IPkcs11ObjectClass getInstance(long value) {
        return FACTORY_HELPER.instanceByValue(value);
    }

    static IPkcs11ObjectClass getInstance(long value, IPkcs11VendorExtensions vendorExtensions) {
        return FACTORY_HELPER.instanceByValue(value, vendorExtensions.getObjectClassVendorFactory());
    }

    interface VendorFactory extends VendorFactoryHelper.VendorFactory<IPkcs11ObjectClass> {
    }

    class UnknownValue extends UnknownValueSupplier implements IPkcs11ObjectClass {
        UnknownValue(long value) {
            super(value);
        }
    }
}
