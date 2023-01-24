/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna;

import com.sun.jna.NativeLong;
import ru.rutoken.pkcs11jna.CK_VENDOR_PIN_PARAMS;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorPinParams;

import java.util.Objects;

import static ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna.RtPkcs11JnaLowLevelApi.makePointerFromBytes;

class CkVendorPinParamsImpl implements CkVendorPinParams {
    private final CK_VENDOR_PIN_PARAMS mData;

    CkVendorPinParamsImpl(CK_VENDOR_PIN_PARAMS data) {
        mData = Objects.requireNonNull(data);
    }

    CK_VENDOR_PIN_PARAMS getJnaValue() {
        return mData;
    }

    @Override
    public void setUserType(long userType) {
        mData.userType = new NativeLong(userType);
    }

    @Override
    public void setPin(byte[] pin) {
        mData.pPinValue = makePointerFromBytes(pin);
        mData.ulPinLength = new NativeLong(pin.length);
    }
}
