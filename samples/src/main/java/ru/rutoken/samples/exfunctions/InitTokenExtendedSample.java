package ru.rutoken.samples.exfunctions;

import ru.rutoken.pkcs11wrapper.rutoken.datatype.RutokenInitParam;
import ru.rutoken.samples.utils.RtPkcs11Module;

import static ru.rutoken.pkcs11wrapper.rutoken.datatype.RutokenInitParam.ChangeUserPinPolicy.TOKEN_FLAGS_ADMIN_AND_USER_CHANGE_USER_PIN;
import static ru.rutoken.samples.utils.Constants.*;
import static ru.rutoken.samples.utils.Pkcs11Operations.initializePkcs11AndGetFirstToken;
import static ru.rutoken.samples.utils.Utils.*;
import static ru.rutoken.samples.utils.Utils.printError;

public class InitTokenExtendedSample {

    public static void runSample(RtPkcs11Module module) {
        try {
            println("Running extended initialization of token");
            final var initParam = new RutokenInitParam(false,
                    DEFAULT_ADMIN_PIN,
                    DEFAULT_USER_PIN,
                    TOKEN_FLAGS_ADMIN_AND_USER_CHANGE_USER_PIN,
                    6,
                    6,
                    10,
                    10,
                    TOKEN_LABEL,
                    null);
            initializePkcs11AndGetFirstToken(module).initToken(DEFAULT_ADMIN_PIN, initParam);
            println("Token initialized");
            printSuccessfulExit(InitTokenExtendedSample.class);
        } catch (Exception e) {
            printError(InitTokenExtendedSample.class, e);
        } finally {
            module.finalizeModule();
            printSampleDelimiter();
        }
    }

    public static void main(String[] args) {
        runSample(RtPkcs11Module.getInstance(args));
    }
}
