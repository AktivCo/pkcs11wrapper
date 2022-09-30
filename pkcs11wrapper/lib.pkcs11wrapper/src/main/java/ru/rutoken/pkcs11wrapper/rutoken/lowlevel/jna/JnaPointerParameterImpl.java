package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.NativeLongByReference;

import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelApi.PointerParameter;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkLocalPinInfo;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkRutokenInitParam;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorPinParams;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorRestoreFactoryDefaultsParams;

import java.util.Objects;

/**
 * Helps to write data into raw memory to pass into pkcs11 as void*.
 */
public class JnaPointerParameterImpl implements PointerParameter {
    /**
     * Store value to prevent garbage collection.
     */
    private final Object mValue;
    private final Pointer mPointer;

    public JnaPointerParameterImpl(long value) {
        mValue = new NativeLongByReference(new NativeLong(value));
        mPointer = ((NativeLongByReference) mValue).getPointer();
    }

    public JnaPointerParameterImpl(CkVendorPinParams value) {
        this(((CkVendorPinParamsImpl) value).getJnaValue());
    }

    public JnaPointerParameterImpl(CkLocalPinInfo value) {
        this(((CkLocalPinInfoImpl) value).getJnaValue());
    }

    public JnaPointerParameterImpl(CkVendorRestoreFactoryDefaultsParams value) {
        this(((CkVendorRestoreFactoryDefaultsParamsImpl) value).getJnaValue());
    }

    private JnaPointerParameterImpl(Structure value) {
        mValue = Objects.requireNonNull(value);
        value.write();
        mPointer = value.getPointer();
    }

    public Pointer getPointer() {
        return mPointer;
    }
}
