/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.constant

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.Test

class RtPkcs11KeyDerivationFunctionTest {
    @Test
    fun getInstance() {
        val derivationFunction = RtPkcs11KeyDerivationFunction.CKD_KDF_GOSTR3411_2012_256

        RtPkcs11KeyDerivationFunction.fromValue(derivationFunction.asLong) shouldBeSameInstanceAs derivationFunction
    }

    @Test
    fun getInstanceUnknownValue() {
        val value = RtPkcs11KeyDerivationFunction.CKD_KDF_GOSTR3411_2012_256.asLong or 0xffff

        shouldThrow<IllegalArgumentException> {
            RtPkcs11KeyDerivationFunction.fromValue(value)
        }
    }
}