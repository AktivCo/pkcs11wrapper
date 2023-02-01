/*
 * Copyright (c) 2023, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.samples.derive;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.CkGostR3410DeriveParams;
import ru.rutoken.samples.createobjects.CreateGostKeyPairAndCertificateSample;
import ru.rutoken.samples.createobjects.GostKeyPairParams;
import ru.rutoken.samples.utils.RtPkcs11Module;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_VALUE;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyDerivationFunction.CKD_KDF_GOSTR3411_2012_256;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.CKM_GOSTR3410_12_DERIVE;
import static ru.rutoken.samples.createobjects.GostKeyPairParams.GOST_2012_256;
import static ru.rutoken.samples.createobjects.GostKeyPairParams.GOST_2012_512;
import static ru.rutoken.samples.derive.Utils.makeGost28147SecretKeyTemplate;
import static ru.rutoken.samples.utils.Constants.DEFAULT_USER_PIN;
import static ru.rutoken.samples.utils.Pkcs11Operations.*;
import static ru.rutoken.samples.utils.Utils.*;

/**
 * This sample derives GOST28147 secretKey, which can be used directly in encrypt/decrypt scenarios.
 * Expects corresponding GOST key pair and certificate on token, you should run
 * {@link CreateGostKeyPairAndCertificateSample} to create them.
 */
public class DeriveGost2012Sample {
    /**
     * You can change GOST key algorithm here.
     */
    private final static GostKeyPairParams GOST_KEY_PAIR_PARAMS = GOST_2012_512;

    public static void runSample(RtPkcs11Module module) {
        printSampleLaunchMessage(DeriveGost2012Sample.class);
        try (var session = initializePkcs11AndGetFirstToken(module).openSession(true)) {
            if (hasUnsupportedMechanisms(DeriveGost2012Sample.class, session.getToken(), CKM_GOSTR3410_12_DERIVE))
                return;

            try (var ignore = session.login(Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)) {
                // We find private key by certificate, but the certificate itself is not needed in this sample
                final var senderCertificateValue =
                        findCertificateById(session, GOST_KEY_PAIR_PARAMS.getId())
                                .getByteArrayAttributeValue(session, CKA_VALUE).getByteArrayValue();
                final var senderPrivateKey = findPrivateKeyByCertificateValue(session, senderCertificateValue);

                // We have a pre-generated local public key
                final var recipientPublicKey = GOST_KEY_PAIR_PARAMS == GOST_2012_256
                        ? RecipientPublicKey.GOST_2012_256 : RecipientPublicKey.GOST_2012_512;

                final var deriveUkm = session.getRandomNumberManager().generateRandom(8);
                final var deriveMechanismParameters = new CkGostR3410DeriveParams(
                        CKD_KDF_GOSTR3411_2012_256.getAsLong(), recipientPublicKey, deriveUkm);
                final var deriveMechanism = Pkcs11Mechanism.make(CKM_GOSTR3410_12_DERIVE, deriveMechanismParameters);

                println("Deriving GOST28147 secret key");
                final var secretKey = session.getKeyManager().deriveKey(deriveMechanism, senderPrivateKey,
                        makeGost28147SecretKeyTemplate(session.getAttributeFactory()));

                final var secretKeyValue = secretKey.getByteArrayAttributeValue(session, CKA_VALUE).getByteArrayValue();
                printHex("GOST28147 secret key value:", secretKeyValue);

                session.getObjectManager().destroyObject(secretKey);

                printSuccessfulExit(DeriveGost2012Sample.class);
            }
        } catch (Exception e) {
            printError(DeriveGost2012Sample.class, e);
        } finally {
            module.finalizeModule();
            printSampleDelimiter();
        }
    }

    public static void main(String[] args) {
        runSample(RtPkcs11Module.getInstance(args));
    }
}
