package ru.rutoken.samples.createobjects;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PublicKeyObject;
import ru.rutoken.samples.utils.RtPkcs11Module;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_EC_KEY_PAIR_GEN;
import static ru.rutoken.samples.createobjects.Utils.makeEcdsaPrivateKeyTemplate;
import static ru.rutoken.samples.createobjects.Utils.makeEcdsaPublicKeyTemplate;
import static ru.rutoken.samples.utils.Constants.*;
import static ru.rutoken.samples.utils.Pkcs11Operations.initializePkcs11AndGetFirstToken;
import static ru.rutoken.samples.utils.Utils.*;

/**
 * This sample generates ECDSA key pair.
 */
public class CreateEcdsaKeyPairSample {
    /**
     * Elliptic curve domain parameters. In this sample we use the secp256k1 parameters OID.
     */
    private static final byte[] EC_PARAMS = {0x06, 0x05, 0x2B, (byte) 0x81, 0x04, 0x00, 0x0A};

    public static void runSample(RtPkcs11Module module) {
        try (var session = initializePkcs11AndGetFirstToken(module).openSession(true)) {
            if (hasUnsupportedMechanisms(CreateEcdsaKeyPairSample.class, session.getToken(), CKM_EC_KEY_PAIR_GEN))
                return;

            try (var ignore = session.login(Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)) {
                println("Generating ECDSA key pair");
                session.getKeyManager().generateKeyPair(
                        Pkcs11PublicKeyObject.class,
                        Pkcs11PrivateKeyObject.class,
                        Pkcs11Mechanism.make(CKM_EC_KEY_PAIR_GEN),
                        makeEcdsaPublicKeyTemplate(session.getAttributeFactory(), EC_PARAMS, ECDSA_PUBLIC_KEY_LABEL,
                                ECDSA_KEY_PAIR_ID),
                        makeEcdsaPrivateKeyTemplate(session.getAttributeFactory(), ECDSA_PRIVATE_KEY_LABEL,
                                ECDSA_KEY_PAIR_ID)
                );

                printSuccessfulExit(CreateEcdsaKeyPairSample.class);
            }
        } catch (Exception e) {
            printError(CreateEcdsaKeyPairSample.class, e);
        } finally {
            module.finalizeModule();
            printSampleDelimiter();
        }
    }

    public static void main(String[] args) {
        runSample(RtPkcs11Module.getInstance(args));
    }
}
