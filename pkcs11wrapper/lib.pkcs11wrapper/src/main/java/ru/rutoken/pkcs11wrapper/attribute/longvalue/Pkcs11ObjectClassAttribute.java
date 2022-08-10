package ru.rutoken.pkcs11wrapper.attribute.longvalue;

import org.jetbrains.annotations.NotNull;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11ObjectClass;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11VendorExtensions;

public class Pkcs11ObjectClassAttribute extends Pkcs11EnumLongAttribute<IPkcs11ObjectClass> {
    public Pkcs11ObjectClassAttribute(IPkcs11AttributeType type) {
        super(IPkcs11ObjectClass.class, type);
    }

    public Pkcs11ObjectClassAttribute(IPkcs11AttributeType type, IPkcs11ObjectClass value) {
        super(IPkcs11ObjectClass.class, type, value);
    }

    protected Pkcs11ObjectClassAttribute(IPkcs11AttributeType type, @NotNull Object value) {
        super(IPkcs11ObjectClass.class, type, value);
    }

    @Override
    public IPkcs11ObjectClass getEnumValue() {
        return IPkcs11ObjectClass.getInstance(getLongValue());
    }

    @Override
    public IPkcs11ObjectClass getEnumValue(IPkcs11VendorExtensions vendorExtensions) {
        return IPkcs11ObjectClass.getInstance(getLongValue(), vendorExtensions);
    }
}


