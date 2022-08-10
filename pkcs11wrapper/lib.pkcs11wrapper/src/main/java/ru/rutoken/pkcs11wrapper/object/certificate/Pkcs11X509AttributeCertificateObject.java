package ru.rutoken.pkcs11wrapper.object.certificate;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_AC_ISSUER;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_ATTR_TYPES;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_OWNER;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_SERIAL_NUMBER;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_VALUE;

public class Pkcs11X509AttributeCertificateObject extends Pkcs11CertificateObject {
    private final Pkcs11ByteArrayAttribute mOwnerAttribute = new Pkcs11ByteArrayAttribute(CKA_OWNER);
    private final Pkcs11ByteArrayAttribute mAcIssuerAttribute = new Pkcs11ByteArrayAttribute(CKA_AC_ISSUER);
    private final Pkcs11ByteArrayAttribute mSerialNumberAttribute = new Pkcs11ByteArrayAttribute(CKA_SERIAL_NUMBER);
    private final Pkcs11ByteArrayAttribute mAttrTypesAttribute = new Pkcs11ByteArrayAttribute(CKA_ATTR_TYPES);
    private final Pkcs11ByteArrayAttribute mValueAttribute = new Pkcs11ByteArrayAttribute(CKA_VALUE);

    protected Pkcs11X509AttributeCertificateObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11ByteArrayAttribute getOwnerAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mOwnerAttribute);
    }

    public Pkcs11ByteArrayAttribute getAcIssuerAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mAcIssuerAttribute);
    }

    public Pkcs11ByteArrayAttribute getSerialNumberAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mSerialNumberAttribute);
    }

    public Pkcs11ByteArrayAttribute getAttrTypesAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mAttrTypesAttribute);
    }

    public Pkcs11ByteArrayAttribute getValueAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mValueAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mOwnerAttribute);
        registerAttribute(mAcIssuerAttribute);
        registerAttribute(mSerialNumberAttribute);
        registerAttribute(mAttrTypesAttribute);
        registerAttribute(mValueAttribute);
    }
}
