package ru.rutoken.pkcs11wrapper.mechanism;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanism;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.Pkcs11MechanismParams;

/**
 * High-level {@link CkMechanism} representation.
 */
public interface Pkcs11Mechanism {
    static Pkcs11Mechanism make(IPkcs11MechanismType type) {
        return make(type, null);
    }

    static Pkcs11Mechanism make(IPkcs11MechanismType type, @Nullable Pkcs11MechanismParams parameter) {
        return new Pkcs11MechanismImpl(type, parameter);
    }

    IPkcs11MechanismType getMechanismType();

    @Nullable
    Pkcs11MechanismParams getParameter();

    CkMechanism toCkMechanism(IPkcs11LowLevelFactory factory);
}
