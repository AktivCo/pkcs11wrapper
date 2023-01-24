/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.datatype;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11ReturnValue;

public class DetachedCmsVerifyResult {
    /**
     * {@link Pkcs11ReturnValue#CKR_OK} or
     * {@link Pkcs11ReturnValue#CKR_SIGNATURE_INVALID} or
     * {@link RtPkcs11ReturnValue#CKR_CERT_CHAIN_NOT_VERIFIED}
     */
    private final IPkcs11ReturnValue mResult;
    /**
     * Signer certificates chain
     */
    @Nullable
    private final List<byte[]> mSignerCertificates;

    public DetachedCmsVerifyResult(IPkcs11ReturnValue result, @Nullable List<byte[]> signerCertificates) {
        mResult = Objects.requireNonNull(result);
        mSignerCertificates = signerCertificates;
    }

    public IPkcs11ReturnValue getResult() {
        return mResult;
    }

    @Nullable
    public List<byte[]> getSignerCertificates() {
        return mSignerCertificates;
    }
}
