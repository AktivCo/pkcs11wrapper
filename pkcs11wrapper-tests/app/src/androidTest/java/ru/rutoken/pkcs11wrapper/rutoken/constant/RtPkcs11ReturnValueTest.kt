/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.constant

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.Test
import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue

class RtPkcs11ReturnValueTest : RtPkcs11ConstantTest() {
    @Test
    fun getInstance() {
        IPkcs11ReturnValue.getInstance(
            RtPkcs11ReturnValue.CKR_CORRUPTED_MAPFILE.asLong, module.value.vendorExtensions
        ) shouldBeSameInstanceAs RtPkcs11ReturnValue.CKR_CORRUPTED_MAPFILE
    }

    @Test
    fun getInstanceUnknownValue() {
        val value = RtPkcs11ReturnValue.CKR_PIN_IN_HISTORY.asLong or 0xffff

        IPkcs11ReturnValue.getInstance(value, module.value.vendorExtensions).asLong shouldBe value
    }
}