package ru.rutoken.pkcs11wrapper.datatype;

import java.util.Date;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkDate;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.util.Pkcs11Utility;

/**
 * Immutable high-level representation of {@link CkDate}.
 */
public class Pkcs11Date {
    private final Date mDate;

    public Pkcs11Date(CkDate date) {
        mDate = Pkcs11Utility.parseDate(date.getYear(), date.getMonth(), date.getDay());
    }

    public CkDate toCkDate(IPkcs11LowLevelFactory factory) {
        final CkDate date = factory.makeDate();
        Pkcs11Utility.assignCkDate(date, mDate);
        return date;
    }

    public Date getDate() {
        return mDate;
    }
}
