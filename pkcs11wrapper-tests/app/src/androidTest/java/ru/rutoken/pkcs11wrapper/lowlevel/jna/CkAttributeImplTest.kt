/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.jna

import com.sun.jna.NativeLong
import io.kotest.matchers.shouldBe
import org.junit.Test
import ru.rutoken.pkcs11jna.CK_ATTRIBUTE
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11AttributeType.CKA_VENDOR_CURRENT_TOKEN_INTERFACE

const val VENDOR_DEFINED_VALUE = 0x800000FFL

class CkAttributeImplTest {
    @Test
    fun makeVendorLowLevelAttributeFromJna() {
        val jnaAttribute = CK_ATTRIBUTE().apply {
            setAttr(CKA_VENDOR_CURRENT_TOKEN_INTERFACE.asLong, VENDOR_DEFINED_VALUE)
        }
        val lowLevelAttribute = CkAttributeImpl(CK_ATTRIBUTE()).apply { assignFromJnaAttribute(jnaAttribute) }

        lowLevelAttribute.longValue shouldBe VENDOR_DEFINED_VALUE
        lowLevelAttribute.type shouldBe CKA_VENDOR_CURRENT_TOKEN_INTERFACE.asLong
        lowLevelAttribute.valueLen shouldBe NativeLong.SIZE
    }

    @Test
    fun makeVendorJnaAttributeFromLowLevel() {
        val lowLevelAttribute = CkAttributeImpl(CK_ATTRIBUTE()).apply {
            type = CKA_VENDOR_CURRENT_TOKEN_INTERFACE.asLong
            longValue = VENDOR_DEFINED_VALUE
        }

        val jnaAttribute = CK_ATTRIBUTE().apply {
            lowLevelAttribute.copyToJnaAttribute(this)
        }

        jnaAttribute.type.toLong() shouldBe CKA_VENDOR_CURRENT_TOKEN_INTERFACE.asLong
        jnaAttribute.ulValueLen.toLong() shouldBe NativeLong.SIZE
        if (NativeLong.SIZE < Long.SIZE_BYTES)
            jnaAttribute.pValue.getInt(0).toUInt().toLong() shouldBe VENDOR_DEFINED_VALUE
        else
            jnaAttribute.pValue.getLong(0) shouldBe VENDOR_DEFINED_VALUE
    }
}