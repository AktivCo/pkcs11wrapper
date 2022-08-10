package ru.rutoken.pkcs11wrapper.lowlevel.datatype;

public interface CkDate {

    byte[] getYear();

    void setYear(byte[] year);

    byte[] getMonth();

    void setMonth(byte[] month);

    byte[] getDay();

    void setDay(byte[] day);
}
