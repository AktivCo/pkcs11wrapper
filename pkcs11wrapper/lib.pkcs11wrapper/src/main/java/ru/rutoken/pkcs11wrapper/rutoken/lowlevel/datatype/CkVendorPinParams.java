package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype;

public interface CkVendorPinParams {
    void setUserType(long userType);

    void setPin(byte[] pin);
}
