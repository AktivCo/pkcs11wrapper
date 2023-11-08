/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant.standard;

import ru.rutoken.pkcs11wrapper.constant.LongValueSupplier;

public enum Pkcs11Flag implements LongValueSupplier {
    /* Bit flags that provide capabilities of the slot */
    CKF_TOKEN_PRESENT(0x00000001L),
    CKF_REMOVABLE_DEVICE(0x00000002L),
    /**
     * Hardware slot.
     */
    CKF_HW_SLOT(0x00000004L),

    /**
     * Has random # generator.
     */
    CKF_RNG(0x00000001L),
    /**
     * Token is write-protected.
     */
    CKF_WRITE_PROTECTED(0x00000002L),
    /**
     * User must login.
     */
    CKF_LOGIN_REQUIRED(0x00000004L),
    /**
     * Normal user's PIN is set.
     */
    CKF_USER_PIN_INITIALIZED(0x00000008L),

    /**
     * If it is set, that means that every time the state of cryptographic operations of a session is successfully
     * saved, all keys needed to continue those operations are stored in the state.
     */
    CKF_RESTORE_KEY_NOT_NEEDED(0x00000020L),
    /**
     * If it is set, that means that the token has some sort of clock. The time on that clock is returned in the token
     * info structure.
     */
    CKF_CLOCK_ON_TOKEN(0x00000040L),
    /**
     * If it is set, that means that there is some way for the user to login without sending a PIN through the Cryptoki
     * library itself.
     */
    CKF_PROTECTED_AUTHENTICATION_PATH(0x00000100L),
    /**
     * If it is true, that means that a single session with the token can perform dual simultaneous cryptographic
     * operations (digest and encrypt; decrypt and digest; sign and encrypt; and decrypt and sign).
     */
    CKF_DUAL_CRYPTO_OPERATIONS(0x00000200L),
    /**
     * If it is true, the token has been initialized using C_InitializeToken or an equivalent mechanism outside the
     * scope of PKCS #11. Calling C_InitializeToken when this flag is set will cause the token to be reinitialized.
     */
    CKF_TOKEN_INITIALIZED(0x00000400L),
    /**
     * If it is true, the token supports secondary authentication for private key objects.
     */
    CKF_SECONDARY_AUTHENTICATION(0x00000800L),
    /**
     * If it is true, an incorrect user login PIN has been entered at least once since the last successful
     * authentication.
     */
    CKF_USER_PIN_COUNT_LOW(0x00010000L),
    /**
     * If it is true, supplying an incorrect user PIN will it to become locked.
     */
    CKF_USER_PIN_FINAL_TRY(0x00020000L),
    /**
     * If it is true, the user PIN has been locked. User login to the token is not possible.
     */
    CKF_USER_PIN_LOCKED(0x00040000L),
    /**
     * If it is true, the user PIN value is the default value set by token initialization or manufacturing, or the PIN
     * has been expired by the card.
     */
    CKF_USER_PIN_TO_BE_CHANGED(0x00080000L),
    /**
     * If it is true, an incorrect SO login PIN has been entered at least once since the last successful authentication.
     */
    CKF_SO_PIN_COUNT_LOW(0x00100000L),
    /**
     * If it is true, supplying an incorrect SO PIN will it to become locked.
     */
    CKF_SO_PIN_FINAL_TRY(0x00200000L),
    /**
     * If it is true, the SO PIN has been locked. SO login to the token is not possible.
     */
    CKF_SO_PIN_LOCKED(0x00400000L),
    /**
     * If it is true, the SO PIN value is the default value set by token initialization or manufacturing, or the PIN has
     * been expired by the card.
     */
    CKF_SO_PIN_TO_BE_CHANGED(0x00800000L),
    CKF_ERROR_STATE(0x01000000L),

    CKF_RW_SESSION(0x00000002L),
    /**
     * No parallel.
     */
    CKF_SERIAL_SESSION(0x00000004L),

    /**
     * The CKF_ARRAY_ATTRIBUTE flag identifies an attribute which consists of an array of values.
     */
    CKF_ARRAY_ATTRIBUTE(0x40000000L),

    /**
     * Performed by HW.
     */
    CKF_HW(0x00000001L),

    /* Specify whether a mechanism can be used for a particular task */
    CKF_ENCRYPT(0x00000100L),
    CKF_DECRYPT(0x00000200L),
    CKF_DIGEST(0x00000400L),
    CKF_SIGN(0x00000800L),
    CKF_SIGN_RECOVER(0x00001000L),
    CKF_VERIFY(0x00002000L),
    CKF_VERIFY_RECOVER(0x00004000L),
    CKF_GENERATE(0x00008000L),
    CKF_GENERATE_KEY_PAIR(0x00010000L),
    CKF_WRAP(0x00020000L),
    CKF_UNWRAP(0x00040000L),
    CKF_DERIVE(0x00080000L),

    /* Describe a token's EC capabilities not available in mechanism information */
    CKF_EC_F_P(0x00100000L),
    CKF_EC_F_2M(0x00200000L),
    CKF_EC_ECPARAMETERS(0x00400000L),
    CKF_EC_NAMEDCURVE(0x00800000L),
    CKF_EC_UNCOMPRESS(0x01000000L),
    CKF_EC_COMPRESS(0x02000000L),

    CKF_EXTENSION(0x80000000L),

    /* Bit flags that provide capabilities of the slot */
    CKF_LIBRARY_CANT_CREATE_OS_THREADS(0x00000001L),
    CKF_OS_LOCKING_OK(0x00000002L),

    /**
     * CKF_DONT_BLOCK is for the function C_WaitForSlotEvent.
     */
    CKF_DONT_BLOCK(1L),

    CKF_NEXT_OTP(0x00000001L),
    CKF_EXCLUDE_TIME(0x00000002L),
    CKF_EXCLUDE_COUNTER(0x00000004L),
    CKF_EXCLUDE_CHALLENGE(0x00000008L),
    CKF_EXCLUDE_PIN(0x00000010L),
    CKF_USER_FRIENDLY_OTP(0x00000020L);

    private final long mValue;

    Pkcs11Flag(long value) {
        mValue = value;
    }

    @Override
    public long getAsLong() {
        return mValue;
    }
}
