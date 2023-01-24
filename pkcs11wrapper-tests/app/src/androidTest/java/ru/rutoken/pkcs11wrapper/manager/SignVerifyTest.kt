/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.manager

import io.kotest.matchers.shouldBe
import org.junit.Assume.assumeTrue
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType.CKU_USER
import ru.rutoken.pkcs11wrapper.main.*
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism
import ru.rutoken.pkcs11wrapper.mechanism.parameter.Pkcs11ByteArrayMechanismParams
import ru.rutoken.pkcs11wrapper.rule.highlevel.*
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.CKM_GOSTR3410_WITH_GOSTR3411_12_256

private val mechanism = Pkcs11Mechanism.make(
    CKM_GOSTR3410_WITH_GOSTR3411_12_256, Pkcs11ByteArrayMechanismParams(GOSTR3411_2012_256_OID)
)

class SignVerifyTest {
    @Test
    fun signVerifyAtOnce() {
        assumeTrue(token.value.isMechanismSupported(CKM_GOSTR3410_WITH_GOSTR3411_12_256))

        val signature = session.value.signManager.signAtOnce(DATA, mechanism, keyPair.value.privateKey)

        session.value.verifyManager.verifyAtOnce(DATA, signature, mechanism, keyPair.value.publicKey) shouldBe true
    }

    @Test
    fun signVerifyUpdateTest() {
        assumeTrue(token.value.isMechanismSupported(CKM_GOSTR3410_WITH_GOSTR3411_12_256))

        val signature = session.value.signManager.run {
            signInit(mechanism, keyPair.value.privateKey)
            signUpdate(DATA)
            signFinal()
        }

        val verifyResult = session.value.verifyManager.run {
            verifyInit(mechanism, keyPair.value.publicKey)
            verifyUpdate(DATA)
            verifyFinal(signature)
        }
        verifyResult shouldBe true
    }

    companion object {
        private val module = ModuleRule()
        private val slot = SlotRule(module)
        private val token = TokenRule(slot)
        private val session = SessionRule(token)
        private val login = LoginRule(session, CKU_USER, DEFAULT_USER_PIN)
        private val keyPair = module.value.attributeFactory.makeGostR3410_2012_256KeyPairRule(session)

        @ClassRule
        @JvmField
        val ruleChain: TestRule =
            RuleChain.outerRule(module).around(slot).around(token).around(session).around(login).around(keyPair)
    }
}