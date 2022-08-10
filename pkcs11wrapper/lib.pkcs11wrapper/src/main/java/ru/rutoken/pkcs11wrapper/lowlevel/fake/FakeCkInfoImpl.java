package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkInfo;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkVersion;

class FakeCkInfoImpl implements CkInfo {
    @Override
    public CkVersion getCryptokiVersion() {
        return new FakeCkVersionImpl((byte) 2, (byte) 20);
    }

    @Override
    public byte[] getManufacturerID() {
        return "Aktiv Co.                       ".getBytes();
    }

    @Override
    public long getFlags() {
        return 0;
    }

    @Override
    public byte[] getLibraryDescription() {
        return "Rutoken ECP PKCS #11 library    ".getBytes();
    }

    @Override
    public CkVersion getLibraryVersion() {
        return new FakeCkVersionImpl((byte) 666, (byte) 0);
    }
}
