package ru.rutoken.samples.createobjects;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.samples.utils.GostDemoCA;
import ru.rutoken.samples.utils.RtPkcs11Module;

import static ru.rutoken.samples.createobjects.Utils.generateRsaKeyPair;
import static ru.rutoken.samples.createobjects.Utils.makeCertificateTemplate;
import static ru.rutoken.samples.utils.Constants.*;
import static ru.rutoken.samples.utils.Pkcs11Operations.initializePkcs11AndGetFirstToken;
import static ru.rutoken.samples.utils.Utils.*;

/**
 * This sample generates RSA key pair and imports corresponding certificate.
 */
public class CreateRsaKeyPairAndCertificateSample {
    /**
     * Value of CKA_MODULUS_BITS attribute.
     * For example, to generate RSA-4096 set this value to 4096.
     */
    private static final int MODULUS_BITS_LENGTH = 2048;
    /**
     * Value of CKA_PUBLIC_EXPONENT attribute.
     */
    private static final byte[] PUBLIC_EXPONENT = {0x01, 0x00, 0x01};

    public static void runSample(RtPkcs11Module module) {
        try (var session = initializePkcs11AndGetFirstToken(module).openSession(true)) {
            try (var ignore = session.login(Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)) {
                println("Generating RSA key pair");
                final var keyPair = generateRsaKeyPair(session, MODULUS_BITS_LENGTH, PUBLIC_EXPONENT,
                        RSA_PUBLIC_KEY_LABEL, RSA_PRIVATE_KEY_LABEL, RSA_KEY_PAIR_ID);

                println("Creating certificate signing request (CSR)");
                final var csr =
                        session.createCsr(keyPair.getPublicKey(), DN, keyPair.getPrivateKey(), null, RSA_EXTENSIONS);
                printCsr(csr);

                /*
                 * We issue certificate by CSR with demo Certification Authority; uncomment lines below if you want
                 * to use your CA.
                 */
                final var encodedCertificate = GostDemoCA.issueCertificate(csr);
//                println("Copy CSR to Certification Authority that will sign it and return signed certificate to you");
//                println("For example, it can be https://www.cryptopro.ru/certsrv/certrqxt.asp");
//                println("Enter certificate in base64 format:");
//                final var encodedCertificate = Base64.getDecoder().decode(readCertificate());

                println("Importing certificate");

                /*
                 * By the convention key pair ID should be the same for private and public keys and corresponding
                 * certificate. Lots of software rely on this, so we strongly recommend to follow this convention.
                 */
                session.getObjectManager().createObject(
                        makeCertificateTemplate(module.getAttributeFactory(), RSA_KEY_PAIR_ID, encodedCertificate)
                );

                printSuccessfulExit(CreateRsaKeyPairAndCertificateSample.class);
            }
        } catch (Exception e) {
            printError(CreateRsaKeyPairAndCertificateSample.class, e);
        } finally {
            module.finalizeModule();
        }
    }

    public static void main(String[] args) {
        runSample(RtPkcs11Module.getInstance(args));
    }
}
