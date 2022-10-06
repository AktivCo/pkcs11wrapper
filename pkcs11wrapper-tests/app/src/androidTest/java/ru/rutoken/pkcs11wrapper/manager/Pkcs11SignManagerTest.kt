package ru.rutoken.pkcs11wrapper.manager

import io.kotest.matchers.shouldBe
import org.junit.Assume.assumeTrue
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_GOSTR3410_WITH_GOSTR3411
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType.CKU_USER
import ru.rutoken.pkcs11wrapper.main.*
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism
import ru.rutoken.pkcs11wrapper.mechanism.parameter.Pkcs11ByteArrayMechanismParams
import ru.rutoken.pkcs11wrapper.rule.highlevel.*

class Pkcs11SignManagerTest {
    @Test
    fun signAtOnce() {
        assumeTrue(token.value.isMechanismSupported(CKM_GOSTR3410_WITH_GOSTR3411))

        val signature = session.value.signManager.signAtOnce(
            DATA,
            Pkcs11Mechanism.make(CKM_GOSTR3410_WITH_GOSTR3411, Pkcs11ByteArrayMechanismParams(GOSTR3411_1994_OID)),
            keyPair.value.privateKey
        )
        signature.size shouldBe 64
    }

    companion object {
        private val module = ModuleRule()
        private val slot = SlotRule(module)
        private val token = TokenRule(slot)
        private val session = SessionRule(token)
        private val login = LoginRule(session, CKU_USER, DEFAULT_USER_PIN)
        private val keyPair = module.value.attributeFactory.makeGostR3410_2001KeyPairRule(session)

        @JvmStatic
        @get:ClassRule
        val ruleChain: TestRule =
            RuleChain.outerRule(module).around(slot).around(token).around(session).around(login).around(keyPair)
    }
}