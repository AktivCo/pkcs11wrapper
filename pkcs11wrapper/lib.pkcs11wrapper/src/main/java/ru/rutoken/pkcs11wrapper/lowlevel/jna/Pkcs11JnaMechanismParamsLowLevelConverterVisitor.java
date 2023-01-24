/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyDerivationFunction.CKD_CPDIVERSIFY_KDF;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyDerivationFunction.CKD_NULL;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyDerivationFunction.CKD_CPDIVERSIFY_KDF_JRT;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyDerivationFunction.CKD_KDF_4357;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyDerivationFunction.CKD_KDF_GOSTR3411_2012_256;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyDerivationFunction.CKD_NULL_KDF_JRT;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Structure;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import ru.rutoken.pkcs11wrapper.mechanism.parameter.CkEcdh1DeriveParams;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.CkGostR3410DeriveParams;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.CkGostR3410KeyWrapParams;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.CkKdfTreeGostParams;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.CkRsaPkcsPssParams;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.CkVendorGostKegParams;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.CkVendorVkoGostR3410_2012_512Params;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.Pkcs11ByteArrayMechanismParams;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.Pkcs11LongMechanismParams;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.Pkcs11MechanismParams;

/**
 * This class performs conversion from high-level {@link Pkcs11MechanismParams} type to
 * low-level representation, used in JNA implementation.
 * On JNA layer all mechanism parameters may be represented as
 * JNA {@link Structure} or raw {@link Memory}.
 * The current implementation uses {@link Memory} only.
 */
