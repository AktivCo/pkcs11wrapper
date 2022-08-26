package ru.rutoken.pkcs11wrapper.rutoken

import android.util.Base64
import org.bouncycastle.cert.X509CertificateHolder
import org.bouncycastle.cert.X509v3CertificateBuilder
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder
import org.bouncycastle.pkcs.PKCS10CertificationRequest
import java.math.BigInteger
import java.security.KeyFactory
import java.security.Security
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*

private const val YEARS = 1

/**
 * Expires in 2045.
 */
private const val CA_CERTIFICATE = "MIIBTzCB+wIJAMGuFHcbok4sMAwGCCqFAwcBAQMCBQAwKzELMAkGA1UEBhMCUlUx" +
        "CzAJBgNVBAMMAkNBMQ8wDQYDVQQIDAZNb3Njb3cwHhcNMTgwNjA2MTAyNzA4WhcN" +
        "NDUxMDIyMTAyNzA4WjArMQswCQYDVQQGEwJSVTELMAkGA1UEAwwCQ0ExDzANBgNV" +
        "BAgMBk1vc2NvdzBmMB8GCCqFAwcBAQEBMBMGByqFAwICIwEGCCqFAwcBAQICA0MA" +
        "BECM6iQnPgDs6K2jmUVLHf4V63xwO2j4vO2X2kNQVELu2bROK+wBaNWkTX5TW+IO" +
        "9gLZFioYMSEK2LxsIO3Zf+JeMAwGCCqFAwcBAQMCBQADQQATx6Ksy1KUuvfa2q8X" +
        "kfo3pDN1x1aGo4AmQolzEpbXvzbyMy3vk+VOqegdd8KP4E3x43zaTmHmnu/G1v20" +
        "VzwO"

private const val CA_PRIVATE_KEY = "MEgCAQAwHwYIKoUDBwEBAQEwEwYHKoUDAgIjAQYIKoUDBwEBAgIEIgQgHZQED2H6" +
        "/QSaiwT1uLTmx9S6dK48xAnrT5/xc/R0+P0="

private fun Date.addYears(years: Int) = with(Calendar.getInstance()) {
    time = this@addYears
    add(Calendar.YEAR, years)
    time
}

object GostDemoCA {
    init {
        val bcProvider = BouncyCastleProvider()
        // Remove system Bouncy Castle provider
        Security.removeProvider(bcProvider.name)
        Security.addProvider(bcProvider)
    }

    fun issueCertificate(csr: ByteArray): ByteArray {
        val caCertificateHolder = X509CertificateHolder(getRootCertificate())

        val caPrivateKeySpec = PKCS8EncodedKeySpec(Base64.decode(CA_PRIVATE_KEY, Base64.DEFAULT))
        val keyFactory = KeyFactory.getInstance("ECGOST3410-2012")
        val caPrivateKey = keyFactory.generatePrivate(caPrivateKeySpec)

        val certificationRequest = PKCS10CertificationRequest(csr)
        val notBefore = Date()
        val notAfter = notBefore.addYears(YEARS)
        val certificateBuilder = X509v3CertificateBuilder(
            caCertificateHolder.issuer,
            BigInteger(160, Random()).also { it.setBit(0) },
            notBefore,
            notAfter,
            certificationRequest.subject,
            certificationRequest.subjectPublicKeyInfo
        )

        val signer = JcaContentSignerBuilder("GOST3411-2012-256WITHECGOST3410-2012-256").build(caPrivateKey)

        return certificateBuilder.build(signer).encoded
    }

    fun getRootCertificate(): ByteArray = Base64.decode(CA_CERTIFICATE, Base64.DEFAULT)
}