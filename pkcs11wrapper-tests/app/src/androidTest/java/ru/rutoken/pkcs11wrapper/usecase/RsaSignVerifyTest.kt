package ru.rutoken.pkcs11wrapper.usecase

import io.kotest.matchers.shouldBe
import org.bouncycastle.asn1.DERNull
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers
import org.bouncycastle.asn1.x509.AlgorithmIdentifier
import org.bouncycastle.asn1.x509.DigestInfo
import org.junit.Assume.assumeTrue
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MaskGenerationFunction.CKG_MGF1_SHA256
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.*
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType
import ru.rutoken.pkcs11wrapper.main.DATA
import ru.rutoken.pkcs11wrapper.main.DEFAULT_USER_PIN
import ru.rutoken.pkcs11wrapper.main.makeRsaKeyPairRule
import ru.rutoken.pkcs11wrapper.main.isMechanismSupported
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism
import ru.rutoken.pkcs11wrapper.mechanism.parameter.CkRsaPkcsPssParams
import ru.rutoken.pkcs11wrapper.rule.highlevel.*

class RsaSignVerifyTest {
    @Test
    fun pkcs1PaddingSign() {
        with(session.value) {
            assumeTrue(token.isMechanismSupported(CKM_SHA256))
            assumeTrue(token.isMechanismSupported(CKM_RSA_PKCS))

            val digest = digestManager.digestAtOnce(DATA, Pkcs11Mechanism.make(CKM_SHA256))
            val digestInfo =
                DigestInfo(AlgorithmIdentifier(NISTObjectIdentifiers.id_sha256, DERNull.INSTANCE), digest).encoded
            val mechanism = Pkcs11Mechanism.make(CKM_RSA_PKCS)

            signManager.signInit(mechanism, keyPair.value.privateKey)
            val signature = signManager.sign(digestInfo)

            verifyManager.verifyAtOnce(digestInfo, signature, mechanism, keyPair.value.publicKey) shouldBe true
        }
    }

    @Test
    fun pssPaddingSign() {
        with(session.value) {
            assumeTrue(token.isMechanismSupported(CKM_SHA256))
            assumeTrue(token.isMechanismSupported(CKM_RSA_PKCS_PSS))

            val digest = digestManager.digestAtOnce(DATA, Pkcs11Mechanism.make(CKM_SHA256))
            val mechanism = Pkcs11Mechanism.make(
                CKM_RSA_PKCS_PSS,
                CkRsaPkcsPssParams(CKM_SHA256.asLong, CKG_MGF1_SHA256.asLong, digest.size.toLong())
            )

            signManager.signInit(mechanism, keyPair.value.privateKey)
            val signature = signManager.sign(digest)

            verifyManager.verifyAtOnce(digest, signature, mechanism, keyPair.value.publicKey) shouldBe true
        }
    }

    companion object {
        private val module = ModuleRule()
        private val slot = SlotRule(module)
        private val token = TokenRule(slot)
        private val session = SessionRule(token)
        private val login = LoginRule(session, Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)
        private val keyPair = module.value.attributeFactory.makeRsaKeyPairRule(session)

        @ClassRule
        @JvmField
        val ruleChain: TestRule =
            RuleChain.outerRule(module).around(slot).around(token).around(session).around(login).around(keyPair)
    }
}