package ru.rutoken.pkcs11wrapper.main

import ru.rutoken.pkcs11wrapper.`object`.key.Pkcs11GostPrivateKeyObject
import ru.rutoken.pkcs11wrapper.`object`.key.Pkcs11GostPublicKeyObject
import ru.rutoken.pkcs11wrapper.`object`.key.Pkcs11RsaPrivateKeyObject
import ru.rutoken.pkcs11wrapper.`object`.key.Pkcs11RsaPublicKeyObject
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute
import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute
import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType
import ru.rutoken.pkcs11wrapper.constant.IPkcs11KeyType
import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.*
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11CertificateType.CKC_X_509
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType.*
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_GOSTR3410_KEY_PAIR_GEN
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_RSA_PKCS_KEY_PAIR_GEN
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ObjectClass.*
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism
import ru.rutoken.pkcs11wrapper.rule.GenerateKeyPairRule
import ru.rutoken.pkcs11wrapper.rule.SessionRule
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyType.*
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.CKM_GOSTR3410_512_KEY_PAIR_GEN

val CRYPTO_PRO_A_GOST28147_89_OID = byteArrayOf(0x06, 0x07, 0x2A, 0x85.toByte(), 0x03, 0x02, 0x02, 0x1f, 0x01)
val CRYPTO_PRO_A_GOSTR3410_2001_OID = byteArrayOf(0x06, 0x07, 0x2A, 0x85.toByte(), 0x03, 0x02, 0x02, 0x23, 0x01)
val CRYPTO_PRO_A_GOSTR3410_2012_256_OID = CRYPTO_PRO_A_GOSTR3410_2001_OID
val CRYPTO_PRO_A_GOSTR3410_2012_512_OID =
    byteArrayOf(0x06, 0x09, 0x2A, 0x85.toByte(), 0x03, 0x07, 0x01, 0x02, 0x01, 0x02, 0x01)

@JvmField
val GOSTR3411_1994_OID = byteArrayOf(0x06, 0x07, 0x2a, 0x85.toByte(), 0x03, 0x02, 0x02, 0x1e, 0x01)
val GOSTR3411_2012_256_OID = byteArrayOf(0x06, 0x08, 0x2a, 0x85.toByte(), 0x03, 0x07, 0x01, 0x01, 0x02, 0x02)
val GOSTR3411_2012_512_OID = byteArrayOf(0x06, 0x08, 0x2a, 0x85.toByte(), 0x03, 0x07, 0x01, 0x01, 0x02, 0x03)

@JvmField
val DATA = byteArrayOf(0x01, 0x02, 0x03)
const val DEFAULT_USER_PIN = "12345678"
const val DEFAULT_ADMIN_PIN = "87654321"

val IPkcs11AttributeFactory.dataObjectAttributes: List<Pkcs11Attribute>
    get() = listOf(
        makePkcs11Attribute(CKA_CLASS, CKO_DATA),
        makePkcs11Attribute(CKA_APPLICATION, "testApplication"),
        makePkcs11Attribute(CKA_OBJECT_ID, byteArrayOf(0x01, 0x02))
    )

const val TEST_2001_PUBLIC_KEY_LABEL = "test 2001 public key"
const val TEST_2001_PRIVATE_KEY_LABEL = "test 2001 private key"
const val TEST_2012_256_PUBLIC_KEY_LABEL = "test 2012 256 public key"
const val TEST_2012_256_PRIVATE_KEY_LABEL = "test 2012 256 private key"
const val TEST_2012_512_PUBLIC_KEY_LABEL = "test 2012 512 public key"
const val TEST_2012_512_PRIVATE_KEY_LABEL = "test 2012 512 private key"
const val TEST_RSA_PUBLIC_KEY_LABEL = "test RSA public key"
const val TEST_RSA_PRIVATE_KEY_LABEL = "test RSA private key"

@JvmField
val DN = listOf(
    "CN",
    "Ivanoff",
    "C",
    "RU",
    "2.5.4.5",
    "12312312312",
    "1.2.840.113549.1.9.1",
    "ivanov@mail.ru",
    "ST",
    "Moscow"
)

@JvmField
val EXTENSIONS = listOf(
    "keyUsage",
    "digitalSignature,nonRepudiation,keyEncipherment,dataEncipherment",
    "extendedKeyUsage",
    "1.2.643.2.2.34.6,1.3.6.1.5.5.7.3.2,1.3.6.1.5.5.7.3.4"
)

fun IPkcs11AttributeFactory.makePkcs11Attribute(type: IPkcs11AttributeType, value: Any): Pkcs11Attribute =
    makeAttribute(type, value)

