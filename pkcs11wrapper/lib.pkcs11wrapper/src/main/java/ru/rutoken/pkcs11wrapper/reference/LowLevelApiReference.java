package ru.rutoken.pkcs11wrapper.reference;

import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;

public interface LowLevelApiReference extends LowLevelFactoryReference {
    @Override
    default IPkcs11LowLevelFactory getLowLevelFactory() {
        return getLowLevelApi().getLowLevelFactory();
    }

    IPkcs11LowLevelApi getLowLevelApi();
}
