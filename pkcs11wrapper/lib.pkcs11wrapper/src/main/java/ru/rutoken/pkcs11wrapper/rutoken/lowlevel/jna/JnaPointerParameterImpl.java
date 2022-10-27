package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.NativeLongByReference;

import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelApi.PointerParameter;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkLocalPinInfo;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorPinParams;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorRestoreFactoryDefaultsParams;

import java.util.Objects;

/**
 * Helps to write data into raw memory to pass into pkcs11 as void*.
 */
public class JnaPointerParameterImpl implements PointerParameter {
    private final Object mValue;
    /**
     * Store value to prevent garbage collection.
     */
    private final Object mJnaValue;
    private final Pointer mPointer;

    public JnaPointerParameterImpl(long value) {
        mValue = value;
        mJnaValue = new NativeLongByReference(new NativeLong(value));
        mPointer = ((NativeLongByReference) mJnaValue).getPointer();
    }

    public JnaPointerParameterImpl(CkVendorPinParams value) {
        this(Objects.requireNonNull(value), ((CkVendorPinParamsImpl) value).getJnaValue());
    }

    public JnaPointerParameterImpl(CkLocalPinInfo value) {
        this(Objects.requireNonNull(value), ((CkLocalPinInfoImpl) value).getJnaValue());
    }

    public JnaPointerParameterImpl(CkVendorRestoreFactoryDefaultsParams value) {
        this(Objects.requireNonNull(value), ((CkVendorRestoreFactoryDefaultsParamsImpl) value).getJnaValue());
    }

    private JnaPointerParameterImpl(Object value, Structure jnaValue) {
        mValue = value;
        mJnaValue = jnaValue;
        jnaValue.write();
        mPointer = jnaValue.getPointer();
    }

    public Pointer getPointer() {
        return mPointer;
    }

    public CkLocalPinInfo getCkLocalPinInfo() {
        if (!(mValue instanceof CkLocalPinInfo))
            throw new IllegalStateException("Value must be an instance of a CkLocalPinInfo class");

        return (CkLocalPinInfo) mValue;
    }
}
