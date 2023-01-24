/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.factory;

/**
 * Helper interface for attributes registration in objects.
 */
public interface AttributeRegistrator {
    /**
     * Adds subclass attributes into an attributes map.
     */
    void registerAttributes();
}
