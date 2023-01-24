/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype;

public interface CkVolumeFormatInfoExtended {
    void setVolumeSize(long volumeSize);

    void setAccessMode(long accessMode);

    void setVolumeOwner(long volumeOwner);

    void setFlags(long flags);
}
