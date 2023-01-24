/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.constant

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.Test
import ru.rutoken.pkcs11wrapper.constant.IPkcs11HardwareFeatureType

class RtPkcs11HardwareFeatureTypeTest : RtPkcs11ConstantTest() {
    @Test
    fun getInstance() {
        IPkcs11HardwareFeatureType.getInstance(
            RtPkcs11HardwareFeatureType.CKH_VENDOR_RNG.asLong, module.value.vendorExtensions
        ) shouldBeSameInstanceAs RtPkcs11HardwareFeatureType.CKH_VENDOR_RNG
    }

    @Test
    fun getInstanceUnknownValue() {
        val value = RtPkcs11HardwareFeatureType.CKH_VENDOR_PIN_POLICY.asLong or 0xffff

        IPkcs11HardwareFeatureType.getInstance(value, module.value.vendorExtensions).asLong shouldBe value
    }
}