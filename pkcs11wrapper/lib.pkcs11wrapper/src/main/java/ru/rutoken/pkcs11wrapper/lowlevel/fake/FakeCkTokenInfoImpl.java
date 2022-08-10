package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkTokenInfo;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkVersion;

class FakeCkTokenInfoImpl implements CkTokenInfo {
    private final byte[] mSerialNumber;

    FakeCkTokenInfoImpl(byte[] serialNumber) {
        mSerialNumber = Objects.requireNonNull(serialNumber);
    }

    @Override
    public byte[] getLabel() {
        return "Fake token".getBytes();
    }

    @Override
    public byte[] getManufacturerID() {
        return "Aktiv Co.                       ".getBytes();
    }

    @Override
    public byte[] getModel() {
        return "".getBytes();
    }

    @Override
    public byte[] getSerialNumber() {
        return mSerialNumber;
    }

    @Override
    public long getFlags() {
        return 0;
    }

    @Override
    public long getMaxSessionCount() {
        return 0;
    }

    @Override
    public long getSessionCount() {
        return 0;
    }

    @Override
    public long getMaxRwSessionCount() {
        return 0;
    }

    @Override
    public long getRwSessionCount() {
        return 0;
    }

    @Override
    public long getMaxPinLen() {
        return 0;
    }

    @Override
    public long getMinPinLen() {
        return 0;
    }

    @Override
    public long getTotalPublicMemory() {
        return 0;
    }

    @Override
    public long getFreePublicMemory() {
        return 0;
    }

    @Override
    public long getTotalPrivateMemory() {
        return 0;
    }

    @Override
    public long getFreePrivateMemory() {
        return 0;
    }

    @Override
    public CkVersion getHardwareVersion() {
        return new FakeCkVersionImpl((byte) 0, (byte) 0);
    }

    @Override
    public CkVersion getFirmwareVersion() {
        return new FakeCkVersionImpl((byte) 0, (byte) 0);
    }

    @Override
    public byte[] getUtcTime() {
        return "".getBytes();
    }
}
