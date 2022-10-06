package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna

import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11wrapper.main.DEFAULT_USER_PIN
import ru.rutoken.pkcs11wrapper.rule.lowlevel.jna.*

class RtExtendedFormatOnExitTest {
    @Test
    fun setLocalPin() {
        val localPin = "11223458".toByteArray()
        val anotherLocalPin = "876541129".toByteArray()
        val localUserId = 0x03L

        module.value.C_EX_SetLocalPIN(slot.value, DEFAULT_USER_PIN.toByteArray(), localPin, localUserId).shouldBeOk()
        module.value.C_EX_SetLocalPIN(slot.value, localPin, anotherLocalPin, localUserId).shouldBeOk()
    }

    companion object {
        private val module = RtModuleRule()
        private val slot = SlotRule(module)
        private val formatTokenRule = FormatTokenOnExitRule(module, slot)
        private val session = SessionRule(module, slot)

        @JvmStatic
        @get:ClassRule
        val ruleChain: TestRule =
            RuleChain.outerRule(module).around(slot).around(formatTokenRule).around(session)
    }
}