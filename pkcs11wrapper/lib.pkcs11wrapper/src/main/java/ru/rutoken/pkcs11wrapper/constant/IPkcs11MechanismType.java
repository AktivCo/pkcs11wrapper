/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11VendorExtensions;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType;

/**
 * This interface helps to support vendor defined values.
 * See {@link RtPkcs11MechanismType} for example.
 */
public interface IPkcs11MechanismType extends AttributeLongValueSupplier {
    VendorFactoryHelper<IPkcs11MechanismType> FACTORY_HELPER = new VendorFactoryHelper<>(
            Pkcs11MechanismType::nullableFromValue, UnknownValue::new);

    static IPkcs11MechanismType getInstance(long value) {
        return FACTORY_HELPER.instanceByValue(value);
    }

    static IPkcs11MechanismType getInstance(long value, IPkcs11VendorExtensions vendorExtensions) {
        return FACTORY_HELPER.instanceByValue(value, vendorExtensions.getMechanismTypeVendorFactory());
    }

    interface VendorFactory extends VendorFactoryHelper.VendorFactory<IPkcs11MechanismType> {
    }

    class UnknownValue extends UnknownValueSupplier implements IPkcs11MechanismType {
        UnknownValue(long value) {
            super(value);
        }
    }
}
