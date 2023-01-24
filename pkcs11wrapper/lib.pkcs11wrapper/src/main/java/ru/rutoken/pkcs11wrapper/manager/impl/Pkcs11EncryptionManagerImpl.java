/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.manager.impl;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11EncryptionManager;
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11KeyObject;
import ru.rutoken.pkcs11wrapper.util.MutableLong;
import ru.rutoken.pkcs11wrapper.util.callconvention.ByteArrayCallConvention;

public class Pkcs11EncryptionManagerImpl extends BaseManager implements Pkcs11EncryptionManager {
    public Pkcs11EncryptionManagerImpl(Pkcs11Session session) {
        super(session);
    }

    @Override
    public void encryptInit(Pkcs11Mechanism mechanism, Pkcs11KeyObject key) {
        getApi().C_EncryptInit(
                mSession.getSessionHandle(), mechanism.toCkMechanism(getLowLevelFactory()), key.getHandle());
    }

    @Override
    public byte[] encrypt(byte[] data) {
        return new ByteArrayCallConvention("C_Encrypt") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_Encrypt(mSession.getSessionHandle(), data, null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_Encrypt(
                        mSession.getSessionHandle(), data, values, length));
            }
        }.call().values;
    }

    @Override
    public byte[] encryptUpdate(byte[] part) {
        return new ByteArrayCallConvention("C_EncryptUpdate") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_EncryptUpdate(mSession.getSessionHandle(), part, null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_EncryptUpdate(
                        mSession.getSessionHandle(), part, values, length));
            }
        }.call().values;
    }

    @Override
    public byte[] encryptFinal() {
        return new ByteArrayCallConvention("C_EncryptFinal") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_EncryptFinal(mSession.getSessionHandle(), null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_EncryptFinal(
                        mSession.getSessionHandle(), values, length));
            }
        }.call().values;
    }
}
