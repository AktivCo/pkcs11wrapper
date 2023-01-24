/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.main;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import ru.rutoken.pkcs11wrapper.datatype.IPkcs11InitializeArgs;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11Info;
import ru.rutoken.pkcs11wrapper.reference.ApiReference;
import ru.rutoken.pkcs11wrapper.reference.AttributeFactoryReference;
import ru.rutoken.pkcs11wrapper.reference.HighLevelFactoryReference;
import ru.rutoken.pkcs11wrapper.reference.ObjectFactoryReference;

/**
 * Defines top level pkcs11 methods and gives access to underlying pkcs11 library.
 * This is an entry point for OOP high-level layer.
 */
public interface IPkcs11Module extends ApiReference, HighLevelFactoryReference, AttributeFactoryReference,
        ObjectFactoryReference {
    Pkcs11Info getInfo();

    List<Pkcs11Slot> getSlotList(boolean tokenPresent);

    @Nullable
    Pkcs11Slot waitForSlotEvent(boolean dontBlock);

    void initializeModule(@Nullable IPkcs11InitializeArgs arguments);

    void finalizeModule();
}
