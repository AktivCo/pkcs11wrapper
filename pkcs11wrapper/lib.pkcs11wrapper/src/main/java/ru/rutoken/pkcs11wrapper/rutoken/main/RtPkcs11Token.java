package ru.rutoken.pkcs11wrapper.rutoken.main;

import org.jetbrains.annotations.Nullable;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Slot;
import ru.rutoken.pkcs11wrapper.main.Pkcs11TokenImpl;
import ru.rutoken.pkcs11wrapper.rutoken.datatype.RutokenInitParam;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkTokenInfoExtended;

public class RtPkcs11Token extends Pkcs11TokenImpl {
    RtPkcs11Token(Pkcs11Slot slot) {
        super(slot);
    }

    public CkTokenInfoExtended getTokenInfoExtended() {
        return getApi().C_EX_GetTokenInfoExtended(getSlot().getId());
    }

    public void initToken(@Nullable String adminPin, RutokenInitParam param) {
        getApi().C_EX_InitToken(getSlot().getId(), adminPin == null ? null : adminPin.getBytes(),
                param.toCkRutokenInitParam((IRtPkcs11LowLevelFactory) getLowLevelFactory()));
    }

    public void setLocalPin(String currentPin, String newLocalPin, long localPinId) {
        getApi().C_EX_SetLocalPIN(getSlot().getId(), currentPin.getBytes(), newLocalPin.getBytes(), localPinId);
    }

    @Override
    public RtPkcs11Api getApi() {
        return (RtPkcs11Api) super.getApi();
    }

    @Override
    public IRtPkcs11LowLevelApi getLowLevelApi() {
        return getApi().getLowLevelApi();
    }

    @Override
    public IRtPkcs11LowLevelFactory getLowLevelFactory() {
        return getApi().getLowLevelFactory();
    }

    @Override
    public RtPkcs11Session openSession(boolean rwSession) {
        return (RtPkcs11Session) super.openSession(rwSession);
    }
}
