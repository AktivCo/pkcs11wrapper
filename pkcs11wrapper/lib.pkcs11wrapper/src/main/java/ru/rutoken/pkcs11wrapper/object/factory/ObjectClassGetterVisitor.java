/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.factory;

import java.util.ArrayList;
import java.util.List;

import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;

/**
 * Collects unique objects classes.
 */
class ObjectClassGetterVisitor implements ObjectFactoryNodeVisitor {
    private final List<Class<? extends Pkcs11Object>> mObjectClasses = new ArrayList<>();

    @Override
    public void visit(ObjectFactoryNode<? extends Pkcs11Object> node) {
        mObjectClasses.add(node.getObjectMaker().getObjectClass());
        for (ObjectFactoryNode<?> child : node.getChildren().values()) {
            child.acceptVisitor(this);
        }
    }

    List<Class<? extends Pkcs11Object>> getObjectClasses() {
        return mObjectClasses;
    }
}
