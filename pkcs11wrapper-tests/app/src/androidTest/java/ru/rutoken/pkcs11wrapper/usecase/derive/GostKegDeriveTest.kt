/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.usecase.derive

import org.junit.Assume.assumeTrue
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute
import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_VALUE
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyDerivationFunction.CKD_NULL
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType.CKU_USER
import ru.rutoken.pkcs11wrapper.main.*
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism
import ru.rutoken.pkcs11wrapper.mechanism.parameter.CkEcdh1DeriveParams
import ru.rutoken.pkcs11wrapper.rule.highlevel.*
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.CKM_GOST_KEG

/** Must be 24 bytes long for 256 key pair and 16 bytes long for 512 key pair */
private val UKM = ByteArray(24) { index -> (index + 1).toByte() }

private val IPkcs11AttributeFactory.twinKeyTemplate: List<Pkcs11Attribute>
    get() = makeSessionMagmaTwinSecretKeyTemplate()

class GostKegDeriveTest {
    @Test
    fun deriveKeyTc26Mechanism() {
        assumeTrue(token.value.isMechanismSupported(CKM_GOST_KEG))

        val recipientPublicKey = recipientKeyPair.value.publicKey
            .getByteArrayAttributeValue(session.value, CKA_VALUE).byteArrayValue

        val twinKekParams = CkEcdh1DeriveParams(CKD_NULL.asLong, recipientPublicKey, UKM)
        val twinKekMechanism = Pkcs11Mechanism.make(CKM_GOST_KEG, twinKekParams)

        session.value.keyManager.deriveKey(
            twinKekMechanism,
            senderKeyPair.value.privateKey,
            attributeFactory.twinKeyTemplate
        ).also {
            session.value.objectManager.destroyObject(it)
        }
    }

    companion object {
        private val module = ModuleRule()
        private val attributeFactory = module.value.attributeFactory
        private val slot = SlotRule(module)
        private val token = TokenRule(slot)
        private val session = SessionRule(token)
        private val login = LoginRule(session, CKU_USER, DEFAULT_USER_PIN)
        private val senderKeyPair = attributeFactory.makeGostR3410_2012_256KeyPairRule(session)
        private val recipientKeyPair = attributeFactory.makeGostR3410_2012_256KeyPairRule(
            session,
            "$TEST_2012_256_PUBLIC_KEY_LABEL recipient",
            "$TEST_2012_256_PRIVATE_KEY_LABEL recipient"
        )

        @ClassRule
        @JvmField
        val sRuleChain: TestRule = RuleChain.outerRule(module).around(slot).around(token)
            .around(session).around(login).around(senderKeyPair).around(recipientKeyPair)
    }
}