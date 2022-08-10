package ru.rutoken.pkcs11wrapper.lowlevel.datatype;

import org.jetbrains.annotations.Nullable;

public interface CkMechanism {
    /**
     * @param parameter in underlying implementation format
     */
    void setMechanism(long mechanismType, @Nullable Parameter parameter);

    long getType();

    /**
     * @return parameter in underlying implementation format
     */
    @Nullable
    Parameter getParameter();

    long getParameterLen();

    /**
     * Marker interface for mechanism parameter in underlying implementation format
     */
    interface Parameter {
    }
}
