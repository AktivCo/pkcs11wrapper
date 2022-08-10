package ru.rutoken.pkcs11wrapper.attribute.longvalue;

import org.jetbrains.annotations.NotNull;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11CertificateCategory;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11VendorExtensions;

public class Pkcs11CertificateCategoryAttribute extends Pkcs11EnumLongAttribute<Pkcs11CertificateCategory> {
    public Pkcs11CertificateCategoryAttribute(IPkcs11AttributeType type) {
        super(Pkcs11CertificateCategory.class, type);
    }

    public Pkcs11CertificateCategoryAttribute(IPkcs11AttributeType type, Pkcs11CertificateCategory value) {
        super(Pkcs11CertificateCategory.class, type, value);
    }

    protected Pkcs11CertificateCategoryAttribute(IPkcs11AttributeType type, @NotNull Object value) {
        super(Pkcs11CertificateCategory.class, type, value);
    }

    @Override
    public Pkcs11CertificateCategory getEnumValue() {
        return Pkcs11CertificateCategory.fromValue(getLongValue());
    }

    @Override
    public Pkcs11CertificateCategory getEnumValue(IPkcs11VendorExtensions vendorExtensions) {
        return getEnumValue();
    }
}
