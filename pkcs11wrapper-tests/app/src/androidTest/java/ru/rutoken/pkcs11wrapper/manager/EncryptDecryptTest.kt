package ru.rutoken.pkcs11wrapper.manager

import io.kotest.matchers.shouldBe
import org.junit.Assume.assumeTrue
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.*
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ObjectClass.CKO_SECRET_KEY
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType.CKU_USER
import ru.rutoken.pkcs11wrapper.main.DEFAULT_USER_PIN
import ru.rutoken.pkcs11wrapper.main.generateRandomData
import ru.rutoken.pkcs11wrapper.main.isMechanismSupported
import ru.rutoken.pkcs11wrapper.main.makePkcs11Attribute
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism
import ru.rutoken.pkcs11wrapper.rule.highlevel.*
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyType.CKK_KUZNECHIK
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.CKM_KUZNECHIK_ECB
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.CKM_KUZNECHIK_KEY_GEN

private const val ECB_BLOCK_SIZE = 32

class EncryptDecryptTest {
    @Test
    fun encryptDecryptAtOnce() {
        assumeTrue(token.value.isMechanismSupported(CKM_KUZNECHIK_ECB))

        val originalData = generateRandomData(ECB_BLOCK_SIZE)
        val mechanism = Pkcs11Mechanism.make(CKM_KUZNECHIK_ECB)

        val encryptedData = session.value.encryptionManager.encryptAtOnce(originalData, mechanism, secretKey.value)
        val decryptedData = session.value.decryptionManager.decryptAtOnce(encryptedData, mechanism, secretKey.value)

        decryptedData shouldBe originalData
    }

    @Test
    fun encryptDecryptUpdate() {
        assumeTrue(token.value.isMechanismSupported(CKM_KUZNECHIK_ECB))

        val originalData = generateRandomData(ECB_BLOCK_SIZE)
        val mechanism = Pkcs11Mechanism.make(CKM_KUZNECHIK_ECB)

        val encryptedData = with(session.value.encryptionManager) {
            encryptInit(mechanism, secretKey.value)
            encryptUpdate(originalData) + encryptFinal()
        }

        val decryptedData = with(session.value.decryptionManager) {
            decryptInit(mechanism, secretKey.value)
            decryptUpdate(encryptedData) + decryptFinal()
        }

        decryptedData shouldBe originalData
    }

    companion object {
        private val module = ModuleRule()
        private val slot = SlotRule(module)
        private val token = TokenRule(slot)
        private val session = SessionRule(token)
        private val login = LoginRule(session, CKU_USER, DEFAULT_USER_PIN)

        private val SECRET_KEY_TEMPLATE = with(module.value.attributeFactory) {
            listOf(
                makePkcs11Attribute(CKA_CLASS, CKO_SECRET_KEY),
                makePkcs11Attribute(CKA_KEY_TYPE, CKK_KUZNECHIK),
                makePkcs11Attribute(CKA_ENCRYPT, true),
                makePkcs11Attribute(CKA_DECRYPT, true),
                makePkcs11Attribute(CKA_TOKEN, false)
            )
        }
        private val secretKey =
            GenerateSecretKeyRule(session, Pkcs11Mechanism.make(CKM_KUZNECHIK_KEY_GEN), SECRET_KEY_TEMPLATE)

        @ClassRule
        @JvmField
        val ruleChain: TestRule =
            RuleChain.outerRule(module).around(slot).around(token).around(session).around(login).around(secretKey)
    }
}