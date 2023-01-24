/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna;

import com.sun.jna.NativeLong;

import java.util.Objects;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11jna.CK_RUTOKEN_INIT_PARAM;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkRutokenInitParam;

import static ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna.RtPkcs11JnaLowLevelApi.makePointerFromBytes;

class CkRutokenInitParamImpl implements CkRutokenInitParam {
    private final CK_RUTOKEN_INIT_PARAM mData;

    CkRutokenInitParamImpl(CK_RUTOKEN_INIT_PARAM data) {
        mData = Objects.requireNonNull(data);
    }

    CK_RUTOKEN_INIT_PARAM getJnaValue() {
        return mData;
    }

    @Override
    public void setUseRepairMode(long mode) {
        mData.UseRepairMode = new NativeLong(mode);
    }

    @Override
    public void setNewAdminPin(byte[] adminPin) {
        mData.pNewAdminPin = makePointerFromBytes(adminPin);
        mData.ulNewAdminPinLen = new NativeLong(adminPin.length);
    }

    @Override
    public void setNewUserPin(byte[] userPin) {
        mData.pNewUserPin = makePointerFromBytes(userPin);
        mData.ulNewUserPinLen = new NativeLong(userPin.length);
    }

    @Override
    public void setChangeUserPinPolicy(long pinPolicy) {
        mData.ChangeUserPINPolicy = new NativeLong(pinPolicy);
    }

    @Override
    public void setMinAdminPinLen(long pinLen) {
        mData.ulMinAdminPinLen = new NativeLong(pinLen);
    }

    @Override
    public void setMinUserPinLen(long pinLen) {
        mData.ulMinUserPinLen = new NativeLong(pinLen);
    }

    @Override
    public void setMaxAdminRetryCount(long retryCount) {
        mData.ulMaxAdminRetryCount = new NativeLong(retryCount);
    }

    @Override
    public void setMaxUserRetryCount(long retryCount) {
        mData.ulMaxUserRetryCount = new NativeLong(retryCount);
    }

    @Override
    public void setTokenLabel(byte @Nullable [] label) {
        mData.pTokenLabel = makePointerFromBytes(label);

        if (label == null)
            mData.ulLabelLen = new NativeLong(0);
        else
            mData.ulLabelLen = new NativeLong(label.length);
    }

    @Override
    public void setSmMode(long mode) {
        mData.ulSmMode = new NativeLong(mode);
    }
}
