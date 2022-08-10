package ru.rutoken.pkcs11wrapper.manager.impl;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11DualFunctionManager;
import ru.rutoken.pkcs11wrapper.util.MutableLong;
import ru.rutoken.pkcs11wrapper.util.callconvention.ByteArrayCallConvention;

public class Pkcs11DualFunctionManagerImpl extends BaseManager implements Pkcs11DualFunctionManager {
    public Pkcs11DualFunctionManagerImpl(Pkcs11Session session) {
        super(session);
    }

    @Override
    public byte[] digestEncryptUpdate(byte[] part) {
        return new ByteArrayCallConvention("C_DigestEncryptUpdate") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_DigestEncryptUpdate(mSession.getSessionHandle(), part, null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_DigestEncryptUpdate(
                        mSession.getSessionHandle(), part, values, length));
            }
        }.call().values;
    }

    @Override
    public byte[] decryptDigestUpdate(byte[] part) {
        return new ByteArrayCallConvention("C_DecryptDigestUpdate") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_DecryptDigestUpdate(mSession.getSessionHandle(), part, null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_DecryptDigestUpdate(
                        mSession.getSessionHandle(), part, values, length));
            }
        }.call().values;
    }

    @Override
    public byte[] signEncryptUpdate(byte[] part) {
        return new ByteArrayCallConvention("C_SignEncryptUpdate") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_SignEncryptUpdate(mSession.getSessionHandle(), part, null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_SignEncryptUpdate(
                        mSession.getSessionHandle(), part, values, length));
            }
        }.call().values;
    }

    @Override
    public byte[] decryptVerifyUpdate(byte[] encryptedPart) {
        return new ByteArrayCallConvention("C_DecryptVerifyUpdate") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_DecryptVerifyUpdate(mSession.getSessionHandle(), encryptedPart, null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_DecryptVerifyUpdate(
                        mSession.getSessionHandle(), encryptedPart, values, length));
            }
        }.call().values;
    }
}
