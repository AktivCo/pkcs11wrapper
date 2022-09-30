package ru.rutoken.pkcs11wrapper.lowlevel.datatype;

import org.jetbrains.annotations.Nullable;

public interface CkMechanism {
    /**
     * @param parameter in underlying implementation format
     */
    void setMechanism(long mechanismType, @Nullable Parameter parameter);

    /**
     * Marker interface for mechanism parameter in underlying implementation format
     */
    interface Parameter {
    }
}
