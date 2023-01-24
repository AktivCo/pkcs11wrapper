/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.attribute

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.ClassRule
import org.junit.Test
import ru.rutoken.pkcs11wrapper.rule.highlevel.RtModuleRule
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11AttributeType

class RtPkcs11AttributeFactoryTest {

    @Test
    fun makeAttribute() {
        val byteAttribute = attributeFactory.makeAttribute(RtPkcs11AttributeType.CKA_VENDOR_PIN_POLICY_MIN_LENGTH)
        byteAttribute.shouldBeInstanceOf<RtPkcs11ByteAttribute>()

        val longAttribute = attributeFactory.makeAttribute(
            RtPkcs11LongArrayAttribute::class.java,
            RtPkcs11AttributeType.CKA_VENDOR_SUPPORTED_PIN_POLICIES
        )
        longAttribute.shouldBeInstanceOf<RtPkcs11LongArrayAttribute>()
    }

    @Test
    fun makeAttributeSetLongArrayValue() {
        val value = listOf(1L)
        val attribute = attributeFactory.makeAttribute(
            RtPkcs11AttributeType.CKA_VENDOR_SUPPORTED_SECURE_MESSAGING_MODES,
            value
        )
        attribute.shouldBeInstanceOf<RtPkcs11LongArrayAttribute>()
        attribute.value shouldBe value
    }

    @Test
    fun makeAttributeSetByteValue() {
        val value: Byte = 1
        val attribute = attributeFactory.makeAttribute(
            RtPkcs11AttributeType.CKA_VENDOR_PIN_POLICY_HISTORY_DEPTH,
            value
        )
        attribute.shouldBeInstanceOf<RtPkcs11ByteAttribute>()
        attribute.value shouldBe value
    }

    @Test
    fun makeAllAttributes() {
        RtPkcs11AttributeType.values().forEach { type -> attributeFactory.makeAttribute(type) }
    }

    @Test
    fun makeAttributeWrongClass_negative() {
        shouldThrow<ClassCastException> {
            attributeFactory.makeAttribute(
                RtPkcs11ByteAttribute::class.java,
                RtPkcs11AttributeType.CKA_VENDOR_SUPPORTED_PIN_POLICIES
            )
        }
    }

    @Test
    fun makeAttributeWrongValue_negative() {
        shouldThrow<RuntimeException> {
            attributeFactory.makeAttribute(
                RtPkcs11AttributeType.CKA_VENDOR_SUPPORTED_SECURE_MESSAGING_MODES,
                "shouldBeLongArray"
            )
        }
    }

    companion object {
        @ClassRule
        @JvmField
        val rtModule = RtModuleRule()
        private val attributeFactory = rtModule.value.attributeFactory
    }
}