package ru.rutoken.pkcs11wrapper.main

import io.kotest.matchers.shouldBe
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11SessionState
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType
import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.SessionRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.SlotRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.TokenRule

class Pkcs11SessionTest {
    @Test
    fun getSessionInfo() {
        session.value.sessionInfo.run {
            isRwSession shouldBe true
            isSerialSession shouldBe true
            state shouldBe Pkcs11SessionState.CKS_RW_PUBLIC_SESSION
        }
    }

    @Test
    fun loginUser() {
        loginAsUser().use {
            session.value.sessionInfo.state shouldBe Pkcs11SessionState.CKS_RW_USER_FUNCTIONS
        }
        session.value.sessionInfo.state shouldBe Pkcs11SessionState.CKS_RW_PUBLIC_SESSION
    }

    @Test
    fun loginAdmin() {
        loginAsAdmin().use {
            session.value.sessionInfo.state shouldBe Pkcs11SessionState.CKS_RW_SO_FUNCTIONS
        }
        session.value.sessionInfo.state shouldBe Pkcs11SessionState.CKS_RW_PUBLIC_SESSION
    }

    @Test
    fun setPin() {
        loginAsUser().use { session.value.setPin(DEFAULT_USER_PIN, DEFAULT_USER_PIN) }
    }

    @Test
    fun initPin() {
        loginAsAdmin().use { session.value.initPin(DEFAULT_USER_PIN) }
    }

    @Test
    fun getOperationState() {
        try {
            session.value.operationState
        } catch (e: Pkcs11Exception) {
            if (Pkcs11ReturnValue.CKR_FUNCTION_NOT_SUPPORTED != e.code)
                throw e
        }
    }

    private fun loginAsUser() =
        session.value.login(Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)

    private fun loginAsAdmin() =
        session.value.login(Pkcs11UserType.CKU_SO, DEFAULT_ADMIN_PIN)

    companion object {
        private val module = ModuleRule()
        private val slot = SlotRule(module)
        private val token = TokenRule(slot)
        private val session = SessionRule(token)

        @JvmField
        @ClassRule
        val ruleChain: TestRule = RuleChain.outerRule(module).around(slot).around(token).around(session)
    }
}