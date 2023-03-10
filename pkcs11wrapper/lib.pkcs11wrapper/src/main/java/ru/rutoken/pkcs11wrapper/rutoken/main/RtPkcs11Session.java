/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.main;

import java.util.List;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.main.Pkcs11SessionImpl;
import ru.rutoken.pkcs11wrapper.object.certificate.Pkcs11CertificateObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PublicKeyObject;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelFactory;
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

    public void setLicense(long licenseNum, byte[] license) {
        getApi().C_EX_SetLicense(getSessionHandle(), licenseNum, license);
    }

    public byte[] getLicense(long licenseNum) {
        return new ByteArrayCallConvention("C_EX_GetLicense") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_EX_GetLicense(getSessionHandle(), licenseNum, null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_EX_GetLicense(
                        getSessionHandle(), licenseNum, values, length));
            }
        }.call().values;
    }

    public String getCertificateInfo(Pkcs11CertificateObject certificate) {
        return getApi().C_EX_GetCertificateInfoText(getSessionHandle(), certificate.getHandle());
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
        return getApi().getLowLevelApi();
    }

    @Override
    public IRtPkcs11LowLevelFactory getLowLevelFactory() {
        return getApi().getLowLevelFactory();
    }

    @Override
    public RtPkcs11Token getToken() {
        return (RtPkcs11Token) super.getToken();
    }
}
