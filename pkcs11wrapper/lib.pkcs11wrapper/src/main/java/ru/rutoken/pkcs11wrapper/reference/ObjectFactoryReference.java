package ru.rutoken.pkcs11wrapper.reference;

import ru.rutoken.pkcs11wrapper.object.factory.IPkcs11ObjectFactory;

public interface ObjectFactoryReference {
    IPkcs11ObjectFactory getObjectFactory();
}
