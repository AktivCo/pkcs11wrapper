package ru.rutoken.pkcs11wrapper.lowlevel.datatype;

public interface CkInfo {

    CkVersion getCryptokiVersion();

    byte[] getManufacturerID();

    long getFlags();

    byte[] getLibraryDescription();

    CkVersion getLibraryVersion();
}
