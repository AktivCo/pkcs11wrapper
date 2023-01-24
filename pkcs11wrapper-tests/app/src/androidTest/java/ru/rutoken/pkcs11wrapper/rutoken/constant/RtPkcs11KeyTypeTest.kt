/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.constant

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.Test
import ru.rutoken.pkcs11wrapper.constant.IPkcs11KeyType

class RtPkcs11KeyTypeTest : RtPkcs11ConstantTest() {
    @Test
    fun getInstance() {
        IPkcs11KeyType.getInstance(
            RtPkcs11KeyType.CKK_KUZNECHIK.asLong, module.value.vendorExtensions
        ) shouldBeSameInstanceAs RtPkcs11KeyType.CKK_KUZNECHIK
    }

    @Test
    fun getInstanceUnknownValue() {
        val value = RtPkcs11KeyType.CKK_MAGMA_TWIN_KEY.asLong or 0xffff

        IPkcs11KeyType.getInstance(value, module.value.vendorExtensions).asLong shouldBe value
    }
}