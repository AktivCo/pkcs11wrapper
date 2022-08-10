package ru.rutoken.pkcs11wrapper.object.factory;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;

/**
 * Finds first node with equal object class.
 */
class NodeByObjectClassFinderVisitor<Obj extends Pkcs11Object> implements ObjectFactoryNodeVisitor {
    private final Class<Obj> mObjectClass;
    @Nullable
    private ObjectFactoryNode<Obj> mNode;

    NodeByObjectClassFinderVisitor(Class<Obj> objectClass) {
        mObjectClass = Objects.requireNonNull(objectClass);
    }

    @Override
    public void visit(ObjectFactoryNode<? extends Pkcs11Object> node) {
        if (node.getObjectMaker().getObjectClass().equals(mObjectClass)) {
            @SuppressWarnings("unchecked")
            ObjectFactoryNode<Obj> foundNode = (ObjectFactoryNode<Obj>) node;
            mNode = foundNode;
        } else {
            for (ObjectFactoryNode<?> child : node.getChildren().values()) {
                child.acceptVisitor(this);
                if (null != mNode)
                    break;
            }
        }
    }

    @Nullable
    ObjectFactoryNode<Obj> getNode() {
        return mNode;
    }
}
