package ru.rutoken.pkcs11wrapper.datatype;

import java.util.Objects;

public class Pkcs11VerifyRecoverResult {
    private final boolean mIsSuccess;
    private final byte[] mData;

    public Pkcs11VerifyRecoverResult(boolean isSuccess, byte[] data) {
        mIsSuccess = isSuccess;
        mData = Objects.requireNonNull(data);
    }

    public boolean isSuccess() {
        return mIsSuccess;
    }

    public byte[] getData() {
        return mData;
    }
}
