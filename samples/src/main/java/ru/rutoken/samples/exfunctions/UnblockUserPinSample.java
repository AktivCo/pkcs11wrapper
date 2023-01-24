/*
 * Copyright (c) 2022, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.samples.exfunctions;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Token;
import ru.rutoken.samples.utils.RtPkcs11Module;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue.CKR_PIN_LOCKED;
import static ru.rutoken.samples.utils.Constants.DEFAULT_ADMIN_PIN;
import static ru.rutoken.samples.utils.Constants.DEFAULT_USER_PIN;
import static ru.rutoken.samples.utils.Pkcs11Operations.initializePkcs11AndGetFirstToken;
import static ru.rutoken.samples.utils.Utils.*;

public class UnblockUserPinSample {

    public static void runSample(RtPkcs11Module module) {
        try (var session = initializePkcs11AndGetFirstToken(module).openSession(true)) {
            printLoginAttemptsLeft(session.getToken());
            println("Blocking user PIN");
            while (true) {
                // Try to log in as user with wrong pin to block it
                try (var ignore = session.login(Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN + "wrong")) {
                    // Nothing to do, just checking login
                } catch (Pkcs11Exception e) {
                    if (e.getCode() == CKR_PIN_LOCKED) {
                        println("User PIN blocked");
                        break;
                    }
                }
            }
            printLoginAttemptsLeft(session.getToken());
            // Log in as admin
            try (var ignore = session.login(Pkcs11UserType.CKU_SO, DEFAULT_ADMIN_PIN)) {
                println("Unblocking user PIN");
                session.unblockUserPIN();
                println("User PIN unblocked");
            }
            // Try to log in as user again after unblocking
            try (var ignore = session.login(Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)) {
                println("User logged in successfully");
                printLoginAttemptsLeft(session.getToken());
            }
            printSuccessfulExit(UnblockUserPinSample.class);
        } catch (Exception e) {
            printError(UnblockUserPinSample.class, e);
        } finally {
            module.finalizeModule();
            printSampleDelimiter();
        }
    }

    private static void printLoginAttemptsLeft(RtPkcs11Token token) {
        println("User login attempts left: " + token.getTokenInfoExtended().getUserRetryCountLeft());
    }

    public static void main(String[] args) {
        runSample(RtPkcs11Module.getInstance(args));
    }
}
