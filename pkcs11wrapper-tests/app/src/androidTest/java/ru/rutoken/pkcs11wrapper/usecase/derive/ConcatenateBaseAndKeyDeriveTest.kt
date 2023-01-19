package ru.rutoken.pkcs11wrapper.usecase.derive

import org.junit.Assume.assumeTrue
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute
import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.*
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_CONCATENATE_BASE_AND_KEY
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType
import ru.rutoken.pkcs11wrapper.main.*
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism
import ru.rutoken.pkcs11wrapper.mechanism.parameter.Pkcs11LongMechanismParams
import ru.rutoken.pkcs11wrapper.rule.highlevel.*
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.CKM_MAGMA_KEY_GEN

private val IPkcs11AttributeFactory.macKeyTemplate: List<Pkcs11Attribute>
    get() = makeSessionMagmaBaseSecretKeyTemplate().apply { add((makePkcs11Attribute(CKA_DERIVE, true))) }

private val IPkcs11AttributeFactory.encryptionKeyTemplate: List<Pkcs11Attribute>
    get() = makeSessionMagmaBaseSecretKeyTemplate().apply {
        add(makePkcs11Attribute(CKA_ENCRYPT, true))
        add(makePkcs11Attribute(CKA_DECRYPT, true))
    }

private val IPkcs11AttributeFactory.magmaTwinKeyTemplate: List<Pkcs11Attribute>
    get() = makeSessionMagmaTwinSecretKeyTemplate()

class ConcatenateBaseAndKeyDeriveTest {
    @Test
    fun deriveKey() {
        with(session.value) {
            assumeTrue(token.isMechanismSupported(CKM_MAGMA_KEY_GEN))
            assumeTrue(token.isMechanismSupported(CKM_CONCATENATE_BASE_AND_KEY))

            val macKey =
                keyManager.generateKey(Pkcs11Mechanism.make(CKM_MAGMA_KEY_GEN), attributeFactory.macKeyTemplate)
            val encKey = keyManager.generateKey(
                Pkcs11Mechanism.make(CKM_MAGMA_KEY_GEN),
                attributeFactory.encryptionKeyTemplate
            )

            val twinKekParams = Pkcs11LongMechanismParams(encKey.handle)
            val twinKekMechanism = Pkcs11Mechanism.make(CKM_CONCATENATE_BASE_AND_KEY, twinKekParams)

            val twinKek = keyManager.deriveKey(twinKekMechanism, macKey, attributeFactory.magmaTwinKeyTemplate)

            objectManager.destroyObject(twinKek)
            objectManager.destroyObject(macKey)
            objectManager.destroyObject(encKey)
        }
    }

    companion object {
        private val module = ModuleRule()
        private val slot = SlotRule(module)
        private val token = TokenRule(slot)
        private val session = SessionRule(token)
        private val login = LoginRule(session, Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)

        @ClassRule
        @JvmField
        val sRuleChain: TestRule = RuleChain.outerRule(module).around(slot).around(token).around(session).around(login)
    }
}