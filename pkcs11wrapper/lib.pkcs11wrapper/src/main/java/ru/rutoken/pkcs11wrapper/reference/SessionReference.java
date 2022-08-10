package ru.rutoken.pkcs11wrapper.reference;

import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Token;

public interface SessionReference extends TokenReference {
    @Override
    default Pkcs11Token getToken() {
        return getSession().getToken();
    }

    Pkcs11Session getSession();
}
