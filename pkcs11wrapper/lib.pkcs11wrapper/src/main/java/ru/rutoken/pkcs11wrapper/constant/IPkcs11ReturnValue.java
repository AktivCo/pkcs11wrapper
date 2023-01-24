/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11VendorExtensions;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11ReturnValue;

/**
 * This interface helps to support vendor defined values.
 * See {@link RtPkcs11ReturnValue} for example.
 */
public interface IPkcs11ReturnValue extends LongValueSupplier {
    VendorFactoryHelper<IPkcs11ReturnValue> FACTORY_HELPER = new VendorFactoryHelper<>(
            Pkcs11ReturnValue::nullableFromValue, UnknownValue::new);

    static IPkcs11ReturnValue getInstance(long value) {
        return FACTORY_HELPER.instanceByValue(value);
    }

    static IPkcs11ReturnValue getInstance(long value, IPkcs11VendorExtensions vendorExtensions) {
        return FACTORY_HELPER.instanceByValue(value, vendorExtensions.getReturnValueVendorFactory());
    }

    interface VendorFactory extends VendorFactoryHelper.VendorFactory<IPkcs11ReturnValue> {
    }

    class UnknownValue extends UnknownValueSupplier implements IPkcs11ReturnValue {
        UnknownValue(long value) {
            super(value);
        }
    }
}
