package ru.rutoken.samples;

import ru.rutoken.samples.findObjects.FindObjectsSample;
import ru.rutoken.samples.utils.RtPkcs11Module;

public class GlobalRun {
    public static void main(String[] args) {
        final var pkcs11Module = RtPkcs11Module.getInstance(args);

        FindObjectsSample.runSample(pkcs11Module);
    }
}
