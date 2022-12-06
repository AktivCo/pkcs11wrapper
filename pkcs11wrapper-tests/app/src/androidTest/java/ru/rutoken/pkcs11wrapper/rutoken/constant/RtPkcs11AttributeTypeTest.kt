package ru.rutoken.pkcs11wrapper.rutoken.constant

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.Test
import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType

class RtPkcs11AttributeTypeTest : RtPkcs11ConstantTest() {
    @Test
    fun getInstance() {
        IPkcs11AttributeType.getInstance(
            RtPkcs11AttributeType.CKA_VENDOR_IV.asLong, module.value.vendorExtensions
        ) shouldBeSameInstanceAs RtPkcs11AttributeType.CKA_VENDOR_IV
    }

    @Test
    fun getInstanceUnknownValue() {
        val value = RtPkcs11AttributeType.CKA_VENDOR_USER_TYPE.asLong or 0xffff

        IPkcs11AttributeType.getInstance(value, module.value.vendorExtensions).asLong shouldBe value
    }
}