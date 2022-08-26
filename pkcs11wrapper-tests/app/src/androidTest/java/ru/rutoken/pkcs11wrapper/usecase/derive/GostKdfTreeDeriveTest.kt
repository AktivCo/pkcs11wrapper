package ru.rutoken.pkcs11wrapper.usecase.derive

import org.junit.Assume.assumeTrue
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute
import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_DERIVE
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType
import ru.rutoken.pkcs11wrapper.main.*
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism
import ru.rutoken.pkcs11wrapper.mechanism.parameter.CkKdfTreeGostParams
import ru.rutoken.pkcs11wrapper.rule.*
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.CKM_KDF_TREE_GOSTR3411_2012_256
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.CKM_MAGMA_KEY_GEN
import java.util.*

private val IPkcs11AttributeFactory.baseKeyTemplate: List<Pkcs11Attribute>
    get() = makeSessionMagmaBaseSecretKeyTemplate().apply { add((makePkcs11Attribute(CKA_DERIVE, true))) }

private val IPkcs11AttributeFactory.magmaTwinKeyTemplate: List<Pkcs11Attribute>
    get() = makeSessionMagmaTwinSecretKeyTemplate()

private const val MAGMA_TWIN_KEY_SIZE = 32L * 2
private const val SEED_LENGTH = 4
private const val NUMBER_OF_ROUNDS = 1L
private val LABEL = byteArrayOf(0x26, 0xbd.toByte(), 0xb8.toByte(), 0x78)

class GostKdfTreeDeriveTest {
    @Test
    fun deriveKey() {
        with(session.value) {
            assumeTrue(token.isMechanismSupported(CKM_MAGMA_KEY_GEN))
            assumeTrue(token.isMechanismSupported(CKM_KDF_TREE_GOSTR3411_2012_256))

            val baseKey =
                keyManager.generateKey(Pkcs11Mechanism.make(CKM_MAGMA_KEY_GEN), attributeFactory.baseKeyTemplate)
            val twinKekParams = CkKdfTreeGostParams(
                LABEL,
                ByteArray(SEED_LENGTH).also { Random().nextBytes(it) },
                NUMBER_OF_ROUNDS,
                MAGMA_TWIN_KEY_SIZE,
                0
            )
            val twinKekMechanism = Pkcs11Mechanism.make(CKM_KDF_TREE_GOSTR3411_2012_256, twinKekParams)

            val twinKey = keyManager.deriveKey(twinKekMechanism, baseKey, attributeFactory.magmaTwinKeyTemplate)

            objectManager.destroyObject(twinKey)
            objectManager.destroyObject(baseKey)
        }
    }

    companion object {
        private val module = ModuleRule()
        private val slot = SlotRule(module)
        private val token = TokenRule(slot)
        private val session = SessionRule(token)
        private val login = LoginRule(session, Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)

        @JvmStatic
        @get:ClassRule
        val sRuleChain: TestRule = RuleChain.outerRule(module).around(slot).around(token).around(session).around(login)
    }
}