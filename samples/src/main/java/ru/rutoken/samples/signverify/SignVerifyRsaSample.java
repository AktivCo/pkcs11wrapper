/*
 * Copyright (c) 2022, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.samples.signverify;

import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.DigestInfo;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.CkRsaPkcsPssParams;
import ru.rutoken.samples.createobjects.CreateRsaKeyPairAndCertificateSample;
import ru.rutoken.samples.utils.RtPkcs11Module;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_VALUE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MaskGenerationFunction.CKG_MGF1_SHA256;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.*;
import static ru.rutoken.samples.signverify.Utils.findCertificateById;
import static ru.rutoken.samples.signverify.Utils.findKeyPairByCertificateValue;
import static ru.rutoken.samples.utils.Constants.DEFAULT_USER_PIN;
import static ru.rutoken.samples.utils.Constants.RSA_KEY_PAIR_ID;
import static ru.rutoken.samples.utils.Pkcs11Operations.initializePkcs11AndGetFirstToken;
import static ru.rutoken.samples.utils.PkiUtils.certificateToPem;
import static ru.rutoken.samples.utils.Utils.*;

/**
 * This sample creates raw RSA signature and verifies it.
 * Expects corresponding RSA key pair and certificate on token, you should run
 * {@link CreateRsaKeyPairAndCertificateSample} to create them.
 */
public class SignVerifyRsaSample {
    private static final byte[] DATA_TO_SIGN = {0x01, 0x02, 0x03};
    /**
     * We will find certificate by its ID. Change this field to your certificate ID.
     */
    private static final byte[] CERTIFICATE_ID = RSA_KEY_PAIR_ID;
    /**
     * Change this flag to false if you want to digest data by yourself.
     */
    private static final boolean SIGN_WITH_DIGEST = true;
    /**
     * Change this flag to false if you want to use PKCS1 padding instead of PSS padding.
     */
    private static final boolean USE_PSS_PADDING = true;

    public static void runSample(RtPkcs11Module module) {
        try (var session = initializePkcs11AndGetFirstToken(module).openSession(true)) {
            IPkcs11MechanismType signMechanismType;
            IPkcs11MechanismType digestMechanismType = null;
            if (SIGN_WITH_DIGEST) {
                signMechanismType = USE_PSS_PADDING ? CKM_SHA256_RSA_PKCS_PSS : CKM_SHA256_RSA_PKCS;
            } else {
                digestMechanismType = CKM_SHA256;
                signMechanismType = USE_PSS_PADDING ? CKM_RSA_PKCS_PSS : CKM_RSA_PKCS;
            }

            if (digestMechanismType == null && hasUnsupportedMechanisms(SignVerifyRsaSample.class, session.getToken(),
                    signMechanismType))
                return;

            if (digestMechanismType != null && hasUnsupportedMechanisms(SignVerifyGostSample.class,
                    session.getToken(), signMechanismType, digestMechanismType))
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

                Pkcs11Mechanism signMechanism;
                var dataToSign = DATA_TO_SIGN;

                if (SIGN_WITH_DIGEST) {
                    signMechanism = USE_PSS_PADDING ? Pkcs11Mechanism.make(
                            signMechanismType,
                            new CkRsaPkcsPssParams(CKM_SHA256.getAsLong(), CKG_MGF1_SHA256.getAsLong(), 0)
                    ) : Pkcs11Mechanism.make(signMechanismType);
                } else {
                    final var digest = session.getDigestManager().digestAtOnce(DATA_TO_SIGN,
                            Pkcs11Mechanism.make(digestMechanismType));

                    if (USE_PSS_PADDING) {
                        dataToSign = digest;
                        signMechanism = Pkcs11Mechanism.make(signMechanismType,
                                new CkRsaPkcsPssParams(CKM_SHA256.getAsLong(), CKG_MGF1_SHA256.getAsLong(),
                                        digest.length));
                    } else {
                        final var digestInfo = new DigestInfo(
                                new AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE), digest
                        );
                        dataToSign = digestInfo.getEncoded();
                        signMechanism = Pkcs11Mechanism.make(signMechanismType);
                    }
                }

                final var signature =
                        session.getSignManager().signAtOnce(dataToSign, signMechanism, keyPair.getPrivateKey());
                printHex("Signed data:", signature);

                println("Verifying RSA signature");
                final var result = session.getVerifyManager().verifyAtOnce(dataToSign, signature, signMechanism,
                        keyPair.getPublicKey());

                if (result)
                    println("RSA signature is valid");
                else
                    throw new IllegalStateException("RSA signature is invalid");

                printSuccessfulExit(SignVerifyRsaSample.class);
            }
        } catch (Exception e) {
            printError(SignVerifyRsaSample.class, e);
        } finally {
            module.finalizeModule();
            printSampleDelimiter();
        }
    }

    public static void main(String[] args) {
        runSample(RtPkcs11Module.getInstance(args));
    }
}
