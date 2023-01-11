package ru.rutoken.samples;

import ru.rutoken.samples.signverify.SignVerifyGostCmsSample;
import ru.rutoken.samples.createobjects.CreateEcdsaKeyPairSample;
import ru.rutoken.samples.signverify.*;
import ru.rutoken.samples.createobjects.CreateGostKeyPairAndCertificateSample;
import ru.rutoken.samples.createobjects.CreateRsaKeyPairAndCertificateSample;
import ru.rutoken.samples.findobjects.FindObjectsSample;
import ru.rutoken.samples.general.InitTokenSample;
import ru.rutoken.samples.exfunctions.GetTokenInfoExtendedSample;
import ru.rutoken.samples.exfunctions.InitTokenExtendedSample;
import ru.rutoken.samples.exfunctions.SetTokenNameSample;
import ru.rutoken.samples.exfunctions.UnblockUserPinSample;
import ru.rutoken.samples.utils.RtPkcs11Module;

public class GlobalRun {
    public static void main(String[] args) {
        final var pkcs11Module = RtPkcs11Module.getInstance(args);

        InitTokenSample.runSample(pkcs11Module);

        CreateGostKeyPairAndCertificateSample.runSample(pkcs11Module);
        CreateRsaKeyPairAndCertificateSample.runSample(pkcs11Module);
        CreateEcdsaKeyPairSample.runSample(pkcs11Module);

        FindObjectsSample.runSample(pkcs11Module);

        SignVerifyGostCmsSample.runSample(pkcs11Module);
        SignVerifyGostSample.runSample(pkcs11Module);
        SignVerifyUpdateGostSample.runSample(pkcs11Module);
        SignVerifyRsaSample.runSample(pkcs11Module);
        SignVerifyEcdsaSample.runSample(pkcs11Module);

        GetTokenInfoExtendedSample.runSample(pkcs11Module);
        SetTokenNameSample.runSample(pkcs11Module);
        UnblockUserPinSample.runSample(pkcs11Module);
        InitTokenExtendedSample.runSample(pkcs11Module);
    }
}
