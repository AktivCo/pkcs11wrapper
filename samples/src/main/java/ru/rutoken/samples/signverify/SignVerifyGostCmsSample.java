/*
 * Copyright (c) 2022, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.samples.signverify;

import ru.rutoken.pkcs11jna.RtPkcs11Constants;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.pkcs11wrapper.rutoken.datatype.VendorX509Store;
import ru.rutoken.pkcs11wrapper.rutoken.manager.RtPkcs11CmsManager.CrlCheckMode;
import ru.rutoken.samples.createobjects.CreateGostKeyPairAndCertificateSample;
import ru.rutoken.samples.createobjects.GostKeyPairParams;
import ru.rutoken.samples.utils.GostDemoCA;
import ru.rutoken.samples.utils.RtPkcs11Module;

import java.util.ArrayList;

import static ru.rutoken.pkcs11jna.RtPkcs11Constants.*;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_VALUE;
import static ru.rutoken.pkcs11wrapper.rutoken.manager.RtPkcs11CmsManager.CrlCheckMode.OPTIONAL_CRL_CHECK;
import static ru.rutoken.samples.utils.Constants.DEFAULT_USER_PIN;
import static ru.rutoken.samples.utils.Pkcs11Operations.*;
import static ru.rutoken.samples.utils.PkiUtils.certificateToPem;
import static ru.rutoken.samples.utils.PkiUtils.cmsToPem;
import static ru.rutoken.samples.utils.Utils.*;

/**
 * This sample creates attached/detached CMS signature and verifies it.
 * Expects corresponding GOST key pair and certificate on token, you should run
 * {@link CreateGostKeyPairAndCertificateSample} to create them.
 */
public class SignVerifyGostCmsSample {
    /**
     * Change this flag to false if you want to create detached CMS signature.
     */
    private static final boolean IS_ATTACHED = true;
    /**
     * Change this flag to false if you do not want to use hardware hash in signing operation.
     */
    private static final boolean USE_HARDWARE_HASH = true;
    /**
     * Change certificate revocation list check policy here.
     */
    private static final CrlCheckMode CRL_CHECK_MODE = OPTIONAL_CRL_CHECK;
    /**
     * Change this flag to false if you want to verify not only CMS signature, but also signer's certificate chain.
     */
    private static final boolean VERIFY_SIGNATURE_ONLY = true;
    /**
     * Set these flags if {@link #VERIFY_SIGNATURE_ONLY} == false.
     * Can be any combination of {@link RtPkcs11Constants#CKF_VENDOR_DO_NOT_USE_INTERNAL_CMS_CERTS},
     * {@link RtPkcs11Constants#CKF_VENDOR_ALLOW_PARTIAL_CHAINS} and
     * {@link RtPkcs11Constants#CKF_VENDOR_USE_TRUSTED_CERTS_FROM_TOKEN} flags.
     */
    private static final long VERIFY_FLAGS = CKF_VENDOR_ALLOW_PARTIAL_CHAINS;
    private static final byte[] DATA_TO_SIGN = {0x01, 0x02, 0x03};
    /**
     * We will find certificate by its ID. Change this field to your certificate ID.
     */
    private static final byte[] CERTIFICATE_ID = GostKeyPairParams.GOST_2012_512.getId();

    public static void runSample(RtPkcs11Module module) {
        printSampleLaunchMessage(SignVerifyGostCmsSample.class);
        try (var session = initializePkcs11AndGetFirstToken(module).openSession(true)) {
            try (var ignore = session.login(Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)) {
                // Side A signs data into CMS with attached/detached content using key pair on Rutoken device.
                // You can get certificate from some database, we'll get it from the token for simplicity.
                println("Finding signer certificate");
                final var signerCertificate = findCertificateById(session, CERTIFICATE_ID);
                final var signerCertificateValue =
                        signerCertificate.getByteArrayAttributeValue(session, CKA_VALUE).getByteArrayValue();
                printString("Certificate value in PEM:", certificateToPem(signerCertificateValue));

                final var signerPrivateKey = findPrivateKeyByCertificateValue(session, signerCertificateValue);
                printHex("Data to sign:", DATA_TO_SIGN);

                final var cmsSignature = session.getCmsManager().sign(DATA_TO_SIGN, signerCertificate, signerPrivateKey,
                        null, (USE_HARDWARE_HASH ? RtPkcs11Constants.USE_HARDWARE_HASH : 0L) |
                                (IS_ATTACHED ? 0L : PKCS7_DETACHED_SIGNATURE));
                printString("CMS signature in PEM:", cmsToPem(cmsSignature));

                // Side B trusts Certificate Authority that signed Side A's certificate;
                // Side B verifies CMS that has been signed by Side A.
                println("Verifying CMS signature");
                // You should fill trustedCertificates with your business system's trusted certificates.
                final var trustedCertificates = new ArrayList<byte[]>();
                // For simplicity, we trust the Certification Authority that signed signer's certificate.
                trustedCertificates.add(GostDemoCA.getRootCertificate());

                // Certificates for signature verification. You must put your intermediate certificates here.
                final var certificates = new ArrayList<byte[]>();
                // For simplicity, we assume that signer certificate is signed directly by trusted CA,
                // therefore we don't need any intermediate certificates.
                certificates.add(signerCertificateValue);

                final var store = VERIFY_SIGNATURE_ONLY ? null :
                        new VendorX509Store(trustedCertificates, certificates, null);
                final var verifyFlags = VERIFY_SIGNATURE_ONLY ? CKF_VENDOR_CHECK_SIGNATURE_ONLY : VERIFY_FLAGS;

                if (IS_ATTACHED) {
                    session.getCmsManager().requireVerifyAttachedAtOnce(cmsSignature, store, CRL_CHECK_MODE,
                            verifyFlags);
                } else {
                    session.getCmsManager().requireVerifyDetachedAtOnce(cmsSignature, DATA_TO_SIGN, store,
                            CRL_CHECK_MODE, verifyFlags);
                }

                println("CMS signature is valid");
                printSuccessfulExit(SignVerifyGostCmsSample.class);
            }
        } catch (Exception e) {
            printError(SignVerifyGostCmsSample.class, e);
        } finally {
            module.finalizeModule();
            printSampleDelimiter();
        }
    }

    public static void main(String[] args) {
        runSample(RtPkcs11Module.getInstance(args));
    }
}
