/*
 * Copyright (c) 2023, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.constant.internal;

import static ru.rutoken.pkcs11jna.Pkcs11Constants.CKA_VENDOR_DEFINED;

/**
 * Extended PKCS#11 constants for internal use. You do not need to use them directly.
 */
public class RtPkcs11InternalConstants {
    public final static long CK_VENDOR_PKCS11_RU_TEAM_TC26 = CKA_VENDOR_DEFINED | 0x54321000;

    public final static long BIP32 = CKA_VENDOR_DEFINED | 0x5000;
}
