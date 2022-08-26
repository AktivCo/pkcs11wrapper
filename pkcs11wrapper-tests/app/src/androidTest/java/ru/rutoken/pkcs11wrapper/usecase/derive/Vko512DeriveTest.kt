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
import ru.rutoken.pkcs11wrapper.rule.*
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.CKM_VKO_GOSTR3410_2012_512

private val UKM = byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8)

private val IPkcs11AttributeFactory.derivedKeyTemplate: List<Pkcs11Attribute>
    get() = makeSessionMagmaTwinSecretKeyTemplate()

class Vko512DeriveTest {
    @Test
    fun deriveKeyTc26Mechanism() {
        assumeTrue(token.value.isMechanismSupported(CKM_VKO_GOSTR3410_2012_512))

        val recipientPublicKey = recipientKeyPair.value.publicKey
            .getByteArrayAttributeValue(session.value, CKA_VALUE).byteArrayValue

        val params = CkEcdh1DeriveParams(CKD_NULL.asLong, recipientPublicKey, UKM)
        val mechanism = Pkcs11Mechanism.make(CKM_VKO_GOSTR3410_2012_512, params)

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
        private val login = LoginRule(session, CKU_USER, DEFAULT_USER_PIN)
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