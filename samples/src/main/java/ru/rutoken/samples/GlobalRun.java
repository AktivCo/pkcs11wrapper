package ru.rutoken.samples;

import ru.rutoken.samples.createKeyPairCertificate.CreateGostKeyPairAndCertificateSample;
import ru.rutoken.samples.createKeyPairCertificate.CreateRsaKeyPairAndCertificateSample;
import ru.rutoken.samples.findObjects.FindObjectsSample;
import ru.rutoken.samples.utils.RtPkcs11Module;

public class GlobalRun {
    public static void main(String[] args) {
        final var pkcs11Module = RtPkcs11Module.getInstance(args);

        CreateGostKeyPairAndCertificateSample.runSample(pkcs11Module);
        CreateRsaKeyPairAndCertificateSample.runSample(pkcs11Module);
        FindObjectsSample.runSample(pkcs11Module);
    }
}
