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
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_GOSTR3410;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.*;
import static ru.rutoken.samples.signverify.Utils.findCertificateById;
import static ru.rutoken.samples.signverify.Utils.findKeyPairByCertificateValue;
import static ru.rutoken.samples.utils.Constants.*;
import static ru.rutoken.samples.utils.Pkcs11Operations.initializePkcs11AndGetFirstToken;
import static ru.rutoken.samples.utils.PkiUtils.certificateToPem;
import static ru.rutoken.samples.utils.Utils.*;

/**
 * This sample creates raw GOST signature and verifies it.
 * Expects corresponding GOST key pair and certificate on token, you should run
 * {@link CreateGostKeyPairAndCertificateSample} to create them.
 */
public class SignVerifyGostSample {
    private static final byte[] DATA_TO_SIGN = {0x01, 0x02, 0x03};
    /**
     * We will find certificate by its ID. Change this field to your certificate ID.
     * Also change GOST key algorithm by setting field {@link #GOST_KEY_PAIR_PARAMS}
     */
    private static final byte[] CERTIFICATE_ID = GostKeyPairParams.GOST_2012_256.getId();
    private static final GostKeyPairParams GOST_KEY_PAIR_PARAMS = GostKeyPairParams.GOST_2012_256;
    /**
     * Change this flag to false if you want to digest data by yourself.
     */
    private static final boolean SIGN_WITH_DIGEST = true;
    /**
     * Change this flag to true if you want to digest data on the CPU and not on the token.
     * This flag is available if {@link #SIGN_WITH_DIGEST} == false.
     */
    private static final boolean USE_PROGRAM_HASH = false;

    public static void runSample(RtPkcs11Module module) {
        try (var session = initializePkcs11AndGetFirstToken(module).openSession(true)) {
            Pkcs11Mechanism signMechanism = null;
            Pkcs11Mechanism digestMechanism = null;
            switch (GOST_KEY_PAIR_PARAMS) {
                case GOST_2012_256:
                    if (SIGN_WITH_DIGEST) {
                        signMechanism = Pkcs11Mechanism.make(CKM_GOSTR3410_WITH_GOSTR3411_12_256,
                                new Pkcs11ByteArrayMechanismParams(GOST_KEY_PAIR_PARAMS.getParamset3411()));
                    } else {
                        signMechanism = Pkcs11Mechanism.make(CKM_GOSTR3410);
                        digestMechanism = USE_PROGRAM_HASH ? Pkcs11Mechanism.make(CKM_GOSTR3411_12_256,
                                new Pkcs11ByteArrayMechanismParams(GOSTR3411_2012_256_OID)) :
                                Pkcs11Mechanism.make(CKM_GOSTR3411_12_256);
                    }
                    break;
                case GOST_2012_512:
                    if (SIGN_WITH_DIGEST) {
                        signMechanism = Pkcs11Mechanism.make(CKM_GOSTR3410_WITH_GOSTR3411_12_512,
                                new Pkcs11ByteArrayMechanismParams(GOST_KEY_PAIR_PARAMS.getParamset3411()));
                    } else {
                        signMechanism = Pkcs11Mechanism.make(CKM_GOSTR3410_512);
                        digestMechanism = USE_PROGRAM_HASH ? Pkcs11Mechanism.make(CKM_GOSTR3411_2012_512,
                                new Pkcs11ByteArrayMechanismParams(GOSTR3411_2012_512_OID)) :
                                Pkcs11Mechanism.make(CKM_GOSTR3411_2012_512);
                    }
            }

            if (digestMechanism == null && hasUnsupportedMechanisms(SignVerifyGostSample.class, session.getToken(),
                    signMechanism.getMechanismType()))
                return;

            if (digestMechanism != null && hasUnsupportedMechanisms(SignVerifyGostSample.class, session.getToken(),
                    signMechanism.getMechanismType(), digestMechanism.getMechanismType()))
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
                printHex("Data to sign:", DATA_TO_SIGN);

                var dataToSign = SIGN_WITH_DIGEST ? DATA_TO_SIGN :
                        session.getDigestManager().digestAtOnce(DATA_TO_SIGN, digestMechanism);

                final var signature =
                        session.getSignManager().signAtOnce(dataToSign, signMechanism, keyPair.getPrivateKey());
                printHex("Signed data:", signature);

                println("Verifying GOST signature");
                final var result = session.getVerifyManager().verifyAtOnce(dataToSign, signature, signMechanism,
                        keyPair.getPublicKey());

                if (result)
                    println("GOST signature is valid");
                else
                    throw new IllegalStateException("GOST signature is invalid");

                printSuccessfulExit(SignVerifyGostSample.class);
            }
        } catch (Exception e) {
            printError(SignVerifyGostSample.class, e);
        } finally {
            module.finalizeModule();
            printSampleDelimiter();
        }
    }

    public static void main(String[] args) {
        runSample(RtPkcs11Module.getInstance(args));
    }
}
