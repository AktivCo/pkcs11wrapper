package ru.rutoken.pkcs11wrapper.rutoken.lowlevel;

import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkRutokenInitParam;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorX509Store;

public interface IRtPkcs11LowLevelFactory extends IPkcs11LowLevelFactory {
    CkVendorX509Store makeVendorX509Store();

    CkRutokenInitParam makeRutokenInitParam();
}
