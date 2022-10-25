package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna

import io.kotest.matchers.shouldBe
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11jna.RtPkcs11Constants.MODE_GET_LOCAL_PIN_INFO
import ru.rutoken.pkcs11wrapper.main.DEFAULT_USER_PIN
import ru.rutoken.pkcs11wrapper.rule.lowlevel.jna.*
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelApi.PointerParameter
import ru.rutoken.pkcs11wrapper.util.Mutable

class RtExtendedFormatOnExitTest {
    @Test
    fun setLocalPinGetLocalPinInfo() {
        val localPin = "11223458".toByteArray()
        val anotherLocalPin = "876541129".toByteArray()
        val localUserId = 0x03L

        with(module.value) {
            C_EX_SetLocalPIN(slot.value, DEFAULT_USER_PIN.toByteArray(), localPin, localUserId).shouldBeOk()
            C_EX_SetLocalPIN(slot.value, localPin, anotherLocalPin, localUserId).shouldBeOk()

            val localPinInfo = lowLevelFactory.makeLocalPinInfo().apply { pinId = localUserId }
            val mutableLocalPinInfo = Mutable<PointerParameter>(JnaPointerParameterImpl(localPinInfo))
            C_EX_SlotManage(slot.value, MODE_GET_LOCAL_PIN_INFO, mutableLocalPinInfo).shouldBeOk()

            val resultLocalPinInfo = (mutableLocalPinInfo.value as JnaPointerParameterImpl).ckLocalPinInfo
            resultLocalPinInfo.minSize shouldBe MIN_PIN_LENGTH
            resultLocalPinInfo.maxRetryCount shouldBe MAX_RETRY_COUNT
        }
    }

    companion object {
        private val module = RtModuleRule()
        private val slot = SlotRule(module)
        private val formatTokenRule = FormatTokenOnExitRule(module, slot)
        private val session = SessionRule(module, slot)

        @JvmStatic
        @get:ClassRule
        val ruleChain: TestRule = RuleChain.outerRule(module).around(slot).around(formatTokenRule).around(session)
    }
}