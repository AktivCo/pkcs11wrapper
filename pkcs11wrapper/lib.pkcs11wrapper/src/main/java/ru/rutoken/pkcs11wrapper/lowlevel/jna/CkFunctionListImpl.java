package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_FUNCTION_LIST;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkFunctionList;

class CkFunctionListImpl implements CkFunctionList {
    private final CK_FUNCTION_LIST mData;

    CkFunctionListImpl(CK_FUNCTION_LIST data) {
        mData = Objects.requireNonNull(data);
    }
}
