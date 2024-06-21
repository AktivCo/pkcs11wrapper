/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.datatype;

import java.time.LocalDate;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkDate;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.util.Pkcs11Utility;

/**
 * Immutable high-level representation of {@link CkDate}.
 */
public class Pkcs11Date {
    private final LocalDate mDate;

    public Pkcs11Date(CkDate date) {
        mDate = Pkcs11Utility.parseDate(date.getYear(), date.getMonth(), date.getDay());
    }

    public Pkcs11Date(LocalDate date) {
        mDate = date;
    }

    public CkDate toCkDate(IPkcs11LowLevelFactory factory) {
        final CkDate date = factory.makeDate();
        Pkcs11Utility.assignCkDate(date, mDate);
        return date;
    }

    public LocalDate getDate() {
        return mDate;
    }
}
