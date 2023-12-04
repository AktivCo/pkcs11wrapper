/*
 * Copyright (c) 2023, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.usecase

import io.kotest.matchers.shouldBe
import org.junit.Assume.assumeTrue
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_ECDSA_SHA384
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_EC_KEY_PAIR_GEN
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType
import ru.rutoken.pkcs11wrapper.main.*
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism
import ru.rutoken.pkcs11wrapper.rule.highlevel.*

private val Pkcs11Token.isEcdsaSecp384r1Supported
    get() = isMechanismSupported(CKM_EC_KEY_PAIR_GEN) && getMechanismInfo(CKM_EC_KEY_PAIR_GEN).maxKeySize >= 384

class EcdsaSecp384r1SignVerifyTest {
    @Test
    fun signVerify() {
        with(session.value) {
            assumeTrue(token.isMechanismSupported(CKM_ECDSA_SHA384))
            val mechanism = Pkcs11Mechanism.make(CKM_ECDSA_SHA384)

            signManager.signInit(mechanism, keyPair.value.privateKey)
            val signature = signManager.sign(DATA)

            verifyManager.verifyAtOnce(DATA, signature, mechanism, keyPair.value.publicKey) shouldBe true
        }
    }

    companion object {
        private val module = ModuleRule()
        private val slot = SlotRule(module)
        private val token = TokenRule(slot)
        private val session = SessionRule(token)
        private val login = LoginRule(session, Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)
        private val keyPair =
            module.value.attributeFactory.makeEcdsaKeyPairRule(session, EC_PARAMS_SECP384R1_OID) { session ->
                session.token.isEcdsaSecp384r1Supported
            }

        @ClassRule
        @JvmField
        val ruleChain: TestRule =
            RuleChain.outerRule(module).around(slot).around(token).around(session).around(login).around(keyPair)
    }
}