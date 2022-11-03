package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna

import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11jna.Pkcs11Constants.CKU_SO
import ru.rutoken.pkcs11wrapper.main.DEFAULT_ADMIN_PIN
import ru.rutoken.pkcs11wrapper.rule.lowlevel.jna.*

class RtExtendedSoLoginTest {
    @Test
    fun unblockUserPin() {
        module.value.C_EX_UnblockUserPIN(session.value).shouldBeOk()
    }

    companion object {
        private val module = RtModuleRule()
        private val slot = SlotRule(module)
        private val session = SessionRule(module, slot)
        private val login = LoginRule(module, session, CKU_SO, DEFAULT_ADMIN_PIN)

        @JvmStatic
        @get:ClassRule
        val ruleChain: TestRule = RuleChain.outerRule(module).around(slot).around(session).around(login)
    }
}