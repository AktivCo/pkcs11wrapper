/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.manager.impl;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11VerifyRecoverResult;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11VerifyManager;
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11KeyObject;
import ru.rutoken.pkcs11wrapper.util.MutableLong;
import ru.rutoken.pkcs11wrapper.util.callconvention.ByteArrayCallConvention;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue.CKR_OK;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue.CKR_SIGNATURE_INVALID;
import static ru.rutoken.pkcs11wrapper.util.callconvention.ByteArrayCallConvention.CallResult;

public class Pkcs11VerifyManagerImpl extends BaseManager implements Pkcs11VerifyManager {
    public Pkcs11VerifyManagerImpl(Pkcs11Session session) {
        super(session);
    }

    @Override
    public void verifyInit(Pkcs11Mechanism mechanism, Pkcs11KeyObject key) {
        getApi().C_VerifyInit(
                mSession.getSessionHandle(), mechanism.toCkMechanism(getLowLevelFactory()), key.getHandle());
    }

    @Override
    public boolean verify(byte[] data, byte[] signature) {
        return getApi().C_Verify(mSession.getSessionHandle(), data, signature);
    }

    @Override
    public void requireVerifyAtOnce(byte[] data, byte[] signature, Pkcs11Mechanism mechanism, Pkcs11KeyObject key) {
        requireVerification(verifyAtOnce(data, signature, mechanism, key));
    }

    @Override
    public void verifyUpdate(byte[] part) {
        getApi().C_VerifyUpdate(mSession.getSessionHandle(), part);
    }

    @Override
    public boolean verifyFinal(byte[] signature) {
        return getApi().C_VerifyFinal(mSession.getSessionHandle(), signature);
    }

    @Override
    public void requireVerifyFinal(byte[] signature) {
        requireVerification(verifyFinal(signature));
    }

    @Override
    public void verifyRecoverInit(Pkcs11Mechanism mechanism, Pkcs11KeyObject key) {
        getApi().C_VerifyRecoverInit(
                mSession.getSessionHandle(), mechanism.toCkMechanism(getLowLevelFactory()), key.getHandle());
    }

    @Override
    public Pkcs11VerifyRecoverResult verifyRecover(byte[] signature) {
        final CallResult result = new ByteArrayCallConvention("C_VerifyRecover") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_VerifyRecover(mSession.getSessionHandle(), signature, null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_VerifyRecover(
                        mSession.getSessionHandle(), signature, values, length));
            }
        }.call(CKR_SIGNATURE_INVALID);
        return new Pkcs11VerifyRecoverResult(result.result == CKR_OK, result.values);
    }

    @Override
    public byte[] requireVerifyRecoverAtOnce(byte[] signature, Pkcs11Mechanism mechanism, Pkcs11KeyObject key) {
        final Pkcs11VerifyRecoverResult result = verifyRecoverAtOnce(signature, mechanism, key);
        requireVerification(result.isSuccess());
        return result.getData();
    }

    private void requireVerification(boolean result) {
        Pkcs11Exception.throwIfNotOk(result ? CKR_OK : CKR_SIGNATURE_INVALID, "Signature verification failed");
    }
}
