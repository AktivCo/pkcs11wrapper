package ru.rutoken.pkcs11wrapper.lowlevel.main;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11CertificateType;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11HardwareFeatureType;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11KeyType;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11ObjectClass;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;

/**
 * This interface defines methods used by the pkcs11wrapper to support vendor defined values.
 */
public interface IPkcs11VendorExtensions {
    IPkcs11AttributeType.VendorFactory getAttributeTypeVendorFactory();

    IPkcs11CertificateType.VendorFactory getCertificateTypeVendorFactory();

    IPkcs11HardwareFeatureType.VendorFactory getHardwareFeatureTypeVendorFactory();

    IPkcs11KeyType.VendorFactory getKeyTypeVendorFactory();

    IPkcs11MechanismType.VendorFactory getMechanismTypeVendorFactory();

    IPkcs11ObjectClass.VendorFactory getObjectClassVendorFactory();

    IPkcs11ReturnValue.VendorFactory getReturnValueVendorFactory();
}
