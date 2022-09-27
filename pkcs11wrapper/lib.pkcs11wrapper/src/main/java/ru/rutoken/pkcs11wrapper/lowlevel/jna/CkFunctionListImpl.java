package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import ru.rutoken.pkcs11jna.CK_FUNCTION_LIST;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkFunctionList;

import java.util.Objects;

class CkFunctionListImpl implements CkFunctionList {
    private final CK_FUNCTION_LIST mData;

    CkFunctionListImpl(CK_FUNCTION_LIST data) {
        mData = Objects.requireNonNull(data);
    }

    public CK_FUNCTION_LIST getJnaValue() {
        return mData;
    }
}
