package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna;

import com.sun.jna.NativeLong;

import ru.rutoken.pkcs11jna.CK_RUTOKEN_INIT_PARAM;
import ru.rutoken.pkcs11jna.CK_VENDOR_RESTORE_FACTORY_DEFAULTS_PARAMS;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkRutokenInitParam;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorRestoreFactoryDefaultsParams;

import java.util.Objects;

import static ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna.RtPkcs11JnaLowLevelApi.makePointerFromBytes;

class CkVendorRestoreFactoryDefaultsParamsImpl implements CkVendorRestoreFactoryDefaultsParams {
    private final CK_VENDOR_RESTORE_FACTORY_DEFAULTS_PARAMS mData;

    CkVendorRestoreFactoryDefaultsParamsImpl(CK_VENDOR_RESTORE_FACTORY_DEFAULTS_PARAMS data) {
        mData = Objects.requireNonNull(data);
    }

    CK_VENDOR_RESTORE_FACTORY_DEFAULTS_PARAMS getJnaValue() {
        return mData;
    }

    @Override
    public void setAdminPin(byte[] adminPin) {
        mData.pAdminPin = makePointerFromBytes(adminPin);
        mData.ulAdminPinLen = new NativeLong(adminPin.length);
    }

    @Override
    public void setInitParam(CkRutokenInitParam initParam) {
        CK_RUTOKEN_INIT_PARAM value = ((CkRutokenInitParamImpl) initParam).getJnaValue();
        value.write();
        mData.pInitParam = new CK_RUTOKEN_INIT_PARAM.ByReference(value.getPointer());
    }

    @Override
    public void setNewEmitentKey(byte[] newEmitentKey) {
        mData.pNewEmitentKey = makePointerFromBytes(newEmitentKey);
        mData.ulNewEmitentKeyLen = new NativeLong(newEmitentKey.length);
    }

    @Override
    public void setNewEmitentKeyRetryCount(long newEmitentKeyRetryCount) {
        mData.ulNewEmitentKeyRetryCount = new NativeLong(newEmitentKeyRetryCount);
    }
}
