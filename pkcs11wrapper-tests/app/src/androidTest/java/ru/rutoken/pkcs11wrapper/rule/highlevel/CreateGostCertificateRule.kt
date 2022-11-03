package ru.rutoken.pkcs11wrapper.rule.highlevel

import org.junit.rules.ExternalResource
import ru.rutoken.pkcs11wrapper.`object`.certificate.Pkcs11CertificateObject
import ru.rutoken.pkcs11wrapper.`object`.key.Pkcs11GostPrivateKeyObject
import ru.rutoken.pkcs11wrapper.`object`.key.Pkcs11GostPublicKeyObject
import ru.rutoken.pkcs11wrapper.main.DN
import ru.rutoken.pkcs11wrapper.main.EXTENSIONS
import ru.rutoken.pkcs11wrapper.main.makeCertificateTemplate
import ru.rutoken.pkcs11wrapper.rutoken.GostDemoCA

class CreateGostCertificateRule(
    private val session: RtSessionRule,
    private val keyPair: GenerateKeyPairRule<Pkcs11GostPublicKeyObject, Pkcs11GostPrivateKeyObject>,
    private val certificateId: String
) : ExternalResource() {
    private lateinit var _encoded: ByteArray
    private lateinit var _value: Pkcs11CertificateObject
    val encoded get() = _encoded
    val value get() = _value

    override fun before() {
        with(session.value) {
            val csr = createCsr(keyPair.value.publicKey, DN, keyPair.value.privateKey, null, EXTENSIONS)
            _encoded = GostDemoCA.issueCertificate(csr)

            _value = objectManager.createObject(
                attributeFactory.makeCertificateTemplate(certificateId.toByteArray(), _encoded)
            ) as Pkcs11CertificateObject
        }
    }

    override fun after() {
        session.value.objectManager.destroyObject(_value)
    }
}