fun IPkcs11AttributeFactory.makeGostPublicKeyTemplate(
    keyType: IPkcs11KeyType,
    label: String,
    gost3410Params: ByteArray,
    gost3411Params: ByteArray
) = listOf(
    makePkcs11Attribute(CKA_CLASS, CKO_PUBLIC_KEY),
    makePkcs11Attribute(CKA_KEY_TYPE, keyType),
    makePkcs11Attribute(CKA_LABEL, label),
    makePkcs11Attribute(CKA_PRIVATE, false),
    Pkcs11ByteArrayAttribute(CKA_GOSTR3410_PARAMS, gost3410Params),
    Pkcs11ByteArrayAttribute(CKA_GOSTR3411_PARAMS, gost3411Params),
    makePkcs11Attribute(CKA_DERIVE, true),
    makePkcs11Attribute(CKA_TOKEN, true)
)

fun IPkcs11AttributeFactory.makeGostPrivateKeyTemplate(
    keyType: IPkcs11KeyType,
    label: String,
    gost3410Params: ByteArray,
    gost3411Params: ByteArray
) = listOf(
    makePkcs11Attribute(CKA_CLASS, CKO_PRIVATE_KEY),
    makePkcs11Attribute(CKA_KEY_TYPE, keyType),
    makePkcs11Attribute(CKA_LABEL, label),
    makePkcs11Attribute(CKA_PRIVATE, true),
    Pkcs11ByteArrayAttribute(CKA_GOSTR3410_PARAMS, gost3410Params),
    Pkcs11ByteArrayAttribute(CKA_GOSTR3411_PARAMS, gost3411Params),
    makePkcs11Attribute(CKA_DERIVE, true),
    makePkcs11Attribute(CKA_TOKEN, true)
)

fun IPkcs11AttributeFactory.makeRsaPublicKeyTemplate(modulusBits: Int, label: String) = listOf(
    makePkcs11Attribute(CKA_CLASS, CKO_PUBLIC_KEY),
    makePkcs11Attribute(CKA_KEY_TYPE, CKK_RSA),
    makePkcs11Attribute(CKA_LABEL, label),
    makePkcs11Attribute(CKA_PRIVATE, false),
    makePkcs11Attribute(CKA_ENCRYPT, true),
    makePkcs11Attribute(CKA_TOKEN, true),
    makePkcs11Attribute(CKA_MODULUS_BITS, modulusBits)
)

fun IPkcs11AttributeFactory.makeRsaPrivateKeyTemplate(label: String) = listOf(
    makePkcs11Attribute(CKA_CLASS, CKO_PRIVATE_KEY),
    makePkcs11Attribute(CKA_KEY_TYPE, CKK_RSA),
    makePkcs11Attribute(CKA_LABEL, label),
    makePkcs11Attribute(CKA_PRIVATE, true),
    makePkcs11Attribute(CKA_DECRYPT, true),
    makePkcs11Attribute(CKA_TOKEN, true)
)

fun IPkcs11AttributeFactory.makeSessionGostSecretKeyTemplate() = listOf(
    makePkcs11Attribute(CKA_CLASS, CKO_SECRET_KEY),
    makePkcs11Attribute(CKA_KEY_TYPE, CKK_GOST28147),
    makePkcs11Attribute(CKA_TOKEN, false),
    makePkcs11Attribute(CKA_PRIVATE, true),
    makePkcs11Attribute(CKA_SENSITIVE, false),
    makePkcs11Attribute(CKA_EXTRACTABLE, true)
)

fun IPkcs11AttributeFactory.makeSessionMagmaBaseSecretKeyTemplate() = mutableListOf(
    makePkcs11Attribute(CKA_CLASS, CKO_SECRET_KEY),
    makePkcs11Attribute(CKA_KEY_TYPE, CKK_MAGMA),
    makePkcs11Attribute(CKA_TOKEN, false),
    makePkcs11Attribute(CKA_PRIVATE, true),
    makePkcs11Attribute(CKA_SENSITIVE, false),
    makePkcs11Attribute(CKA_EXTRACTABLE, true),
)

fun IPkcs11AttributeFactory.makeSessionMagmaTwinSecretKeyTemplate() = listOf(
    makePkcs11Attribute(CKA_CLASS, CKO_SECRET_KEY),
    makePkcs11Attribute(CKA_KEY_TYPE, CKK_MAGMA_TWIN_KEY),
    makePkcs11Attribute(CKA_TOKEN, false),
    makePkcs11Attribute(CKA_WRAP, true),
    makePkcs11Attribute(CKA_UNWRAP, true),
    makePkcs11Attribute(CKA_PRIVATE, true),
    makePkcs11Attribute(CKA_SENSITIVE, false),
    makePkcs11Attribute(CKA_EXTRACTABLE, true)
)

