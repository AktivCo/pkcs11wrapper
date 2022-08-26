package ru.rutoken.pkcs11wrapper.rutoken

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.kotest.matchers.shouldBe
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.ExternalResource
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import ru.rutoken.pkcs11jna.RtPkcs11Constants.*
import ru.rutoken.pkcs11wrapper.`object`.certificate.Pkcs11CertificateObject
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType
import ru.rutoken.pkcs11wrapper.main.*
import ru.rutoken.pkcs11wrapper.rule.*
import ru.rutoken.pkcs11wrapper.rutoken.GostDemoCA.getRootCertificate
import ru.rutoken.pkcs11wrapper.rutoken.GostDemoCA.issueCertificate
import ru.rutoken.pkcs11wrapper.rutoken.datatype.VendorX509Store
import ru.rutoken.pkcs11wrapper.rutoken.manager.RtPkcs11CmsManager.CrlCheckMode.OPTIONAL_CRL_CHECK

@RunWith(AndroidJUnit4::class)
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
        private val certificate = CertificateRule()

        @get:ClassRule
        @JvmStatic
        val ruleChain: TestRule = RuleChain.outerRule(module).around(slot).around(token).around(session)
            .around(login).around(keyPair).around(certificate)

        private class CertificateRule : ExternalResource() {
            private lateinit var _encoded: ByteArray
            private lateinit var _value: Pkcs11CertificateObject
            val encoded get() = _encoded
            val value get() = _value

            override fun before() {
                val csr =
                    session.value.createCsr(keyPair.value.publicKey, DN, keyPair.value.privateKey, null, EXTENSIONS)
                _encoded = issueCertificate(csr)

                _value = session.value.objectManager.createObject(
                    attributeFactory.makeCertificateTemplate(
                        "GOST R 34.10-2012 (512 bits) Certificate".toByteArray(),
                        _encoded
                    )
                ) as Pkcs11CertificateObject
            }

            override fun after() {
                session.value.objectManager.destroyObject(_value)
            }
        }
    }
}