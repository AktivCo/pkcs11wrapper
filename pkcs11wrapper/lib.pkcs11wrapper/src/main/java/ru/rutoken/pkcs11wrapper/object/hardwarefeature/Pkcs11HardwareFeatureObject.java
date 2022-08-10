package ru.rutoken.pkcs11wrapper.object.hardwarefeature;

import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11HardwareFeatureTypeAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.object.Pkcs11BaseObject;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_HW_FEATURE_TYPE;

public class Pkcs11HardwareFeatureObject extends Pkcs11BaseObject {
    private final Pkcs11HardwareFeatureTypeAttribute mHardwareFeatureTypeAttribute =
            new Pkcs11HardwareFeatureTypeAttribute(CKA_HW_FEATURE_TYPE);

    protected Pkcs11HardwareFeatureObject(long objectHandle) {
        super(objectHandle);
    }

    public Pkcs11HardwareFeatureTypeAttribute getHardwareFeatureTypeAttributeValue(Pkcs11Session session) {
        return getAttributeValue(session, mHardwareFeatureTypeAttribute);
    }

    @Override
    public void registerAttributes() {
        super.registerAttributes();
        registerAttribute(mHardwareFeatureTypeAttribute);
    }
}
