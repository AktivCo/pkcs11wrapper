/*
 * Copyright (c) 2023, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.usecase

import io.kotest.matchers.booleans.shouldBeFalse
import org.junit.Assume.assumeTrue
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11HardwareFeatureTypeAttribute
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11ObjectClassAttribute
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_CLASS
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_HW_FEATURE_TYPE
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ObjectClass.CKO_HW_FEATURE
import ru.rutoken.pkcs11wrapper.rule.highlevel.RtModuleRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.SlotRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.TokenRule
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11AttributeType.CKA_VENDOR_MODEL_NAME
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11HardwareFeatureType.CKH_VENDOR_TOKEN_INFO

class GettingVendorModelNameTest {
    @Test
    fun vendorModelNameTest() {
        with(token.value) {
            openSession(false).use { session ->
                val vendorTokenInfo = session.objectManager.findSingleObject(
                    listOf(
                        Pkcs11ObjectClassAttribute(CKA_CLASS, CKO_HW_FEATURE),
                        Pkcs11HardwareFeatureTypeAttribute(CKA_HW_FEATURE_TYPE, CKH_VENDOR_TOKEN_INFO)
                    )
                )
                val ckaVendorModelName = vendorTokenInfo.getStringAttributeValue(session, CKA_VENDOR_MODEL_NAME)

                assumeTrue(ckaVendorModelName.isPresent && !ckaVendorModelName.isEmpty)
                ckaVendorModelName.stringValue.isBlank().shouldBeFalse()
            }
        }
    }

    companion object {
        private val module = RtModuleRule()
        private val slot = SlotRule(module)
        private val token = TokenRule(slot)

        @ClassRule
        @JvmField
        val ruleChain: TestRule = RuleChain.outerRule(module).around(slot).around(token)
    }
}
