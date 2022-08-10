package ru.rutoken.pkcs11wrapper.datatype;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkVersion;

/**
 * Immutable high-level representation of {@link CkVersion}.
 */
public class Pkcs11Version {
    private final byte mMajor;
    private final byte mMinor;

    public Pkcs11Version(CkVersion version) {
        mMajor = version.getMajor();
        mMinor = version.getMinor();
    }

    public Pkcs11Version(byte major, byte minor) {
        mMajor = major;
        mMinor = minor;
    }

    public byte getMajor() {
        return mMajor;
    }

    public byte getMinor() {
        return mMinor;
    }

    @Override
    public String toString() {
        return "Pkcs11Version{" +
                "mMajor=" + mMajor +
                ", mMinor=" + mMinor +
                '}';
    }
}
