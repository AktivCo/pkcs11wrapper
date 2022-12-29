package ru.rutoken.samples;

import ru.rutoken.samples.signverify.SignVerifyGostCmsSample;
import ru.rutoken.samples.createobjects.CreateGostKeyPairAndCertificateSample;
import ru.rutoken.samples.createobjects.CreateRsaKeyPairAndCertificateSample;
import ru.rutoken.samples.findobjects.FindObjectsSample;
import ru.rutoken.samples.general.InitTokenSample;
import ru.rutoken.samples.utils.RtPkcs11Module;

public class GlobalRun {
    public static void main(String[] args) {
        final var pkcs11Module = RtPkcs11Module.getInstance(args);

        CreateGostKeyPairAndCertificateSample.runSample(pkcs11Module);
        CreateRsaKeyPairAndCertificateSample.runSample(pkcs11Module);

        FindObjectsSample.runSample(pkcs11Module);

        SignVerifyGostCmsSample.runSample(pkcs11Module);

        InitTokenSample.runSample(pkcs11Module);
    }
}
