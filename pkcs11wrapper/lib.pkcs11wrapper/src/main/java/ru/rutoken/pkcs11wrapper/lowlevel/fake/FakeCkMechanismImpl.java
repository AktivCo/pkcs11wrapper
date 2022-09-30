package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import org.jetbrains.annotations.Nullable;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanism;

class FakeCkMechanismImpl implements CkMechanism {
    @Override
    public void setMechanism(long mechanismType, @Nullable Parameter parameter) {
    }
}
