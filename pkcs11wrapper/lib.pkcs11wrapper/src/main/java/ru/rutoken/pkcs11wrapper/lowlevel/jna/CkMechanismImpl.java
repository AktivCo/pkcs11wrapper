package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_MECHANISM;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanism;

class CkMechanismImpl implements CkMechanism {
    private final CK_MECHANISM mData;
    /**
     * We must store a strong reference to a Memory until a native call finishes
     */
    @Nullable
    private MemoryStream.MemoryBuffer mParameter = null;

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

            mParameter = (MemoryStream.MemoryBuffer) parameter;
            mData.pParameter = mParameter.getMemory();
            mData.ulParameterLen = new NativeLong(mParameter.getMemory().size());
        } else {
            mParameter = null;
            mData.pParameter = Pointer.NULL;
            mData.ulParameterLen = new NativeLong(0);
        }
        mData.mechanism.setValue(mechanismType);
    }

    @Override
    public long getType() {
        return mData.mechanism.longValue();
    }

    @Override
    @Nullable
    public Parameter getParameter() {
        return mParameter;
    }

    @Override
    public long getParameterLen() {
        return mData.ulParameterLen.longValue();
    }
}
