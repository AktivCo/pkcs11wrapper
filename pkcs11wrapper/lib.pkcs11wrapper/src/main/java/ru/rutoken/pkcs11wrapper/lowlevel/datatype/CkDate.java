/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.datatype;

public interface CkDate {

    byte[] getYear();

    void setYear(byte[] year);

    byte[] getMonth();

    void setMonth(byte[] month);

    byte[] getDay();

    void setDay(byte[] day);
}
