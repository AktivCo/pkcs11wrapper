/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.main;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11MechanismInfo;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11TokenInfo;
import ru.rutoken.pkcs11wrapper.reference.SlotReference;

/**
 * @see Pkcs11Slot#getToken()
 */
public interface Pkcs11Token extends SlotReference {
    int LABEL_LENGTH = 32;

    void initToken(@Nullable String pin, String label);

    Pkcs11TokenInfo getTokenInfo();

    List<IPkcs11MechanismType> getMechanismList();

    Pkcs11MechanismInfo getMechanismInfo(IPkcs11MechanismType type);

    Pkcs11Session openSession(boolean rwSession);

    void closeAllSessions();
}
