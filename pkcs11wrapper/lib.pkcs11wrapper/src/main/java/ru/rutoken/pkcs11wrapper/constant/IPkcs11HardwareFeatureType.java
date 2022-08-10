package ru.rutoken.pkcs11wrapper.constant;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11HardwareFeatureType;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11VendorExtensions;

/**
 * This interface helps to support vendor defined values.
 */
public interface IPkcs11HardwareFeatureType extends AttributeLongValueSupplier {
    VendorFactoryHelper<IPkcs11HardwareFeatureType> FACTORY_HELPER = new VendorFactoryHelper<>(
            Pkcs11HardwareFeatureType::nullableFromValue, UnknownValue::new);

    static IPkcs11HardwareFeatureType getInstance(long value) {
        return FACTORY_HELPER.instanceByValue(value);
    }

    static IPkcs11HardwareFeatureType getInstance(long value, IPkcs11VendorExtensions vendorExtensions) {
        return FACTORY_HELPER.instanceByValue(value, vendorExtensions.getHardwareFeatureTypeVendorFactory());
    }

    interface VendorFactory extends VendorFactoryHelper.VendorFactory<IPkcs11HardwareFeatureType> {
    }

    class UnknownValue extends UnknownValueSupplier implements IPkcs11HardwareFeatureType {
        UnknownValue(long value) {
            super(value);
        }
    }
}
