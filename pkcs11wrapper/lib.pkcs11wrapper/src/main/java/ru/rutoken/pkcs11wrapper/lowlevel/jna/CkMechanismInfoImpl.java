package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_MECHANISM_INFO;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanismInfo;

class CkMechanismInfoImpl implements CkMechanismInfo {
    private final CK_MECHANISM_INFO mData;

    CkMechanismInfoImpl(CK_MECHANISM_INFO data) {
        mData = Objects.requireNonNull(data);
    }

    @Override
    public long getMinKeySize() {
        return mData.ulMinKeySize.longValue();
    }

    @Override
    public long getMaxKeySize() {
        return mData.ulMaxKeySize.longValue();
    }

    @Override
    public long getFlags() {
        return mData.flags.longValue();
    }
}
