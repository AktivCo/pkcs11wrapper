package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_DATE;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkDate;

class CkDateImpl implements CkDate {
    private final CK_DATE mData;

    CkDateImpl(CK_DATE data) {
        mData = Objects.requireNonNull(data);
    }

    CK_DATE getJnaValue() {
        return mData;
    }

    @Override
    public byte[] getYear() {
        return mData.year;
    }

    @Override
    public void setYear(byte[] year) {
        mData.year = year;
    }

    @Override
    public byte[] getMonth() {
        return mData.month;
    }

    @Override
    public void setMonth(byte[] month) {
        mData.month = month;
    }

    @Override
    public byte[] getDay() {
        return mData.day;
    }

    @Override
    public void setDay(byte[] day) {
        mData.day = day;
    }
}
