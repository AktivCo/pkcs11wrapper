package ru.rutoken.pkcs11wrapper.attribute

import com.sun.jna.NativeLong
import io.kotest.matchers.shouldBe
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.RtModuleRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.SessionRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.SlotRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.TokenRule
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11AttributeType.CKA_VENDOR_CURRENT_TOKEN_INTERFACE

const val VENDOR_DEFINED_VALUE = 0x800000FFL

class Pkcs11AttributeTest {
    @Test
    fun makeVendorLowLevelAttributeFromHighLevel() {
        val attribute = attributeFactory.makeAttribute(CKA_VENDOR_CURRENT_TOKEN_INTERFACE, VENDOR_DEFINED_VALUE)
        val lowLevelAttribute = attribute.toCkAttribute(session.value.lowLevelFactory)

        lowLevelAttribute.longValue shouldBe VENDOR_DEFINED_VALUE
        lowLevelAttribute.type shouldBe CKA_VENDOR_CURRENT_TOKEN_INTERFACE.asLong
        lowLevelAttribute.valueLen shouldBe NativeLong.SIZE
    }

    @Test
    fun makeVendorHighLevelAttributeFromLowLevel() {
        val lowLevelAttribute = session.value.lowLevelFactory.makeAttribute().apply {
            type = CKA_VENDOR_CURRENT_TOKEN_INTERFACE.asLong
            longValue = VENDOR_DEFINED_VALUE
        }

        val highLevelAttribute =
            attributeFactory.makeAttribute(CKA_VENDOR_CURRENT_TOKEN_INTERFACE).apply {
                assignFromCkAttribute(lowLevelAttribute, module.value.lowLevelFactory, attributeFactory)
            }

        highLevelAttribute.type shouldBe CKA_VENDOR_CURRENT_TOKEN_INTERFACE
        highLevelAttribute.value shouldBe VENDOR_DEFINED_VALUE
    }

    companion object {
        private val module = RtModuleRule()
        private val attributeFactory = module.value.attributeFactory
        private val slot = SlotRule(module)
        private val token = TokenRule(slot)
        private val session = SessionRule(token)

        @ClassRule
        @JvmField
        val ruleChain: TestRule = RuleChain.outerRule(module).around(slot).around(token).around(session)
    }
}