package ru.rutoken.samples.utils;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11InitializeArgs;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Token;

import java.util.ArrayList;
import java.util.List;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_RSA_PKCS_KEY_PAIR_GEN;

public final class Pkcs11Operations {
    private Pkcs11Operations() {
    }

    public static RtPkcs11Token initializePkcs11AndGetFirstToken(RtPkcs11Module module) {
        module.initializeModule(new Pkcs11InitializeArgs.Builder().setOsLockingOk(true).build());

        final var slots = module.getSlotList(true);
        if (slots.size() == 0)
            throw new IllegalStateException("Rutoken is not found");

        return (RtPkcs11Token) slots.get(0).getToken();
    }

    /**
     * This method checks if there are some mechanisms in {@param mechanisms} that are not supported by {@param token}
     * and returns all such mechanisms.
     */
    public static List<IPkcs11MechanismType> getUnsupportedMechanisms(RtPkcs11Token token,
                                                                      IPkcs11MechanismType... mechanisms) {
        final var result = new ArrayList<IPkcs11MechanismType>();
        for (var mechanism : mechanisms) {
            if (!isMechanismSupported(token, mechanism))
                result.add(mechanism);
        }
        return result;
    }

    public static boolean isRsaModulusSupported(RtPkcs11Token token, int modulusBits) {
        final var info = token.getMechanismInfo(CKM_RSA_PKCS_KEY_PAIR_GEN);
        return (modulusBits >= info.getMinKeySize()) && (modulusBits <= info.getMaxKeySize());
    }

    private static boolean isMechanismSupported(RtPkcs11Token token, IPkcs11MechanismType mechanism) {
        final var mechanismList = token.getMechanismList();
        return mechanismList.stream().anyMatch(it -> it.getAsLong() == mechanism.getAsLong());
    }
}
