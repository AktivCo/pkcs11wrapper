/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11VendorExtensions;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyType;

/**
 * This interface helps to support vendor defined values.
 * See {@link RtPkcs11KeyType} for example.
 */
public interface IPkcs11KeyType extends AttributeLongValueSupplier {
    VendorFactoryHelper<IPkcs11KeyType> FACTORY_HELPER = new VendorFactoryHelper<>(
            Pkcs11KeyType::nullableFromValue, UnknownValue::new);

    static IPkcs11KeyType getInstance(long value) {
        return FACTORY_HELPER.instanceByValue(value);
    }

    static IPkcs11KeyType getInstance(long value, IPkcs11VendorExtensions vendorExtensions) {
        return FACTORY_HELPER.instanceByValue(value, vendorExtensions.getKeyTypeVendorFactory());
    }

    interface VendorFactory extends VendorFactoryHelper.VendorFactory<IPkcs11KeyType> {
    }

    class UnknownValue extends UnknownValueSupplier implements IPkcs11KeyType {
        UnknownValue(long value) {
            super(value);
        }
    }
}
