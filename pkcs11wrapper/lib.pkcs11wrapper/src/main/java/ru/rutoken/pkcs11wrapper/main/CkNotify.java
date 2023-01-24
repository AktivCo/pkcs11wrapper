/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.main;

/**
 * Callback interface, called by pkcs11 library.
 */
public interface CkNotify {
    void onEvent(long session, long event, Object application);
}
