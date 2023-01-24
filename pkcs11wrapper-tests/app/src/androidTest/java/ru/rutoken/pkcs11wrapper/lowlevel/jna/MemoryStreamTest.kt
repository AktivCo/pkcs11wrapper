/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.jna

import com.sun.jna.NativeLong
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.Test
import org.junit.Assume.assumeTrue
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.ByteOrder.*

private const val ONE = 1L
private val ONE_LITTLE_ENDIAN_SERIALIZED = ByteBuffer.allocate(Long.SIZE_BYTES).order(LITTLE_ENDIAN)
    .putLong(ONE).array().copyOfRange(0, NativeLong.SIZE)
private val ONE_BIG_ENDIAN_SERIALIZED = ByteBuffer.allocate(Long.SIZE_BYTES).order(BIG_ENDIAN)
    .putLong(ONE).array().copyOfRange(Long.SIZE_BYTES - NativeLong.SIZE, Long.SIZE_BYTES)

class MemoryStreamTest {
    @Test
    fun writeNativeLong() = writeOneWithOrder(nativeOrder())

    @Test
    fun setByteOrderLittleEndian() = writeOneWithOrder(LITTLE_ENDIAN)

    @Test
    fun setByteOrderBigEndian() = writeOneWithOrder(BIG_ENDIAN)

    /**
     * For Armv7a build only
     */
    @Test
    fun writeBigNativeLong() {
        assumeTrue(NativeLong.SIZE < Long.SIZE_BYTES)

        val buffer = MemoryStream.MemoryBuffer(NativeLong.SIZE)
        shouldThrow<IllegalArgumentException> {
            MemoryStream(buffer).writeNativeLong(Long.MAX_VALUE)
        }
    }

    private fun writeOneWithOrder(order: ByteOrder) {
        val buffer = MemoryStream.MemoryBuffer(NativeLong.SIZE)
        MemoryStream(buffer).setByteOrder(order).writeNativeLong(ONE)

        val data = buffer.toByteArray()
        when (order) {
            LITTLE_ENDIAN -> data shouldBe ONE_LITTLE_ENDIAN_SERIALIZED
            BIG_ENDIAN -> data shouldBe ONE_BIG_ENDIAN_SERIALIZED
            else -> error("Unexpected order $order")
        }
    }
}

private fun MemoryStream.MemoryBuffer.toByteArray() = memory.getByteArray(0, memory.size().toInt())
