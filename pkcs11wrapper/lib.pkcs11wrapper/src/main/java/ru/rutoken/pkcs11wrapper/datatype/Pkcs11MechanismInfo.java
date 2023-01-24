/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.datatype;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11Flag;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanismInfo;

/**
 * Immutable high-level representation of {@link CkMechanismInfo}.
 */
@SuppressWarnings("unused")
public class Pkcs11MechanismInfo {
    private final long mMinKeySize;
    private final long mMaxKeySize;
    private final long mFlags;

    public Pkcs11MechanismInfo(CkMechanismInfo mechanismInfo) {
        mMinKeySize = mechanismInfo.getMinKeySize();
        mMaxKeySize = mechanismInfo.getMaxKeySize();
        mFlags = mechanismInfo.getFlags();
    }

    public long getMinKeySize() {
        return mMinKeySize;
    }

    public long getMaxKeySize() {
        return mMaxKeySize;
    }

    public boolean isHw() {
        return checkFlag(Pkcs11Flag.CKF_HW);
    }

    public boolean isEncrypt() {
        return checkFlag(Pkcs11Flag.CKF_ENCRYPT);
    }

    public boolean isDecrypt() {
        return checkFlag(Pkcs11Flag.CKF_DECRYPT);
    }

    public boolean isDigest() {
        return checkFlag(Pkcs11Flag.CKF_DIGEST);
    }

    public boolean isSign() {
        return checkFlag(Pkcs11Flag.CKF_SIGN);
    }

    public boolean isSignRecover() {
        return checkFlag(Pkcs11Flag.CKF_SIGN_RECOVER);
    }

    public boolean isVerify() {
        return checkFlag(Pkcs11Flag.CKF_VERIFY);
    }

    public boolean isVerifyRecover() {
        return checkFlag(Pkcs11Flag.CKF_VERIFY_RECOVER);
    }

    public boolean isGenerate() {
        return checkFlag(Pkcs11Flag.CKF_GENERATE);
    }

    public boolean isGenerateKeyPair() {
        return checkFlag(Pkcs11Flag.CKF_GENERATE_KEY_PAIR);
    }

    public boolean isWrap() {
        return checkFlag(Pkcs11Flag.CKF_WRAP);
    }

    public boolean isUnwrap() {
        return checkFlag(Pkcs11Flag.CKF_UNWRAP);
    }

    public boolean isDerive() {
        return checkFlag(Pkcs11Flag.CKF_DERIVE);
    }

    public boolean isExtension() {
        return checkFlag(Pkcs11Flag.CKF_EXTENSION);
    }

    public long getFlags() {
        return mFlags;
    }

    @Override
    public String toString() {
        return "Pkcs11MechanismInfo{" +
                "mMinKeySize=" + mMinKeySize +
                ", mMaxKeySize=" + mMaxKeySize +
                ", mFlags=" + mFlags +
                '}';
    }

    private boolean checkFlag(Pkcs11Flag flag) {
        return (mFlags & flag.getAsLong()) != 0L;
    }
}
