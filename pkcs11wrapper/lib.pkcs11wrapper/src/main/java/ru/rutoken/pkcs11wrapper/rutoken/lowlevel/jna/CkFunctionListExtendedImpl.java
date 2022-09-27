package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna;

import ru.rutoken.pkcs11jna.CK_FUNCTION_LIST_EXTENDED;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkFunctionListExtended;

import java.util.Objects;

public class CkFunctionListExtendedImpl implements CkFunctionListExtended {
    private final CK_FUNCTION_LIST_EXTENDED mData;

    CkFunctionListExtendedImpl(CK_FUNCTION_LIST_EXTENDED data) {
        mData = Objects.requireNonNull(data);
    }

    public CK_FUNCTION_LIST_EXTENDED getJnaValue() {
        return mData;
    }
}
