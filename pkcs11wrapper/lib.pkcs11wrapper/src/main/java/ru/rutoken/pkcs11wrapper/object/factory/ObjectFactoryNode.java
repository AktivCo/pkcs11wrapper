package ru.rutoken.pkcs11wrapper.object.factory;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;

/**
 * We have to select {@link Pkcs11Object} type depending on many attribute values.
 * Each node is associated with concrete {@link Pkcs11Object} subclass and attribute.
 * Node's children are mapped by attribute values
 */
class ObjectFactoryNode<Obj extends Pkcs11Object> {
    private final Map<Object/*attribute value*/, ObjectFactoryNode<?>> mChildren = new HashMap<>();
    /**
     * Creates instance of specified object class.
     * Helps to handle cases when specified object class is just an interface.
     */
    private final ObjectMaker<Obj> mObjectMaker;
    @Nullable
    private final Pkcs11Attribute mAttribute;
    @Nullable
    private ObjectFactoryNode<?> mParent;
    @Nullable
    private Object mParentAttributeValue;

    ObjectFactoryNode(Class<Obj> objectClass, @Nullable Pkcs11Attribute attribute) {
        this(new ConstructorObjectMaker<>(objectClass), attribute);
    }

    ObjectFactoryNode(ObjectMaker<Obj> objectMaker, @Nullable Pkcs11Attribute attribute) {
        mObjectMaker = Objects.requireNonNull(objectMaker);
        mAttribute = attribute;
    }

    void acceptVisitor(ObjectFactoryNodeVisitor visitor) {
        try {
            visitor.visit(this);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Visitor " + visitor.getClass().getSimpleName() + " exception: " + e.getMessage(), e);
        }
    }

    ObjectMaker<Obj> getObjectMaker() {
        return mObjectMaker;
    }

    @Nullable
    public Pkcs11Attribute getAttribute() {
        return mAttribute;
    }

    @Nullable
    public ObjectFactoryNode<?> getParent() {
        return mParent;
    }

    private void setParent(ObjectFactoryNode<?> parent) {
        if (this == parent)
            throw new RuntimeException("node cannot add self instance as parent");
        mParent = Objects.requireNonNull(parent);
    }

    Map<Object, ObjectFactoryNode<?>> getChildren() {
        return mChildren;
    }

    void addChild(Object attributeValue, ObjectFactoryNode<?> child) {
        if (this == child)
            throw new RuntimeException("node cannot add self instance as child");
        if (null == mAttribute)
            throw new RuntimeException("can not add child node if no attribute was specified");
        if (mChildren.containsKey(attributeValue))
            throw new IllegalArgumentException("node already has attribute value: " + attributeValue);
        child.setParent(this);
        innerAddChild(Objects.requireNonNull(attributeValue), Objects.requireNonNull(child));
    }

    @Nullable
    Object getParentAttributeValue() {
        return mParentAttributeValue;
    }

    private void innerAddChild(Object attributeValue, ObjectFactoryNode<?> child) {
        mChildren.put(attributeValue, child);
        child.mParentAttributeValue = attributeValue;
    }
}
