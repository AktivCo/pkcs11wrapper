package ru.rutoken.pkcs11wrapper.reference;

import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.main.IPkcs11Api;
import ru.rutoken.pkcs11wrapper.main.IPkcs11HighLevelFactory;
import ru.rutoken.pkcs11wrapper.main.IPkcs11Module;
import ru.rutoken.pkcs11wrapper.object.factory.IPkcs11ObjectFactory;

public interface ModuleReference extends ApiReference, HighLevelFactoryReference, AttributeFactoryReference,
        ObjectFactoryReference {
    @Override
    default IPkcs11Api getApi() {
        return getModule().getApi();
    }

    @Override
    default IPkcs11HighLevelFactory getHighLevelFactory() {
        return getModule().getHighLevelFactory();
    }

    @Override
    default IPkcs11AttributeFactory getAttributeFactory() {
        return getModule().getAttributeFactory();
    }

    @Override
    default IPkcs11ObjectFactory getObjectFactory() {
        return getModule().getObjectFactory();
    }

    IPkcs11Module getModule();
}
