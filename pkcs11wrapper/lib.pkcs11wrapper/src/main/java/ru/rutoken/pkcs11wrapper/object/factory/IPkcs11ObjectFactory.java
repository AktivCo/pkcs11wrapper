package ru.rutoken.pkcs11wrapper.object.factory;

import java.util.List;

import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11ObjectManager;
import ru.rutoken.pkcs11wrapper.object.Pkcs11BaseObject;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;
import ru.rutoken.pkcs11wrapper.reference.AttributeFactoryReference;

/**
 * Object factory is used for {@link Pkcs11Object} instances creation.
 * Also this factory helps to generate template by object class,
 * which may be used, for example, to quickly find all certificates on token.
 *
 * @see Pkcs11ObjectManager#findObjectsAtOnce(Class)
 */
// TODO implement possibility to register vendor defined objects
public interface IPkcs11ObjectFactory {
    /**
     * Creates a simple {@link Pkcs11BaseObject} (no inheritance take place) to wrap objectHandle.
     * No calls to pkcs11 library and hardware token are made.
     */
    default Pkcs11Object makeObject(long objectHandle) {
        return makeObject(Pkcs11BaseObject.class, objectHandle);
    }

    /**
     * Creates an instance of {@link Pkcs11Object} of specified class.
     * No calls to pkcs11 library and hardware token are made.
     */
    <Obj extends Pkcs11Object> Obj makeObject(Class<Obj> objectClass, long objectHandle);

    /**
     * Creates an instance of {@link Pkcs11Object} which type is deduced using attributes read from token.
     */
    Pkcs11Object makeObject(Pkcs11Session session, long objectHandle);

    /**
     * Creates a template using object class. Resulting template contains only attributes with filled values.
     * That template can be used to find objects represented by objectClass.
     * Use {@link Pkcs11BaseObject#makeClearTemplate(IPkcs11AttributeFactory)} or
     * {@link Pkcs11BaseObject#makeClearTemplate(AttributeFactoryReference)} if you need template containing all
     * object attributes.
     */
    <Obj extends Pkcs11Object> List<Pkcs11Attribute> makeTemplate(Class<Obj> objectClass);

    boolean isObjectClassRegistered(Class<? extends Pkcs11Object> objectClass);

    List<Class<? extends Pkcs11Object>> getRegisteredObjectClasses();
}
