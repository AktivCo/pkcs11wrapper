package ru.rutoken.pkcs11wrapper.rutoken.main;

import ru.rutoken.pkcs11wrapper.main.Pkcs11Slot;
import ru.rutoken.pkcs11wrapper.main.Pkcs11TokenImpl;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkTokenInfoExtended;

public class RtPkcs11Token extends Pkcs11TokenImpl {
    RtPkcs11Token(Pkcs11Slot slot) {
        super(slot);
    }

    public CkTokenInfoExtended getTokenInfoExtended() {
        return getApi().C_EX_GetTokenInfoExtended(getSlot().getId());
    }

    @Override
    public RtPkcs11Api getApi() {
        return (RtPkcs11Api) super.getApi();
    }

    @Override
    public RtPkcs11Session openSession(boolean rwSession) {
        return (RtPkcs11Session) super.openSession(rwSession);
    }
}
