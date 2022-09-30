package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype;

public interface CkVendorRestoreFactoryDefaultsParams {
    void setAdminPin(byte[] adminPin);

    void setInitParam(CkRutokenInitParam initParam);

    void setNewEmitentKey(byte[] newEmitentKey);

    void setNewEmitentKeyRetryCount(long newEmitentKeyRetryCount);
}
