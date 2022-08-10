package ru.rutoken.pkcs11wrapper.manager.impl;

import java.util.Arrays;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11SignManager;
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11KeyObject;
import ru.rutoken.pkcs11wrapper.util.MutableLong;
import ru.rutoken.pkcs11wrapper.util.callconvention.ByteArrayCallConvention;

public class Pkcs11SignManagerImpl extends BaseManager implements Pkcs11SignManager {
    public Pkcs11SignManagerImpl(Pkcs11Session session) {
        super(session);
    }

    @Override
    public void signInit(Pkcs11Mechanism mechanism, Pkcs11KeyObject key) {
        getApi().C_SignInit(
                mSession.getSessionHandle(), mechanism.toCkMechanism(getLowLevelFactory()), key.getHandle());
    }

    @Override
    public byte[] sign(byte[] data) {
        return new ByteArrayCallConvention("C_Sign") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_Sign(mSession.getSessionHandle(), data, null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return signHelper(data, values, length);
            }
        }.call().values;
    }

    @Override
    public byte[] sign(byte[] data, int maxSignatureLength) {
        final byte[] signature = new byte[maxSignatureLength];
        final MutableLong outputSignatureLength = new MutableLong(maxSignatureLength);
        Pkcs11Exception.throwIfNotOk(signHelper(data, signature, outputSignatureLength), "C_Sign failed");
        return Arrays.copyOf(signature, (int) outputSignatureLength.value);
    }

    @Override
    public void signUpdate(byte[] part) {
        getApi().C_SignUpdate(mSession.getSessionHandle(), part);
    }

    @Override
    public byte[] signFinal() {
        return new ByteArrayCallConvention("C_SignFinal") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_SignFinal(mSession.getSessionHandle(), null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_SignFinal(
                        mSession.getSessionHandle(), values, length));
            }
        }.call().values;
    }

    @Override
    public void signRecoverInit(Pkcs11Mechanism mechanism, Pkcs11KeyObject key) {
        getApi()
                .C_SignRecoverInit(mSession.getSessionHandle(), mechanism.toCkMechanism(getLowLevelFactory()),
                        key.getHandle());
    }

    @Override
    public byte[] signRecover(byte[] data) {
        return new ByteArrayCallConvention("C_SignRecover") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_SignRecover(mSession.getSessionHandle(), data, null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_SignRecover(
                        mSession.getSessionHandle(), data, values, length));
            }
        }.call().values;
    }

    private IPkcs11ReturnValue signHelper(byte[] data, byte[] signature, MutableLong outputSignatureLength) {
        return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_Sign(
                mSession.getSessionHandle(), data, signature, outputSignatureLength),
                getVendorExtensions());
    }
}
