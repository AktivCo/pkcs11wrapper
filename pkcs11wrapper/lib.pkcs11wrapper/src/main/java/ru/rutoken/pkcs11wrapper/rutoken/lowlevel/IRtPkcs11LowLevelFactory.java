/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel;

import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.*;

public interface IRtPkcs11LowLevelFactory extends IPkcs11LowLevelFactory {
    CkVendorX509Store makeVendorX509Store();

    CkRutokenInitParam makeRutokenInitParam();

    CkVolumeInfoExtended makeVolumeInfoExtended();

    CkVolumeFormatInfoExtended makeVolumeFormatInfoExtended();

    CkVendorPinParams makeVendorPinParams();

    CkLocalPinInfo makeLocalPinInfo();

    CkVendorRestoreFactoryDefaultsParams makeVendorRestoreFactoryDefaultsParams();
}