public class Pkcs11JnaMechanismParamsLowLevelConverterVisitor
        implements Pkcs11MechanismParams.LowLevelConverterVisitor {
    private static final long GOST2001_PUB_KEY_LEN = 64;
    /**
     * Value is limited by token implementation
     */
    private static final long GOST2001_VKO_UKM_LEN = 8;

    private MemoryStream.MemoryBuffer mBuffer;

    @Override
    public void visit(Pkcs11ByteArrayMechanismParams parameter) {
        mBuffer = new MemoryStream.MemoryBuffer(parameter.getByteArray().length);
        new MemoryStream(mBuffer).write(parameter.getByteArray());
    }

    /**
     * @param parameter This structure may be serialized in different formats depending on kdf type and GOST 2001/2012.
     *                  Currently we support GOST 2012 format and 2001 with CK_GOSTR3410_DERIVE_PARAMS_JRT structure.
     */
    @Override
    public void visit(CkGostR3410DeriveParams parameter) {
        if (parameter.getKdf() != CKD_NULL.getAsLong() &&
                parameter.getKdf() != CKD_CPDIVERSIFY_KDF.getAsLong() &&
                parameter.getKdf() != CKD_KDF_4357.getAsLong() &&
                parameter.getKdf() != CKD_KDF_GOSTR3411_2012_256.getAsLong() &&
                parameter.getKdf() != CKD_CPDIVERSIFY_KDF_JRT.getAsLong() &&
                parameter.getKdf() != CKD_NULL_KDF_JRT.getAsLong())
            throw new IllegalArgumentException("Unsupported kdf value: " + parameter.getKdf());

        if (parameter.getKdf() == CKD_CPDIVERSIFY_KDF_JRT.getAsLong() ||
                parameter.getKdf() == CKD_NULL_KDF_JRT.getAsLong()) { // GOST 2001 CK_GOSTR3410_DERIVE_PARAMS_JRT format
            if (parameter.getPublicKeyValue().length != GOST2001_PUB_KEY_LEN)
                throw new IllegalArgumentException("Wrong public key length: " + parameter.getPublicKeyValue().length);
            if (parameter.getUkm().length != GOST2001_VKO_UKM_LEN)
                throw new IllegalArgumentException("Wrong UKM length: " + parameter.getUkm().length);

            mBuffer = new MemoryStream.MemoryBuffer(3 * NativeLong.SIZE
                    + parameter.getPublicKeyValue().length + parameter.getUkm().length);
            new MemoryStream(mBuffer)
                    .writeNativeLong(parameter.getKdf())
                    .writeNativeLong(parameter.getPublicKeyValue().length)
                    .write(parameter.getPublicKeyValue())
                    .writeNativeLong(parameter.getUkm().length)
                    .write(parameter.getUkm());
        } else { // GOST 2012
            final ByteBuffer buffer = ByteBuffer.allocate(3 * Integer.SIZE / Byte.SIZE
                    + parameter.getPublicKeyValue().length + parameter.getUkm().length);
            buffer.order(ByteOrder.LITTLE_ENDIAN)
                    .putInt((int) parameter.getKdf())
                    .putInt(parameter.getPublicKeyValue().length)
                    .put(parameter.getPublicKeyValue())
                    .putInt(parameter.getUkm().length)
                    .put(parameter.getUkm());

            mBuffer = new MemoryStream.MemoryBuffer(buffer.capacity());
            new MemoryStream(mBuffer).write(buffer.array());
        }
    }

    @Override
    public void visit(CkGostR3410KeyWrapParams parameter) {
        mBuffer = new MemoryStream.MemoryBuffer(NativeLong.SIZE * 3 + Native.POINTER_SIZE * 2);
        new MemoryStream(mBuffer)
                .writeAsPointerWithLength(parameter.getWrapOid())
                .writeAsPointerWithLength(parameter.getUkm())
                .writeNativeLong(parameter.getKeyHandle());
    }

    @Override
    public void visit(CkKdfTreeGostParams parameter) {
        mBuffer = new MemoryStream.MemoryBuffer(NativeLong.SIZE * 5 + Native.POINTER_SIZE * 2);
        new MemoryStream(mBuffer)
                .writeAsLengthWithPointer(parameter.getLabel())
                .writeAsLengthWithPointer(parameter.getSeed())
                .writeNativeLong(parameter.getR())
                .writeNativeLong(parameter.getL())
                .writeNativeLong(parameter.getOffset());
    }

    @Override
    public void visit(CkVendorGostKegParams parameter) {
        mBuffer = new MemoryStream.MemoryBuffer(NativeLong.SIZE * 2 + Native.POINTER_SIZE * 2);
        new MemoryStream(mBuffer)
                .writeAsPointerWithLength(parameter.getPublicData())
                .writeAsPointerWithLength(parameter.getUkm());
    }

    @Override
    public void visit(Pkcs11LongMechanismParams parameter) {
        mBuffer = new MemoryStream.MemoryBuffer(NativeLong.SIZE);
        new MemoryStream(mBuffer).writeNativeLong(parameter.getValue());
    }

    @Override
    public void visit(CkVendorVkoGostR3410_2012_512Params parameter) {
        mBuffer = new MemoryStream.MemoryBuffer(NativeLong.SIZE * 3 + Native.POINTER_SIZE * 2);
        new MemoryStream(mBuffer)
                .writeNativeLong(parameter.getKdf())
                .writeAsPointerWithLength(parameter.getPublicKeyValue())
                .writeAsPointerWithLength(parameter.getUkm());
    }

    @Override
    public void visit(CkEcdh1DeriveParams parameter) {
        mBuffer = new MemoryStream.MemoryBuffer(NativeLong.SIZE * 3 + Native.POINTER_SIZE * 2);
        new MemoryStream(mBuffer)
                .writeNativeLong(parameter.getKdf())
                .writeAsLengthWithPointer(parameter.getSharedData())
                .writeAsLengthWithPointer(parameter.getPublicKeyValue());
    }

    @Override
    public void visit(CkRsaPkcsPssParams parameter) {
        mBuffer = new MemoryStream.MemoryBuffer(NativeLong.SIZE * 3);
        new MemoryStream(mBuffer)
                .writeNativeLong(parameter.getHashAlgorithm())
                .writeNativeLong(parameter.getMgf())
                .writeNativeLong(parameter.getSaltLength());
    }

    @NotNull
    @Override
    public MemoryStream.MemoryBuffer getConverted() {
        return mBuffer;
    }
}
