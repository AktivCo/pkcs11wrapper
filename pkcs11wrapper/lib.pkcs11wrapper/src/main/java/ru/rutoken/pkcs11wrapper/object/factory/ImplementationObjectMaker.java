package ru.rutoken.pkcs11wrapper.object.factory;

import java.lang.reflect.Constructor;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;

/**
 * Creates instances of implementation class.
 *
 * @param <Obj>  interface class
 * @param <Impl> implementation class
 */
class ImplementationObjectMaker<Obj extends Pkcs11Object, Impl extends Obj> implements ObjectMaker<Obj> {
    private final Class<Obj> mObjectClass;
    private final Class<Impl> mImplementationObjectClass;

    ImplementationObjectMaker(Class<Obj> objectClass, Class<Impl> implementationObjectClass) {
        mObjectClass = Objects.requireNonNull(objectClass);
        mImplementationObjectClass = Objects.requireNonNull(implementationObjectClass);
    }

    @Override
    public Class<Obj> getObjectClass() {
        return mObjectClass;
    }

    @Override
    public Obj make(long objectHandle) throws ReflectiveOperationException {
        Constructor<Impl> constructor = mImplementationObjectClass.getDeclaredConstructor(long.class);
        constructor.setAccessible(true);
        return constructor.newInstance(objectHandle);
    }
}
