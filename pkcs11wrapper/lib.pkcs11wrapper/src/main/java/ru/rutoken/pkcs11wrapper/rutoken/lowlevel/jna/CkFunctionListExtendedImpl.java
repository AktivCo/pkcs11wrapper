/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna;

import ru.rutoken.pkcs11jna.CK_FUNCTION_LIST_EXTENDED;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkFunctionListExtended;

import java.util.Objects;

class CkFunctionListExtendedImpl implements CkFunctionListExtended {
    private final CK_FUNCTION_LIST_EXTENDED mData;

    CkFunctionListExtendedImpl(CK_FUNCTION_LIST_EXTENDED data) {
        mData = Objects.requireNonNull(data);
    }

    public CK_FUNCTION_LIST_EXTENDED getJnaValue() {
        return mData;
    }
}
