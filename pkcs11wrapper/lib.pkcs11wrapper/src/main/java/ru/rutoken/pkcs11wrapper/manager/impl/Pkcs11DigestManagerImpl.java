package ru.rutoken.pkcs11wrapper.manager.impl;

import java.util.Arrays;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11DigestManager;
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.util.MutableLong;
import ru.rutoken.pkcs11wrapper.util.callconvention.ByteArrayCallConvention;

public class Pkcs11DigestManagerImpl extends BaseManager implements Pkcs11DigestManager {
    public Pkcs11DigestManagerImpl(Pkcs11Session session) {
        super(session);
    }

    @Override
    public void digestInit(Pkcs11Mechanism mechanism) {
        getApi().C_DigestInit(mSession.getSessionHandle(), mechanism.toCkMechanism(getLowLevelFactory()));
    }

    @Override
    public byte[] digest(byte[] data) {
        return new ByteArrayCallConvention("C_Digest") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_Digest(mSession.getSessionHandle(), data, null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return digestHelper(data, values, length);
            }
        }.call().values;
    }

    @Override
    public byte[] digest(byte[] data, int maxDigestLength) {
        final byte[] digest = new byte[maxDigestLength];
        final MutableLong outputDigestLength = new MutableLong(maxDigestLength);
        Pkcs11Exception.throwIfNotOk(digestHelper(data, digest, outputDigestLength), "C_Digest failed");
        return Arrays.copyOf(digest, (int) outputDigestLength.value);
    }

    @Override
    public void digestUpdate(byte[] part) {
        getApi().C_DigestUpdate(mSession.getSessionHandle(), part);
    }

    @Override
    public byte[] digestFinal() {
        return new ByteArrayCallConvention("C_DigestFinal") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_DigestFinal(mSession.getSessionHandle(), null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_DigestFinal(
                        mSession.getSessionHandle(), values, length));
            }
        }.call().values;
    }

    private IPkcs11ReturnValue digestHelper(byte[] data, byte[] digest, MutableLong outputDigestLength) {
        return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_Digest(
                mSession.getSessionHandle(), data, digest, outputDigestLength),
                getVendorExtensions());
    }
}
