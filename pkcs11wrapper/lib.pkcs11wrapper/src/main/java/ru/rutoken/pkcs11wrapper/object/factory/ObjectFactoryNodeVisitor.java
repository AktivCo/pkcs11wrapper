/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.factory;

import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;

interface ObjectFactoryNodeVisitor {
    void visit(ObjectFactoryNode<? extends Pkcs11Object> node) throws Exception;
}
