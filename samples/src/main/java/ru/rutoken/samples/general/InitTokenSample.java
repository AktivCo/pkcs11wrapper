package ru.rutoken.samples.general;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.samples.utils.RtPkcs11Module;

import static ru.rutoken.samples.utils.Constants.*;
import static ru.rutoken.samples.utils.Pkcs11Operations.initializePkcs11AndGetFirstToken;
import static ru.rutoken.samples.utils.Utils.*;

public class InitTokenSample {
    public static void runSample(RtPkcs11Module module) {
        try {
            final var token = initializePkcs11AndGetFirstToken(module);
            println("Token initialization");
            token.initToken(DEFAULT_ADMIN_PIN, TOKEN_LABEL);

            // According to the PKCS#11 standard, "access by the normal user is disabled until the SO sets the
            // normal user’s PIN", so we have to init user PIN after token initialization.
            println("Opening session");
            try (var session = token.openSession(true)) {
                println("Logging in as administrator");
                try (var ignore = session.login(Pkcs11UserType.CKU_SO, DEFAULT_ADMIN_PIN)) {
                    println("User PIN initialization");
                    session.initPin(DEFAULT_USER_PIN);
                }

                printSuccessfulExit(InitTokenSample.class);
            }
        } catch (Exception e) {
            printError(InitTokenSample.class, e);
        } finally {
            module.finalizeModule();
        }
    }

    public static void main(String[] args) {
        runSample(RtPkcs11Module.getInstance(args));
    }
}
