package ru.rutoken.pkcs11wrapper.attribute.longvalue;

import org.jetbrains.annotations.NotNull;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11CertificateType;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11VendorExtensions;

public class Pkcs11CertificateTypeAttribute extends Pkcs11EnumLongAttribute<IPkcs11CertificateType> {
    public Pkcs11CertificateTypeAttribute(IPkcs11AttributeType type) {
        super(IPkcs11CertificateType.class, type);
    }

    public Pkcs11CertificateTypeAttribute(IPkcs11AttributeType type, IPkcs11CertificateType value) {
        super(IPkcs11CertificateType.class, type, value);
    }

    protected Pkcs11CertificateTypeAttribute(IPkcs11AttributeType type, @NotNull Object value) {
        super(IPkcs11CertificateType.class, type, value);
    }

    @Override
    public IPkcs11CertificateType getEnumValue() {
        return IPkcs11CertificateType.getInstance(getLongValue());
    }

    @Override
    public IPkcs11CertificateType getEnumValue(IPkcs11VendorExtensions vendorExtensions) {
        return IPkcs11CertificateType.getInstance(getLongValue(), vendorExtensions);
    }
}
