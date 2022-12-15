package ru.rutoken.samples.createKeyPairCertificate;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.samples.utils.GostDemoCA;
import ru.rutoken.samples.utils.RtPkcs11Module;

import static ru.rutoken.samples.createKeyPairCertificate.Utils.generateGostKeyPair;
import static ru.rutoken.samples.createKeyPairCertificate.Utils.makeCertificateTemplate;
import static ru.rutoken.samples.utils.Constants.*;
import static ru.rutoken.samples.utils.Pkcs11Operations.initializePkcs11AndGetFirstToken;
import static ru.rutoken.samples.utils.Utils.*;

/**
 * This sample generates GOST R 34.10-2012 key pair and imports corresponding certificate.
 */
public class CreateGostKeyPairAndCertificateSample {
    /**
     * You can change GOST key algorithm here.
     */
    private static final GostKeyPairParams GOST_KEY_PAIR_PARAMS = GostKeyPairParams.GOST_2012_256;

    public static void runSample(RtPkcs11Module module) {
        try (var session = initializePkcs11AndGetFirstToken(module).openSession(true)) {
            try (var ignore = session.login(Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)) {
                println("Generating GOST key pair");
                final var keyPair = generateGostKeyPair(session, GOST_KEY_PAIR_PARAMS);

                println("Creating certificate signing request (CSR)");
                final var csr =
                        session.createCsr(keyPair.getPublicKey(), DN, keyPair.getPrivateKey(), null, GOST_EXTENSIONS);
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
                session.getObjectManager().createObject(makeCertificateTemplate(
                        module.getAttributeFactory(), GOST_KEY_PAIR_PARAMS.getId(), encodedCertificate
                ));

                printSuccessfulExit(CreateGostKeyPairAndCertificateSample.class);
            }
        } catch (Exception e) {
            printError(CreateGostKeyPairAndCertificateSample.class, e);
        } finally {
            module.finalizeModule();
        }
    }

    public static void main(String[] args) {
        runSample(RtPkcs11Module.getInstance(args));
    }
}
