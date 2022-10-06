package ru.rutoken.pkcs11wrapper.usecase.derive

import org.junit.Assume.assumeTrue
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute
import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_VALUE
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType
import ru.rutoken.pkcs11wrapper.main.*
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism
import ru.rutoken.pkcs11wrapper.mechanism.parameter.CkGostR3410DeriveParams
import ru.rutoken.pkcs11wrapper.rule.highlevel.*
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyDerivationFunction.CKD_KDF_GOSTR3411_2012_256
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.CKM_GOSTR3410_12_DERIVE

private val UKM = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
private val IPkcs11AttributeFactory.derivedKeyTemplate: List<Pkcs11Attribute>
    get() = makeSessionGostSecretKeyTemplate()

class Gost2012_512DeriveTest {
    @Test
    fun deriveKey() {
        assumeTrue(token.value.isMechanismSupported(CKM_GOSTR3410_12_DERIVE))

        val recipientPublicKey = recipientKeyPair.value.publicKey
            .getByteArrayAttributeValue(session.value, CKA_VALUE).byteArrayValue

        val params = CkGostR3410DeriveParams(CKD_KDF_GOSTR3411_2012_256.asLong, recipientPublicKey, UKM)
        val mechanism = Pkcs11Mechanism.make(CKM_GOSTR3410_12_DERIVE, params)

        session.value.keyManager.deriveKey(
            mechanism,
            senderKeyPair.value.privateKey,
            attributeFactory.derivedKeyTemplate
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
        private val login = LoginRule(session, Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)
        private val senderKeyPair = attributeFactory.makeGostR3410_2012_512KeyPairRule(session)
        private val recipientKeyPair = attributeFactory.makeGostR3410_2012_512KeyPairRule(
            session,
            "$TEST_2012_512_PUBLIC_KEY_LABEL recipient",
            "$TEST_2012_512_PRIVATE_KEY_LABEL recipient"
        )

        @JvmStatic
        @get:ClassRule
        val sRuleChain: TestRule = RuleChain.outerRule(module).around(slot).around(token)
            .around(session).around(login).around(senderKeyPair).around(recipientKeyPair)
    }
}