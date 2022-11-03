package ru.rutoken.pkcs11wrapper.rutoken

import io.kotest.matchers.shouldBe
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType
import ru.rutoken.pkcs11wrapper.main.DEFAULT_USER_PIN
import ru.rutoken.pkcs11wrapper.main.DN
import ru.rutoken.pkcs11wrapper.main.EXTENSIONS
import ru.rutoken.pkcs11wrapper.main.makeGostR3410_2012_256KeyPairRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.*
import ru.rutoken.pkcs11wrapper.rutoken.GostDemoCA.issueCertificate
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11ReturnValue.CKR_CORRUPTED_MAPFILE

class RtExtendedTest {
    @Test
    fun getTokenInfoExtended() {
        val info = token.value.tokenInfoExtended
    }

    @Test
    fun createCsr() {
        val csr = session.value.createCsr(keyPair.value.publicKey, DN, keyPair.value.privateKey, null, EXTENSIONS)
        val encodedCertificate = issueCertificate(csr)
    }

    @Test
    fun vendorReturnValue() {
        val value = IPkcs11ReturnValue.getInstance(CKR_CORRUPTED_MAPFILE.asLong, module.value.vendorExtensions)
        value shouldBe CKR_CORRUPTED_MAPFILE
    }

    @Test
    fun getCertificateInfoText() {
        val certificateInfo = session.value.getCertificateInfo(certificate.value)
        certificateInfo.isNullOrEmpty() shouldBe false
    }

    companion object {
        private val module = RtModuleRule()
        private val attributeFactory = module.value.attributeFactory
        private val slot = SlotRule(module)
        private val token = RtTokenRule(slot)
        private val session = RtSessionRule(token)
        private val login = LoginRule(session, Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)
        private val keyPair = attributeFactory.makeGostR3410_2012_256KeyPairRule(session)
        private val certificate =
            CreateGostCertificateRule(session, keyPair, "GOST R 34.10-2012 (256 bits) Certificate")

        @get:ClassRule
        @JvmStatic
        val ruleChain: TestRule = RuleChain.outerRule(module).around(slot).around(token).around(session)
            .around(login).around(keyPair).around(certificate)
    }
}