fun IPkcs11AttributeFactory.makeGostR3410KeyPairRule(
    sessionRule: SessionRule,
    mechanismType: IPkcs11MechanismType,
    keyType: IPkcs11KeyType,
    paramset3410: ByteArray,
    paramset3411: ByteArray,
    publicKeyLabel: String,
    privateKeyLabel: String
) = GenerateKeyPairRule(
    Pkcs11GostPublicKeyObject::class.java,
    Pkcs11GostPrivateKeyObject::class.java,
    sessionRule,
    Pkcs11Mechanism.make(mechanismType),
    makeGostPublicKeyTemplate(keyType, publicKeyLabel, paramset3410, paramset3411),
    makeGostPrivateKeyTemplate(keyType, privateKeyLabel, paramset3410, paramset3411)
)

@JvmOverloads
fun IPkcs11AttributeFactory.makeGostR3410_2001KeyPairRule(
    sessionRule: SessionRule,
    publicKeyLabel: String = TEST_2001_PUBLIC_KEY_LABEL,
    privateKeyLabel: String = TEST_2001_PRIVATE_KEY_LABEL
) = makeGostR3410KeyPairRule(
    sessionRule,
    CKM_GOSTR3410_KEY_PAIR_GEN,
    CKK_GOSTR3410,
    CRYPTO_PRO_A_GOSTR3410_2001_OID,
    GOSTR3411_1994_OID,
    publicKeyLabel,
    privateKeyLabel
)

@JvmOverloads
fun IPkcs11AttributeFactory.makeGostR3410_2012_256KeyPairRule(
    sessionRule: SessionRule,
    publicKeyLabel: String = TEST_2012_256_PUBLIC_KEY_LABEL,
    privateKeyLabel: String = TEST_2012_256_PRIVATE_KEY_LABEL
) = makeGostR3410KeyPairRule(
    sessionRule,
    CKM_GOSTR3410_KEY_PAIR_GEN,
    CKK_GOSTR3410,
    CRYPTO_PRO_A_GOSTR3410_2012_256_OID,
    GOSTR3411_2012_256_OID,
    publicKeyLabel,
    privateKeyLabel
)

@JvmOverloads
fun IPkcs11AttributeFactory.makeGostR3410_2012_512KeyPairRule(
    sessionRule: SessionRule,
    publicKeyLabel: String = TEST_2012_512_PUBLIC_KEY_LABEL,
    privateKeyLabel: String = TEST_2012_512_PRIVATE_KEY_LABEL
) = makeGostR3410KeyPairRule(
    sessionRule,
    CKM_GOSTR3410_512_KEY_PAIR_GEN,
    CKK_GOSTR3410_512,
    CRYPTO_PRO_A_GOSTR3410_2012_512_OID,
    GOSTR3411_2012_512_OID,
    publicKeyLabel,
    privateKeyLabel
)

fun IPkcs11AttributeFactory.makeRsaKeyPairRule(
    sessionRule: SessionRule,
    modulusBits: Int = 2048,
    publicKeyLabel: String = TEST_RSA_PUBLIC_KEY_LABEL,
    privateKeyLabel: String = TEST_RSA_PRIVATE_KEY_LABEL
) = GenerateKeyPairRule(
    Pkcs11RsaPublicKeyObject::class.java,
    Pkcs11RsaPrivateKeyObject::class.java,
    sessionRule,
    Pkcs11Mechanism.make(CKM_RSA_PKCS_KEY_PAIR_GEN),
    makeRsaPublicKeyTemplate(modulusBits, publicKeyLabel),
    makeRsaPrivateKeyTemplate(privateKeyLabel)
)

fun IPkcs11AttributeFactory.makeCertificateTemplate(id: ByteArray, value: ByteArray) = listOf(
    makePkcs11Attribute(CKA_CLASS, CKO_CERTIFICATE),
    makePkcs11Attribute(CKA_CERTIFICATE_TYPE, CKC_X_509),
    makePkcs11Attribute(CKA_ID, id),
    makePkcs11Attribute(CKA_TOKEN, true),
    makePkcs11Attribute(CKA_PRIVATE, false),
    makePkcs11Attribute(CKA_VALUE, value)
)

fun Pkcs11Token.isMechanismSupported(mechanism: IPkcs11MechanismType) =
    mechanismList.any { it.asLong == mechanism.asLong }
