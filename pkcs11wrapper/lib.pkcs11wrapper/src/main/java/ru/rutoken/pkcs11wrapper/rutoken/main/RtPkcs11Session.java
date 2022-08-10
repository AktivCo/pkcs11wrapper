package ru.rutoken.pkcs11wrapper.rutoken.main;

import java.util.List;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.main.Pkcs11SessionImpl;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PublicKeyObject;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.rutoken.manager.RtPkcs11CmsManager;
import ru.rutoken.pkcs11wrapper.util.MutableLong;
import ru.rutoken.pkcs11wrapper.util.callconvention.ByteArrayCallConvention;

public class RtPkcs11Session extends Pkcs11SessionImpl {
    private final RtPkcs11CmsManager mCmsManager;

    public RtPkcs11Session(RtPkcs11Token token, long sessionHandle) {
        super(token, sessionHandle);
        mCmsManager = new RtPkcs11CmsManager(this);
    }

    public void unblockUserPIN() {
        getApi().C_EX_UnblockUserPIN(getSessionHandle());
    }

    public byte[] getTokenName() {
        return new ByteArrayCallConvention("C_EX_GetTokenName") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_EX_GetTokenName(getSessionHandle(), null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_EX_GetTokenName(
                        getSessionHandle(), values, length));
            }
        }.call().values;
    }

    public void setTokenName(String label) {
        getApi().C_EX_SetTokenName(getSessionHandle(), label.getBytes());
    }

    public byte[] createCsr(Pkcs11PublicKeyObject publicKey, List<String> dn, Pkcs11PrivateKeyObject privateKey,
                            List<String> attributes, List<String> extensions) {
        return getApi().C_EX_CreateCSR(getSessionHandle(), publicKey.getHandle(), dn, privateKey.getHandle(),
                attributes, extensions);
    }

    public RtPkcs11CmsManager getCmsManager() {
        return mCmsManager;
    }

    @Override
    public RtPkcs11Api getApi() {
        return (RtPkcs11Api) super.getApi();
    }

    @Override
    public IRtPkcs11LowLevelApi getLowLevelApi() {
        return (IRtPkcs11LowLevelApi) super.getLowLevelApi();
    }

    @Override
    public RtPkcs11Token getToken() {
        return (RtPkcs11Token) super.getToken();
    }
}
