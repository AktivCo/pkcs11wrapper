package ru.rutoken.pkcs11wrapper.object.factory;

import java.lang.reflect.Constructor;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;

/**
 * Creates objects of given class by calling its constructor.
 */
class ConstructorObjectMaker<Obj extends Pkcs11Object> implements ObjectMaker<Obj> {
    private final Class<Obj> mObjectClass;

    ConstructorObjectMaker(Class<Obj> objectClass) {
        mObjectClass = Objects.requireNonNull(objectClass);
    }

    @Override
    public Class<Obj> getObjectClass() {
        return mObjectClass;
    }

    @Override
    public Obj make(long objectHandle) throws ReflectiveOperationException {
        Constructor<Obj> constructor = mObjectClass.getDeclaredConstructor(long.class);
        constructor.setAccessible(true);
        return constructor.newInstance(objectHandle);
    }
}
