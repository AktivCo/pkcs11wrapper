package ru.rutoken.pkcs11wrapper.object.factory;

import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;

interface ObjectFactoryNodeVisitor {
    void visit(ObjectFactoryNode<? extends Pkcs11Object> node) throws Exception;
}
