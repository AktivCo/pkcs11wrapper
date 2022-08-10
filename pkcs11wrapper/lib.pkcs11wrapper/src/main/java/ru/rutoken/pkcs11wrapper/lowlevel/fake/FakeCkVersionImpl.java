package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkVersion;

class FakeCkVersionImpl implements CkVersion {
    private final byte mMajor;
    private final byte mMinor;

    FakeCkVersionImpl(byte major, byte minor) {
        mMajor = major;
        mMinor = minor;
    }

    @Override
    public byte getMajor() {
        return mMajor;
    }

    @Override
    public byte getMinor() {
        return mMinor;
    }
}
