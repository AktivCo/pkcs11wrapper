package ru.rutoken.pkcs11wrapper.rutoken.constant

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.Test
import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType

class RtPkcs11MechanismTypeTest : RtPkcs11ConstantTest() {
    @Test
    fun getInstance() {
        IPkcs11MechanismType.getInstance(
            RtPkcs11MechanismType.CKM_KUZNECHIK_ECB.asLong, module.value.vendorExtensions
        ) shouldBeSameInstanceAs RtPkcs11MechanismType.CKM_KUZNECHIK_ECB
    }

    @Test
    fun getInstanceUnknownValue() {
        val value = RtPkcs11MechanismType.CKM_GOST_KEG.asLong or 0xffff

        IPkcs11MechanismType.getInstance(value, module.value.vendorExtensions).asLong shouldBe value
    }
}