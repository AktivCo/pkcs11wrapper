/*
 * Copyright (c) 2023, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.samples.derive;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.CkEcdh1DeriveParams;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.Pkcs11ByteArrayMechanismParams;
import ru.rutoken.samples.createobjects.CreateGostKeyPairAndCertificateSample;
import ru.rutoken.samples.createobjects.GostKeyPairParams;
import ru.rutoken.samples.utils.RtPkcs11Module;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_VALUE;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyDerivationFunction.CKD_NULL;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.*;
import static ru.rutoken.samples.createobjects.GostKeyPairParams.GOST_2012_256;
import static ru.rutoken.samples.createobjects.GostKeyPairParams.GOST_2012_512;
import static ru.rutoken.samples.derive.Utils.makeMagmaSecretKeyTemplate;
import static ru.rutoken.samples.derive.Utils.makeMagmaTwinSecretKeyTemplate;
import static ru.rutoken.samples.utils.Constants.DEFAULT_USER_PIN;
import static ru.rutoken.samples.utils.Pkcs11Operations.*;
import static ru.rutoken.samples.utils.Utils.*;

/**
 * This sample derives a twin symmetric session key which should be used to wrap other session keys.
 * Expects corresponding GOST key pair and certificate on token, you should run
 * {@link CreateGostKeyPairAndCertificateSample} to create them.
 */
public class DeriveGostKegSample {
    /**
     * You can change GOST key algorithm here.
     */
    private final static GostKeyPairParams GOST_KEY_PAIR_PARAMS = GOST_2012_512;
    @SuppressWarnings("ConstantValue")
    private final static int UKM_LENGTH = GOST_KEY_PAIR_PARAMS == GOST_2012_256 ? 24 : 16;

    public static void runSample(RtPkcs11Module module) {
        printSampleLaunchMessage(DeriveGostKegSample.class);
        try (var session = initializePkcs11AndGetFirstToken(module).openSession(true)) {
            if (hasUnsupportedMechanisms(DeriveGostKegSample.class, session.getToken(),
                    CKM_GOST_KEG, CKM_MAGMA_KEY_GEN, CKM_MAGMA_KEXP_15_WRAP))
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

                final var deriveUkm = session.getRandomNumberManager().generateRandom(UKM_LENGTH);
                final var deriveMechanism = Pkcs11Mechanism.make(CKM_GOST_KEG,
                        new CkEcdh1DeriveParams(CKD_NULL.getAsLong(), recipientPublicKey, deriveUkm));

                println("Deriving twin key");
                final var magmaTwinKey = session.getKeyManager().deriveKey(deriveMechanism, senderPrivateKey,
                        makeMagmaTwinSecretKeyTemplate(session.getAttributeFactory()));

                // The derived key should be used for wrapping/unwrapping other session keys
                println("Generating secret key");
                final var magmaSecretKey = session.getKeyManager().generateKey(Pkcs11Mechanism.make(CKM_MAGMA_KEY_GEN),
                        makeMagmaSecretKeyTemplate(session.getAttributeFactory()));

                final var wrapUkm = session.getRandomNumberManager().generateRandom(4);
                final var wrapMechanism = Pkcs11Mechanism.make(CKM_MAGMA_KEXP_15_WRAP,
                        new Pkcs11ByteArrayMechanismParams(wrapUkm));

                println("Wrapping secret key with derived twin key");
                final var wrappedKey = session.getKeyManager().wrapKey(wrapMechanism, magmaTwinKey, magmaSecretKey);
                printHex("Wrapped key value:", wrappedKey);

                session.getObjectManager().destroyObject(magmaTwinKey);
                session.getObjectManager().destroyObject(magmaSecretKey);

                printSuccessfulExit(DeriveGostKegSample.class);
            }
        } catch (Exception e) {
            printError(DeriveGostKegSample.class, e);
        } finally {
            module.finalizeModule();
            printSampleDelimiter();
        }
    }

    public static void main(String[] args) {
        runSample(RtPkcs11Module.getInstance(args));
    }
}
