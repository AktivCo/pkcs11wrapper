package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import org.jetbrains.annotations.Nullable;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanism;

/**
 * Defines stream API to write data into a {@link MemoryBuffer}.
 * Helps to write data into raw memory to pass into pkcs11 library, for example as void *.
 */
public class MemoryStream {
    private static final int sNativeLongBits = NativeLong.SIZE * 8;
    private final MemoryBuffer mBuffer;
    /**
     * Java is always big-endian, but mechanism parameters may have different byte ordering
     */
    private ByteOrder mByteOrder = ByteOrder.nativeOrder();
    private int mOffset;

    public MemoryStream(MemoryBuffer buffer) {
        mBuffer = Objects.requireNonNull(buffer);
    }

    private static Memory wrap(byte[] data) {
        final Memory memory = new Memory(data.length);
        memory.write(0, data, 0, data.length);
        return memory;
    }

    /**
     * Set byte order, default: nativeOrder().
     * As {@link Memory} mirrors a buffer in a native heap, which is expected to store
     * multibyte types in a native endianness if other is not specified for particular data structure.
     */
    public MemoryStream setByteOrder(ByteOrder byteOrder) {
        mByteOrder = Objects.requireNonNull(byteOrder);
        return this;
    }

    public MemoryStream writeNativeLong(long value) {
        final int significantBits = Long.SIZE - Long.numberOfLeadingZeros(value);
        if (significantBits > sNativeLongBits)
            throw new IllegalArgumentException("Too big value to fit in " + sNativeLongBits + "-bit NativeLong: "
                    + Long.toHexString(value));

        // Jni automatically converts endianness of java types (int, long, etc) when calling native methods.
        // Memory is implemented via native calls internally,
        // so we just need to reverse bytes if desired endianness differs from native one.
        if (mByteOrder != ByteOrder.nativeOrder()) {
            value = Long.reverseBytes(value);
            if (Long.SIZE > sNativeLongBits) {
                value = value >>> (Long.SIZE - sNativeLongBits);
            }
        }

        mBuffer.mMemory.setNativeLong(mOffset, new NativeLong(value, true));
        mOffset += NativeLong.SIZE;
        return this;
    }

    public MemoryStream write(byte[] data) {
        mBuffer.mMemory.write(mOffset, data, 0, data.length);
        mOffset += data.length;
        return this;
    }

    public MemoryStream writePointer(@Nullable Pointer pointer) {
        if (pointer != Pointer.NULL)
            mBuffer.mPointers.add(pointer);

        mBuffer.mMemory.setPointer(mOffset, pointer);
        mOffset += Native.POINTER_SIZE;
        return this;
    }

    public MemoryStream writeAsPointerWithLength(byte @Nullable [] data) {
        if (data == null) {
            return writePointer(Pointer.NULL).writeNativeLong(0);
        } else {
            return writePointer(wrap(data)).writeNativeLong(data.length);
        }
    }

    public MemoryStream writeAsLengthWithPointer(byte @Nullable [] data) {
        if (data == null) {
            return writeNativeLong(0).writePointer(Pointer.NULL);
        } else {
            return writeNativeLong(data.length).writePointer(wrap(data));
        }
    }

    /**
     * Helper class holding Memory and Pointers written into Memory to prevent GC before native call finishes.
     */
    public static class MemoryBuffer implements CkMechanism.Parameter {
        private final List<Pointer> mPointers = new ArrayList<>();
        private final Memory mMemory;

        public MemoryBuffer(int size) {
            mMemory = new Memory(size);
        }

        public Memory getMemory() {
            return mMemory;
        }
    }
}


