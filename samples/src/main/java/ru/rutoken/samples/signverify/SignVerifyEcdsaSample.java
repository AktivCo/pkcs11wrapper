/*
 * Copyright (c) 2022, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.samples.signverify;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.samples.createobjects.CreateEcdsaKeyPairSample;
import ru.rutoken.samples.utils.RtPkcs11Module;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_ECDSA;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_SHA256;
import static ru.rutoken.samples.signverify.Utils.findPrivateKeyById;
import static ru.rutoken.samples.signverify.Utils.findPublicKeyById;
import static ru.rutoken.samples.utils.Constants.DEFAULT_USER_PIN;
import static ru.rutoken.samples.utils.Constants.ECDSA_KEY_PAIR_ID;
import static ru.rutoken.samples.utils.Pkcs11Operations.initializePkcs11AndGetFirstToken;
import static ru.rutoken.samples.utils.Utils.*;

/**
 * This sample creates raw ECDSA signature for two blocks of data and verifies it.
 * Expects corresponding ECDSA key pair on token, you should run {@link CreateEcdsaKeyPairSample} to create it.
 */
public class SignVerifyEcdsaSample {
    private static final byte[] FIRST_DATA_TO_SIGN = {0x01, 0x02, 0x03};
    private static final byte[] SECOND_DATA_TO_SIGN = {0x04, 0x05, 0x06};
    /**
     * We will find ECDSA key pair by its ID. Change this field to your key pair ID.
     */
    private static final byte[] KEY_PAIR_ID = ECDSA_KEY_PAIR_ID;

    public static void runSample(RtPkcs11Module module) {
        try (var session = initializePkcs11AndGetFirstToken(module).openSession(true)) {
            if (hasUnsupportedMechanisms(SignVerifyEcdsaSample.class, session.getToken(), CKM_SHA256, CKM_ECDSA))
                return;

            try (var ignore = session.login(Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)) {
                // We find key pair by its ID.
                println("Finding signer private key");
                final var signerPrivateKey = findPrivateKeyById(session, KEY_PAIR_ID);

                printHex("The first data block to sign:", FIRST_DATA_TO_SIGN);
                printHex("The second data block to sign:", SECOND_DATA_TO_SIGN);

                session.getDigestManager().digestInit(Pkcs11Mechanism.make(CKM_SHA256));
                session.getDigestManager().digestUpdate(FIRST_DATA_TO_SIGN);
                session.getDigestManager().digestUpdate(SECOND_DATA_TO_SIGN);
                final var digest = session.getDigestManager().digestFinal();

                final var signature =
                        session.getSignManager().signAtOnce(digest, Pkcs11Mechanism.make(CKM_ECDSA), signerPrivateKey);
                printHex("Signed data:", signature);

                println("Finding signer public key");
                final var signerPublicKey = findPublicKeyById(session, KEY_PAIR_ID);

                println("Verifying ECDSA signature");
                final var result = session.getVerifyManager()
                        .verifyAtOnce(digest, signature, Pkcs11Mechanism.make(CKM_ECDSA), signerPublicKey);

                if (result)
                    println("ECDSA signature is valid");
                else
                    throw new IllegalStateException("ECDSA signature is invalid");

                printSuccessfulExit(SignVerifyEcdsaSample.class);
            }
        } catch (Exception e) {
            printError(SignVerifyEcdsaSample.class, e);
        } finally {
            module.finalizeModule();
            printSampleDelimiter();
        }
    }

    public static void main(String[] args) {
        runSample(RtPkcs11Module.getInstance(args));
    }
}
