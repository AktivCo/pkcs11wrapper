package ru.rutoken.samples.findobjects;

import org.bouncycastle.cert.X509CertificateHolder;

import ru.rutoken.pkcs11wrapper.object.certificate.Pkcs11X509PublicKeyCertificateObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PublicKeyObject;
import ru.rutoken.samples.findobjects.Container.CertificateAndKeyPairContainer;
import ru.rutoken.samples.findobjects.Container.CertificateContainer;
import ru.rutoken.samples.findobjects.Container.KeyPairContainer;
import ru.rutoken.samples.utils.RtPkcs11Module;

import java.util.ArrayList;
import java.util.List;

import static ru.rutoken.samples.findobjects.Utils.*;
import static ru.rutoken.samples.utils.Pkcs11Operations.findPublicKeyByCertificate;
import static ru.rutoken.samples.utils.Pkcs11Operations.initializePkcs11AndGetFirstToken;
import static ru.rutoken.samples.utils.Utils.*;

/**
 * This sample finds objects on the token and prints information about them.
 * For simplicity, we assume that if a public key is found, then there is a key pair on the token.
 */
public class FindObjectsSample {
    public static void runSample(RtPkcs11Module module) {
        try (var session = initializePkcs11AndGetFirstToken(module).openSession(true)) {
            final var containers = new ArrayList<Container>();
            final var certificates =
                    session.getObjectManager().findObjectsAtOnce(Pkcs11X509PublicKeyCertificateObject.class);
            final var publicKeyIds = new ArrayList<byte[]>();

            for (final var certificate : certificates) {
                final var certificateId = certificate.getIdAttributeValue(session).getByteArrayValue();
                final var x509CertificateHolder =
                        new X509CertificateHolder(certificate.getValueAttributeValue(session).getByteArrayValue());
                final var publicKey = findPublicKeyByCertificate(session, x509CertificateHolder);

                if (publicKey != null) {
                    final var publicKeyId = publicKey.getIdAttributeValue(session).getByteArrayValue();
                    publicKeyIds.add(publicKeyId);
                    containers.add(new CertificateAndKeyPairContainer(
                            certificateId,
                            publicKeyId,
                            x509CertificateHolder,
                            getAlgorithm(publicKey, session)
                    ));
                } else {
                    containers.add(new CertificateContainer(certificateId, x509CertificateHolder));
                }
            }

            final var publicKeys = session.getObjectManager().findObjectsAtOnce(Pkcs11PublicKeyObject.class);

            for (final var key : publicKeys) {
                final var keyId = key.getIdAttributeValue(session).getByteArrayValue();

                if (!contains(publicKeyIds, keyId))
                    containers.add(new KeyPairContainer(keyId, getAlgorithm(key, session)));
            }

            printContainerInfo(containers);
            printSuccessfulExit(FindObjectsSample.class);
        } catch (Exception e) {
            printError(FindObjectsSample.class, e);
        } finally {
            module.finalizeModule();
        }
    }

    public static void main(String[] args) {
        runSample(RtPkcs11Module.getInstance(args));
    }

    private static void printContainerInfo(List<Container> containers) {
        println("Containers:");
        println("-------------------------------------");
        for (var container : containers) {
            container.printInfo();
            println("-------------------------------------");
        }
    }
}
