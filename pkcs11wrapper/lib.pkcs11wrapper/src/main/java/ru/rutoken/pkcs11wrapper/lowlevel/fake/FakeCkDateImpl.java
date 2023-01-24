/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkDate;

class FakeCkDateImpl implements CkDate {
    private byte[] mYear = new byte[4];
    private byte[] mMonth = new byte[2];
    private byte[] mDay = new byte[2];

    FakeCkDateImpl() {
    }

    FakeCkDateImpl(byte[] year, byte[] month, byte[] day) {
        mYear = Objects.requireNonNull(year);
        mMonth = Objects.requireNonNull(month);
        mDay = Objects.requireNonNull(day);
    }

    @Override
    public byte[] getYear() {
        return mYear;
    }

    @Override
    public void setYear(byte[] year) {
        mYear = Objects.requireNonNull(year);
    }

    @Override
    public byte[] getMonth() {
        return mMonth;
    }

    @Override
    public void setMonth(byte[] month) {
        mMonth = Objects.requireNonNull(month);
    }

    @Override
    public byte[] getDay() {
        return mDay;
    }

    @Override
    public void setDay(byte[] day) {
        mDay = Objects.requireNonNull(day);
    }
}
