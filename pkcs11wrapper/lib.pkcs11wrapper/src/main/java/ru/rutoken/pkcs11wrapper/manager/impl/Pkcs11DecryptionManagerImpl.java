/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.manager.impl;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11DecryptionManager;
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11KeyObject;
import ru.rutoken.pkcs11wrapper.util.MutableLong;
import ru.rutoken.pkcs11wrapper.util.callconvention.ByteArrayCallConvention;

public class Pkcs11DecryptionManagerImpl extends BaseManager implements Pkcs11DecryptionManager {
    public Pkcs11DecryptionManagerImpl(Pkcs11Session session) {
        super(session);
    }

    @Override
    public void decryptInit(Pkcs11Mechanism mechanism, Pkcs11KeyObject key) {
        getApi().C_DecryptInit(
                mSession.getSessionHandle(), mechanism.toCkMechanism(getLowLevelFactory()), key.getHandle());
    }

    @Override
    public byte[] decrypt(byte[] data) {
        return new ByteArrayCallConvention("C_Decrypt") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_Decrypt(mSession.getSessionHandle(), data, null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_Decrypt(
                        mSession.getSessionHandle(), data, values, length));
            }
        }.call().values;
    }

    @Override
    public byte[] decryptUpdate(byte[] part) {
        return new ByteArrayCallConvention("C_DecryptUpdate") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_DecryptUpdate(mSession.getSessionHandle(), part, null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_DecryptUpdate(
                        mSession.getSessionHandle(), part, values, length));
            }
        }.call().values;
    }

    @Override
    public byte[] decryptFinal() {
        return new ByteArrayCallConvention("C_DecryptFinal") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_DecryptFinal(mSession.getSessionHandle(), null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_DecryptFinal(
                        mSession.getSessionHandle(), values, length));
            }
        }.call().values;
    }
}
