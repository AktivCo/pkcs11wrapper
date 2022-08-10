package ru.rutoken.pkcs11wrapper.object.factory;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.attribute.Attributes;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;

/**
 * Creates {@link Pkcs11Object} instance depending on attribute values read from token.
 */
class HierarchyObjectMakerVisitor implements ObjectFactoryNodeVisitor {
    private final Pkcs11Session mSession;
    private final long mObjectHandle;
    private Pkcs11Object mObject;

    HierarchyObjectMakerVisitor(Pkcs11Session session, long objectHandle) {
        mSession = Objects.requireNonNull(session);
        mObjectHandle = objectHandle;
    }

    @Override
    public void visit(ObjectFactoryNode<? extends Pkcs11Object> node) throws ReflectiveOperationException {
        final Pkcs11Attribute attribute = node.getAttribute();
        if (null != attribute) {
            Attributes.getAttributeValue(mSession, mObjectHandle, attribute);
            final ObjectFactoryNode<?> child = node.getChildren().get(attribute.getValue());
            if (null != child) {
                child.acceptVisitor(this);
                return;
            }
        }
        mObject = node.getObjectMaker().make(mObjectHandle);
    }

    public Pkcs11Object getObject() {
        return Objects.requireNonNull(mObject);
    }
}
