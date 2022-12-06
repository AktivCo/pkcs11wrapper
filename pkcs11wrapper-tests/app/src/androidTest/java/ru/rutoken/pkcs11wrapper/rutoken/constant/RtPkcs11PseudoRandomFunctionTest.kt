package ru.rutoken.pkcs11wrapper.rutoken.constant

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.Test

class RtPkcs11PseudoRandomFunctionTest {
    @Test
    fun getInstance() {
        val randomFunction = RtPkcs11PseudoRandomFunction.CKP_PKCS5_PBKD2_HMAC_GOSTR3411_2012_512

        RtPkcs11PseudoRandomFunction.fromValue(randomFunction.asLong) shouldBeSameInstanceAs randomFunction
    }

    @Test
    fun getInstanceUnknownValue() {
        val value = RtPkcs11PseudoRandomFunction.CKP_PKCS5_PBKD2_HMAC_GOSTR3411_2012_512.asLong or 0xffff

        shouldThrow<IllegalArgumentException> {
            RtPkcs11PseudoRandomFunction.fromValue(value)
        }
    }
}