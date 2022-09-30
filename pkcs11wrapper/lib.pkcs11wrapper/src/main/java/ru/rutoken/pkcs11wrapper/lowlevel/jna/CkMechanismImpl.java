package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_MECHANISM;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanism;

class CkMechanismImpl implements CkMechanism {
    private final CK_MECHANISM mData;

    CkMechanismImpl(CK_MECHANISM data) {
        mData = Objects.requireNonNull(data);
    }

    CK_MECHANISM getJnaValue() {
        return mData;
    }

    /**
     * @param parameter this implementation always expects MemoryBuffer
     */
    @Override
    public void setMechanism(long mechanismType, @Nullable Parameter parameter) {
        if (parameter != null) {
            if (!(parameter instanceof MemoryStream.MemoryBuffer))
                throw new IllegalArgumentException("parameter must be an instance of a MemoryBuffer class");

            MemoryStream.MemoryBuffer memoryBufParameter = (MemoryStream.MemoryBuffer) parameter;
            mData.pParameter = memoryBufParameter.getMemory();
            mData.ulParameterLen = new NativeLong(memoryBufParameter.getMemory().size());
        } else {
            mData.pParameter = Pointer.NULL;
            mData.ulParameterLen = new NativeLong(0);
        }
        mData.mechanism.setValue(mechanismType);
    }
}
