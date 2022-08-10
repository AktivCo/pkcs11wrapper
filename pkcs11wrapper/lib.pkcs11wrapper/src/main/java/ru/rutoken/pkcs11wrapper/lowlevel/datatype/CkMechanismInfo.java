package ru.rutoken.pkcs11wrapper.lowlevel.datatype;

public interface CkMechanismInfo {

    long getMinKeySize();

    long getMaxKeySize();

    long getFlags();
}
