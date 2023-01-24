/*
 * Copyright (c) 2022, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.samples.exfunctions;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.samples.utils.RtPkcs11Module;

import java.util.Random;

import static ru.rutoken.samples.utils.Constants.DEFAULT_USER_PIN;
import static ru.rutoken.samples.utils.Pkcs11Operations.initializePkcs11AndGetFirstToken;
import static ru.rutoken.samples.utils.Utils.*;

public class SetTokenNameSample {

    public static void runSample(RtPkcs11Module module) {
        try (var session = initializePkcs11AndGetFirstToken(module).openSession(true)) {
            try (var ignore = session.login(Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)) {
                final var newTokenName = "Rutoken #" + new Random().nextInt(20);
                printlnf("Renaming user token from `%s` to `%s`",
                        new String(session.getTokenName()),
                        newTokenName);
                session.setTokenName(newTokenName);
                printlnf("Token renamed, new token name: `%s`", new String(session.getTokenName()));
            }
            printSuccessfulExit(SetTokenNameSample.class);
        } catch (Exception e) {
            printError(SetTokenNameSample.class, e);
        } finally {
            module.finalizeModule();
            printSampleDelimiter();
        }
    }

    public static void main(String[] args) {
        runSample(RtPkcs11Module.getInstance(args));
    }
}
