/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna

import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11jna.Pkcs11Constants.CKU_SO
import ru.rutoken.pkcs11jna.Pkcs11Constants.CKU_USER
import ru.rutoken.pkcs11jna.RtPkcs11Constants.MODE_CHANGE_DEFAULT_PIN
import ru.rutoken.pkcs11jna.RtPkcs11Constants.MODE_RESET_PIN_TO_DEFAULT
import ru.rutoken.pkcs11wrapper.main.DEFAULT_ADMIN_PIN
import ru.rutoken.pkcs11wrapper.main.DEFAULT_USER_PIN
import ru.rutoken.pkcs11wrapper.rule.lowlevel.jna.*

class RtExtendedSoLoginTest {
    @Test
    fun unblockUserPin() {
        module.value.C_EX_UnblockUserPIN(session.value).shouldBeOk()
    }

    @Test
    fun tokenManage() {
        with(module.value) {
            C_EX_TokenManage(session.value, MODE_RESET_PIN_TO_DEFAULT, JnaPointerParameterImpl(CKU_USER)).shouldBeOk()

            val pinParams = lowLevelFactory.makeVendorPinParams().apply {
                setUserType(CKU_USER)
                setPin(DEFAULT_USER_PIN.toByteArray())
            }
            C_EX_TokenManage(session.value, MODE_CHANGE_DEFAULT_PIN, JnaPointerParameterImpl(pinParams)).shouldBeOk()
        }
    }

    companion object {
        private val module = RtModuleRule()
        private val slot = SlotRule(module)
        private val session = SessionRule(module, slot)
        private val login = LoginRule(module, session, CKU_SO, DEFAULT_ADMIN_PIN)

        @ClassRule
        @JvmField
        val ruleChain: TestRule = RuleChain.outerRule(module).around(slot).around(session).around(login)
    }
}