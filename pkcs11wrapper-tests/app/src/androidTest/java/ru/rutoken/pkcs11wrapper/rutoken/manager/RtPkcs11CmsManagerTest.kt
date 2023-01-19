package ru.rutoken.pkcs11wrapper.rutoken.manager

import io.kotest.matchers.shouldBe
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11jna.RtPkcs11Constants.*
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType
import ru.rutoken.pkcs11wrapper.main.DATA
import ru.rutoken.pkcs11wrapper.main.DEFAULT_USER_PIN
import ru.rutoken.pkcs11wrapper.main.makeGostR3410_2012_512KeyPairRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.*
import ru.rutoken.pkcs11wrapper.rutoken.GostDemoCA.getRootCertificate
import ru.rutoken.pkcs11wrapper.rutoken.datatype.VendorX509Store
import ru.rutoken.pkcs11wrapper.rutoken.manager.RtPkcs11CmsManager.CrlCheckMode.OPTIONAL_CRL_CHECK

class RtPkcs11CmsManagerTest {
    @Test
    fun signVerifyAttached() = signVerify(attached = true, verifySignatureOnly = false)

    @Test
    fun signVerifyAttachedSignatureOnly() = signVerify(attached = true, verifySignatureOnly = true)

    @Test
    fun signVerifyDetached() = signVerify(attached = false, verifySignatureOnly = false)

    @Test
    fun signVerifyDetachedSignatureOnly() = signVerify(attached = false, verifySignatureOnly = true)

    private fun signVerify(attached: Boolean, verifySignatureOnly: Boolean) = with(session.value) {
        val cms = cmsManager.sign(
            DATA, certificate.value, keyPair.value.privateKey, null,
            if (attached) 0L else PKCS7_DETACHED_SIGNATURE
        )

        val signerCertificates = listOf(certificate.encoded)

        val store = if (verifySignatureOnly)
            null
        else
            VendorX509Store(listOf(getRootCertificate()), signerCertificates, null)

        val flags = if (verifySignatureOnly) CKF_VENDOR_CHECK_SIGNATURE_ONLY else CKF_VENDOR_ALLOW_PARTIAL_CHAINS

        val chain = if (attached) {
            val result = cmsManager.requireVerifyAttachedAtOnce(cms, store, OPTIONAL_CRL_CHECK, flags)

            result.signedData shouldBe DATA
            result.signerCertificates
        } else {
            cmsManager.requireVerifyDetachedAtOnce(cms, DATA, store, OPTIONAL_CRL_CHECK, flags)
        }

        // does not always equals signerCertificates in the wild
        chain shouldBe if (verifySignatureOnly) null else signerCertificates
    }

    companion object {
        private val module = RtModuleRule()
        private val attributeFactory = module.value.attributeFactory
        private val slot = SlotRule(module)
        private val token = TokenRule(slot)
        private val session = RtSessionRule(token)
        private val login = LoginRule(session, Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN)
        private val keyPair = attributeFactory.makeGostR3410_2012_512KeyPairRule(session)
        private val certificate =
            CreateGostCertificateRule(session, keyPair, "GOST R 34.10-2012 (512 bits) Certificate")

        @ClassRule
        @JvmField
        val ruleChain: TestRule = RuleChain.outerRule(module).around(slot).around(token).around(session)
            .around(login).around(keyPair).around(certificate)
    }
}