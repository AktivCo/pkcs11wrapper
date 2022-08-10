package ru.rutoken.pkcs11wrapper.rutoken.attribute;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11AttributeType;

public class RtPkcs11AttributeFactory extends Pkcs11AttributeFactory {
    public RtPkcs11AttributeFactory() {
        super();
        for (RtPkcs11AttributeType type : RtPkcs11AttributeType.values())
            registerAttribute(type, type.getAttributeClass());
    }
}
