/*
 * Copyright (c) 2022, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.samples.signverify;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.Pkcs11ByteArrayMechanismParams;
import ru.rutoken.samples.createobjects.CreateGostKeyPairAndCertificateSample;
import ru.rutoken.samples.createobjects.GostKeyPairParams;
import ru.rutoken.samples.utils.RtPkcs11Module;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_VALUE;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.CKM_GOSTR3410_WITH_GOSTR3411_12_256;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.CKM_GOSTR3410_WITH_GOSTR3411_12_512;
import static ru.rutoken.samples.signverify.Utils.findCertificateById;
import static ru.rutoken.samples.signverify.Utils.findKeyPairByCertificateValue;
import static ru.rutoken.samples.utils.Constants.DEFAULT_USER_PIN;
import static ru.rutoken.samples.utils.Pkcs11Operations.initializePkcs11AndGetFirstToken;
import static ru.rutoken.samples.utils.PkiUtils.certificateToPem;
import static ru.rutoken.samples.utils.Utils.*;

/**
 * This sample creates raw GOST signature for two blocks of data and verifies it.
 * Expects corresponding GOST key pair and certificate on token, you should run
 * {@link CreateGostKeyPairAndCertificateSample} to create them.
 */
public class SignVerifyUpdateGostSample {
    private static final byte[] FIRST_DATA_TO_SIGN = {0x01, 0x02, 0x03};
    private static final byte[] SECOND_DATA_TO_SIGN = {0x04, 0x05, 0x06};
    /**
     * We will find certificate by its ID. Change this field to your certificate ID.
     * Also change GOST key algorithm by setting field {@link #GOST_KEY_PAIR_PARAMS}
     */
    private static final byte[] CERTIFICATE_ID = GostKeyPairParams.GOST_2012_256.getId();
    private static final GostKeyPairParams GOST_KEY_PAIR_PARAMS = GostKeyPairParams.GOST_2012_256;

    public static void runSample(RtPkcs11Module module) {
        try (var session = initializePkcs11AndGetFirstToken(module).openSession(true)) {
            Pkcs11Mechanism signMechanism = null;
            switch (GOST_KEY_PAIR_PARAMS) {
                case GOST_2012_256:
                    signMechanism = Pkcs11Mechanism.make(CKM_GOSTR3410_WITH_GOSTR3411_12_256,
                            new Pkcs11ByteArrayMechanismParams(GOST_KEY_PAIR_PARAMS.getParamset3411()));
                    break;
                case GOST_2012_512:
                    signMechanism = Pkcs11Mechanism.make(CKM_GOSTR3410_WITH_GOSTR3411_12_512,
                            new Pkcs11ByteArrayMechanismParams(GOST_KEY_PAIR_PARAMS.getParamset3411()));
            }

            if (hasUnsupportedMechanisms(SignVerifyUpdateGostSample.class, session.getToken(),
                    signMechanism.getMechanismType()))
                return;

            try (var ignore = session.login(Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)) {
                // We find key pair by certificate, but the certificate is not needed for signing/verifying message.
                // You can get certificate from some database, we'll get it from the token for simplicity.
                println("Finding signer certificate");
                final var signerCertificate = findCertificateById(session, CERTIFICATE_ID);
                final var signerCertificateValue =
                        signerCertificate.getByteArrayAttributeValue(session, CKA_VALUE).getByteArrayValue();
                printString("Certificate value in PEM:", certificateToPem(signerCertificateValue));

                final var keyPair = findKeyPairByCertificateValue(session, signerCertificateValue);
                printHex("The first data block to sign:", FIRST_DATA_TO_SIGN);
                printHex("The second data block to sign:", SECOND_DATA_TO_SIGN);

                session.getSignManager().signInit(signMechanism, keyPair.getPrivateKey());
                session.getSignManager().signUpdate(FIRST_DATA_TO_SIGN);
                session.getSignManager().signUpdate(SECOND_DATA_TO_SIGN);
                final var signature = session.getSignManager().signFinal();
                printHex("Signed data:", signature);

                println("Verifying GOST signature");
                session.getVerifyManager().verifyInit(signMechanism, keyPair.getPublicKey());
                session.getVerifyManager().verifyUpdate(FIRST_DATA_TO_SIGN);
                session.getVerifyManager().verifyUpdate(SECOND_DATA_TO_SIGN);
                final var result = session.getVerifyManager().verifyFinal(signature);

                if (result)
                    println("GOST signature is valid");
                else
                    throw new IllegalStateException("GOST signature is invalid");

                printSuccessfulExit(SignVerifyUpdateGostSample.class);
            }
        } catch (Exception e) {
            printError(SignVerifyUpdateGostSample.class, e);
        } finally {
            module.finalizeModule();
            printSampleDelimiter();
        }
    }

    public static void main(String[] args) {
        runSample(RtPkcs11Module.getInstance(args));
    }
}
