package ru.rutoken.pkcs11wrapper.main;

import ru.rutoken.pkcs11wrapper.datatype.Pkcs11SlotInfo;
import ru.rutoken.pkcs11wrapper.reference.ModuleReference;

public interface Pkcs11Slot extends ModuleReference {
    long getId();

    Pkcs11SlotInfo getSlotInfo();

    /**
     * It is safe to call this method at any time. It does not matter if a token is really present in slot or not.
     */
    Pkcs11Token getToken();
}
