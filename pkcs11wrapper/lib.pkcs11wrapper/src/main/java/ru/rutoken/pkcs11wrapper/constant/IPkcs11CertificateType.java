package ru.rutoken.pkcs11wrapper.constant;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11CertificateType;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11VendorExtensions;

/**
 * This interface helps to support vendor defined values.
 */
public interface IPkcs11CertificateType extends AttributeLongValueSupplier {
    VendorFactoryHelper<IPkcs11CertificateType> FACTORY_HELPER = new VendorFactoryHelper<>(
            Pkcs11CertificateType::nullableFromValue, UnknownValue::new);

    static IPkcs11CertificateType getInstance(long value) {
        return FACTORY_HELPER.instanceByValue(value);
    }

    static IPkcs11CertificateType getInstance(long value, IPkcs11VendorExtensions vendorExtensions) {
        return FACTORY_HELPER.instanceByValue(value, vendorExtensions.getCertificateTypeVendorFactory());
    }

    interface VendorFactory extends VendorFactoryHelper.VendorFactory<IPkcs11CertificateType> {
    }

    class UnknownValue extends UnknownValueSupplier implements IPkcs11CertificateType {
        UnknownValue(long value) {
            super(value);
        }
    }
}
