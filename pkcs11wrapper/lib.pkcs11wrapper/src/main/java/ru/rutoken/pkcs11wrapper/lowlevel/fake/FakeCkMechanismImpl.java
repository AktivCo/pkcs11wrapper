package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanism;

class FakeCkMechanismImpl implements CkMechanism {
    private IPkcs11MechanismType mType;
    private Parameter mParameter;

    @Override
    public void setMechanism(long mechanismType, @Nullable Parameter parameter) {
        mType = IPkcs11MechanismType.getInstance(mechanismType);
        mParameter = parameter;
    }

    @Override
    public long getType() {
        return mType.getAsLong();
    }

    @Nullable
    @Override
    public Parameter getParameter() {
        return mParameter;
    }

    @Override
    public long getParameterLen() {
        return 0;
    }
}
