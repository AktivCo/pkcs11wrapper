package ru.rutoken.pkcs11wrapper.lowlevel.datatype;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;

public interface CkAttribute {

    long getType();

    void setType(long type);

    /**
     * @return Object in underlying implementation format
     */
    @NotNull
    Object getValue();

    /**
     * Accepts Object in underlying implementation format
     */
    void setValue(@NotNull Object value);

    long getLongValue();

    void setLongValue(long value);

    String getStringValue();

    void setStringValue(String value);

    boolean getBooleanValue();

    void setBooleanValue(boolean value);

    byte[] getByteArrayValue();

    void setByteArrayValue(byte[] value);

    CkDate getDateValue(IPkcs11LowLevelFactory factory);

    void setDateValue(CkDate value);

    List<CkAttribute> getAttributesValue(IPkcs11LowLevelFactory factory);

    void setAttributesValue(List<CkAttribute> value);

    List<Long> getLongArrayValue();

    void setLongArrayValue(List<Long> value);

    long getValueLen();

    /**
     * If an attribute has zero length, then its value is irrelevant.
     */
    default boolean isEmpty() {
        return 0 >= getValueLen();
    }

    /**
     * Allocates space to fill attribute value by pkcs11. See C_GetAttributeValue documentation
     */
    void allocate(long size);
}
