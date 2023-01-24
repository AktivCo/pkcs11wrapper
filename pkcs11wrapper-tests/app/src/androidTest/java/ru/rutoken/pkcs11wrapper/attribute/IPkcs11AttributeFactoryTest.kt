/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.attribute

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.ClassRule
import org.junit.Test
import ru.rutoken.pkcs11wrapper.attribute.longvalue.*
import ru.rutoken.pkcs11wrapper.constant.AttributeLongValueSupplier
import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType
import ru.rutoken.pkcs11wrapper.constant.standard.*
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11Date
import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule

class IPkcs11AttributeFactoryTest {
    @Test
    fun makeAttribute() {
        val attribute = factory.makeAttribute(Pkcs11AttributeType.CKA_CLASS)
        attribute.shouldBeInstanceOf<Pkcs11ObjectClassAttribute>()

        val longAttribute = factory.makeAttribute(Pkcs11LongAttribute::class.java, Pkcs11AttributeType.CKA_CLASS)
        longAttribute.shouldBeInstanceOf<Pkcs11ObjectClassAttribute>()

        val classAttribute =
            factory.makeAttribute(Pkcs11ObjectClassAttribute::class.java, Pkcs11AttributeType.CKA_CLASS)
        classAttribute.shouldBeInstanceOf<Pkcs11ObjectClassAttribute>()
    }

    @Test
    fun makeAttributeSetBooleanValue() {
        val value = false
        val attribute = factory.makeAttribute(Pkcs11AttributeType.CKA_TOKEN, value)
        attribute.shouldBeInstanceOf<Pkcs11BooleanAttribute>()
        attribute.value shouldBe value
    }

    @Test
    fun makeAttributeSetStringValue() {
        val value = "test"
        val attribute = factory.makeAttribute(Pkcs11AttributeType.CKA_LABEL, value)
        attribute.shouldBeInstanceOf<Pkcs11StringAttribute>()
        attribute.value shouldBe value
    }

    @Test
    fun makeAttributeSetIntValue() {
        val value = 1
        val attribute = factory.makeAttribute(Pkcs11AttributeType.CKA_CERTIFICATE_CATEGORY, value)
        attribute.shouldBeInstanceOf<Pkcs11LongAttribute>()
        attribute.value shouldBe value.toLong()
    }

    @Test
    fun makeAttributeSetLongValue() {
        val value = 1L
        val attribute = factory.makeAttribute(Pkcs11AttributeType.CKA_CERTIFICATE_CATEGORY, value)
        attribute.shouldBeInstanceOf<Pkcs11LongAttribute>()
        attribute.value shouldBe value
    }

    @Test
    fun makeAttributeSetByteArrayValue() {
        val value = byteArrayOf(0x01, 0x02, 0x03)
        val attribute = factory.makeAttribute(Pkcs11AttributeType.CKA_VALUE, value)
        attribute.shouldBeInstanceOf<Pkcs11ByteArrayAttribute>()
        attribute.value shouldBe value
    }

    @Test
    fun makeAttributeSetDateValue() {
        val value = Pkcs11Date(
            module.value.lowLevelFactory.makeDate().apply {
                year = "2023".toByteArray()
                month = "01".toByteArray()
                day = "17".toByteArray()
            }
        )
        val attribute = factory.makeAttribute(Pkcs11AttributeType.CKA_START_DATE, value)
        attribute.shouldBeInstanceOf<Pkcs11DateAttribute>()
        attribute.value shouldBeEqualToComparingFields value
    }

    @Test
    fun makeAttributeSetEnumValue() {
        testMakeAttributeSetEnumValue<Pkcs11ObjectClassAttribute>(
            Pkcs11AttributeType.CKA_CLASS, Pkcs11ObjectClass.CKO_PRIVATE_KEY
        )

        testMakeAttributeSetEnumValue<Pkcs11CertificateTypeAttribute>(
            Pkcs11AttributeType.CKA_CERTIFICATE_TYPE, Pkcs11CertificateType.CKC_X_509
        )

        testMakeAttributeSetEnumValue<Pkcs11MechanismTypeAttribute>(
            Pkcs11AttributeType.CKA_NAME_HASH_ALGORITHM, Pkcs11MechanismType.CKM_SHA256,
        )

        testMakeAttributeSetEnumValue<Pkcs11KeyTypeAttribute>(
            Pkcs11AttributeType.CKA_KEY_TYPE, Pkcs11KeyType.CKK_GOSTR3410
        )
    }

    @Test
    fun makeAttributeWrongClass_negative() {
        shouldThrow<ClassCastException> {
            factory.makeAttribute(Pkcs11ObjectClassAttribute::class.java, Pkcs11AttributeType.CKA_KEY_TYPE)
        }
    }

    @Test
    fun makeAllAttributes() {
        Pkcs11AttributeType.values().forEach { type -> factory.makeAttribute(type) }
    }

    private inline fun <reified T : Any> testMakeAttributeSetEnumValue(
        type: IPkcs11AttributeType,
        value: AttributeLongValueSupplier
    ) {
        factory.makeAttribute(type, value).let { attribute ->
            attribute.shouldBeInstanceOf<T>()
            attribute.value shouldBe value.asLong
        }
    }

    companion object {
        @ClassRule
        @JvmField
        val module = ModuleRule()

        private val factory = module.value.attributeFactory
    }
}