package ru.rutoken.pkcs11wrapper.attribute;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType;

public class Pkcs11AttributeFactory implements IPkcs11AttributeFactory {
    private final Map<IPkcs11AttributeType, Class<? extends Pkcs11Attribute>> mAttributeClasses = new HashMap<>();

    public Pkcs11AttributeFactory() {
        for (Pkcs11AttributeType type : Pkcs11AttributeType.values())
            registerAttribute(type, type.getAttributeClass());
    }

    @Override
    public boolean isAttributeRegistered(IPkcs11AttributeType type) {
        return mAttributeClasses.containsKey(type);
    }

    @Override
    public void registerAttribute(IPkcs11AttributeType type, Class<? extends Pkcs11Attribute> attribute) {
        if (!isAttributeRegistered(type))
            mAttributeClasses.put(Objects.requireNonNull(type), Objects.requireNonNull(attribute));
    }

    @Override
    public Pkcs11Attribute makeAttribute(IPkcs11AttributeType type) {
        return makeAttribute(Pkcs11Attribute.class, type);
    }

    @Override
    public Pkcs11Attribute makeAttribute(IPkcs11AttributeType type, @NotNull Object value) {
        checkRegistration(type);
        try {
            Constructor<? extends Pkcs11Attribute> constructor =
                    Objects.requireNonNull(mAttributeClasses.get(type))
                            .getDeclaredConstructor(IPkcs11AttributeType.class, Object.class);
            constructor.setAccessible(true);
            return constructor.newInstance(type, value);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <Attr extends Pkcs11Attribute> Attr makeAttribute(Class<Attr> attributeClass, IPkcs11AttributeType type) {
        checkRegistration(type);
        try {
            @SuppressWarnings("unchecked") final Class<Attr> attrClass = (Class<Attr>) mAttributeClasses.get(type);
            Constructor<Attr> constructor = Objects.requireNonNull(attrClass)
                    .getDeclaredConstructor(IPkcs11AttributeType.class);
            constructor.setAccessible(true);
            Attr attribute = constructor.newInstance(type);
            if (!attributeClass.isInstance(attribute))
                throw new ClassCastException("attribute class is invalid " + attributeClass);
            return attribute;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <Attr extends Pkcs11Attribute> Attr makeAttribute(Class<Attr> attributeClass, IPkcs11AttributeType type,
                                                             @NotNull Object value) {
        checkRegistration(type);
        try {
            @SuppressWarnings("unchecked") final Class<Attr> attrClass = (Class<Attr>) mAttributeClasses.get(type);
            Constructor<Attr> constructor = Objects.requireNonNull(attrClass)
                    .getDeclaredConstructor(IPkcs11AttributeType.class, Object.class);
            constructor.setAccessible(true);
            Attr attribute = constructor.newInstance(type, value);
            if (!attributeClass.isInstance(attribute))
                throw new ClassCastException("attribute class is invalid " + attributeClass);
            return attribute;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkRegistration(IPkcs11AttributeType type) {
        if (!isAttributeRegistered(type))
            throw new RuntimeException("attribute not registered " + type);
    }
}
