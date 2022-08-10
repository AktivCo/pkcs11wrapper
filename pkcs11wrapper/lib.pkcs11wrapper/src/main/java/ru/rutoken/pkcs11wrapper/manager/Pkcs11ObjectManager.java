package ru.rutoken.pkcs11wrapper.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11KeyPair;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;
import ru.rutoken.pkcs11wrapper.reference.SessionReference;

/**
 * Collects all object management pkcs11 methods.
 * Note that search results may depend on a login state.
 *
 * @see Pkcs11Session#login(Pkcs11UserType, String)
 */
public interface Pkcs11ObjectManager extends SessionReference {
    int getMaxObjectCount();

    void setMaxObjectCount(int maxObjectCount);

    /**
     * Finds one object that matches the template.
     *
     * @throws IllegalStateException if multiple objects are found.
     */
    Pkcs11Object findSingleObject(List<Pkcs11Attribute> template);

    /**
     * Finds one object of known object class.
     * Template is completed with attributes deduced from object class.
     *
     * @throws IllegalStateException if multiple objects are found.
     */
    <Obj extends Pkcs11Object> Obj findSingleObject(Class<Obj> objectClass);

    /**
     * Finds one object of known class, template is provided by user.
     * Template is completed with attributes deduced from object class.
     *
     * @throws IllegalStateException if multiple objects are found.
     */
    <Obj extends Pkcs11Object> Obj findSingleObject(Class<Obj> objectClass,
                                                    List<Pkcs11Attribute> template);

    /**
     * Finds all objects that match the template.
     */
    default List<Pkcs11Object> findObjectsAtOnce(List<Pkcs11Attribute> template) {
        final List<Pkcs11Object> objects = new ArrayList<>();
        findObjectsInit(template);
        List<Pkcs11Object> foundObjects;
        do {
            foundObjects = findObjects(getMaxObjectCount());
            objects.addAll(foundObjects);
        } while (!foundObjects.isEmpty());
        findObjectsFinal();
        return objects;
    }

    /**
     * Finds all objects of known object class, template is deduced from object class
     */
    default <Obj extends Pkcs11Object> List<Obj> findObjectsAtOnce(Class<Obj> objectClass) {
        @SuppressWarnings("unchecked") final List<Obj> objects = (List<Obj>) findObjectsAtOnce(
                getObjectFactory().makeTemplate(objectClass));
        return objects;
    }

    /**
     * Finds all objects of known class, template is provided by user.
     * Template is completed with template deduced from class
     */
    default <Obj extends Pkcs11Object> List<Obj> findObjectsAtOnce(Class<Obj> objectClass,
                                                                   List<Pkcs11Attribute> template) {
        final Set<Pkcs11Attribute> uniqueAttributes = new HashSet<>(template);
        uniqueAttributes.addAll(getObjectFactory().makeTemplate(objectClass));
        @SuppressWarnings("unchecked") final List<Obj> objects = (List<Obj>) findObjectsAtOnce(
                new ArrayList<>(uniqueAttributes));
        return objects;
    }

    void findObjectsInit(List<Pkcs11Attribute> template);

    List<Pkcs11Object> findObjects(int maxObjectCount);

    void findObjectsFinal();

    /**
     * Creates object on token.
     *
     * @return Object which type is dynamic and deduced automatically
     */
    Pkcs11Object createObject(List<Pkcs11Attribute> template);

    /**
     * Creates object on token. Returns an instance of specified class representing this object.
     * There is no additional interaction with a token.
     * Caller is responsible for specifying correct class that may represent created object.
     */
    <Obj extends Pkcs11Object> Obj createObject(Class<Obj> objectClass, List<Pkcs11Attribute> template);

    default <Obj extends Pkcs11Object> Obj copyObject(Obj object) {
        return copyObject(object, Collections.emptyList());
    }

    <Obj extends Pkcs11Object> Obj copyObject(Obj object, List<Pkcs11Attribute> template);

    void destroyObject(Pkcs11Object object);

    default void destroyKeyPair(Pkcs11KeyPair<?, ?> keyPair) {
        try {
            destroyObject(keyPair.getPublicKey());
        } finally {
            destroyObject(keyPair.getPrivateKey());
        }
    }

    long getObjectSize(Pkcs11Object object);
}
