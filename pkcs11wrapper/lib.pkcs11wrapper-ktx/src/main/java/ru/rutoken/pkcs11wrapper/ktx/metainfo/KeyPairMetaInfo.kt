package ru.rutoken.pkcs11wrapper.ktx.metainfo

import ru.rutoken.pkcs11wrapper.constant.IPkcs11KeyType
import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType.*
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.*
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyType.CKK_GOSTR3410_256
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyType.CKK_GOSTR3410_512
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.*
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_GOSTR3410
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_GOSTR3410_DERIVE
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_GOSTR3410_KEY_PAIR_GEN

interface KeyPairMetaInfo {
    val generateMechanismType: IPkcs11MechanismType
    val signMechanismType: IPkcs11MechanismType
    val keyType: IPkcs11KeyType
    val isGost get() = false
}

interface GostKeyPairMetaInfo : KeyPairMetaInfo {
    val isGost2001 get() = false
    val isGost2012 get() = false
    override val isGost get() = true
    val digestMechanismType: IPkcs11MechanismType
    val signWithDigestMechanismType: IPkcs11MechanismType
    val vkoMechanismType: IPkcs11MechanismType

    /**
     * @see <a href="https://tools.ietf.org/id/draft-smyshlyaev-tls12-gost-suites-00.html">GOST Cipher Suites for TLS</a>
     */
    val kegMechanismType get() = CKM_GOST_KEG
}

object Gost2001KeyPairMetaInfo : GostKeyPairMetaInfo {
    override val isGost2001 = true
    override val digestMechanismType = CKM_GOSTR3411_94
    override val generateMechanismType = CKM_GOSTR3410_KEY_PAIR_GEN
    override val signMechanismType = CKM_GOSTR3410
    override val signWithDigestMechanismType = CKM_GOSTR3410_WITH_GOSTR3411_94
    override val keyType = CKK_GOSTR3410
    override val vkoMechanismType = CKM_GOSTR3410_DERIVE
}

object Gost2012ShortKeyPairMetaInfo : GostKeyPairMetaInfo {
    override val isGost2012 = true
    override val digestMechanismType = CKM_GOSTR3411_2012_256
    override val generateMechanismType = CKM_GOSTR3410_256_KEY_PAIR_GEN
    override val signMechanismType = CKM_GOSTR3410_256
    override val signWithDigestMechanismType = CKM_GOSTR3410_WITH_GOSTR3411_2012_256
    override val keyType = CKK_GOSTR3410_256
    override val vkoMechanismType = CKM_GOSTR3410_2012_DERIVE
}

object Gost2012LongKeyPairMetaInfo : GostKeyPairMetaInfo {
    override val isGost2012 = true
    override val digestMechanismType = CKM_GOSTR3411_2012_512
    override val generateMechanismType = CKM_GOSTR3410_512_KEY_PAIR_GEN
    override val signMechanismType = CKM_GOSTR3410_512
    override val signWithDigestMechanismType = CKM_GOSTR3410_WITH_GOSTR3411_2012_512
    override val keyType = CKK_GOSTR3410_512
    override val vkoMechanismType = CKM_VKO_GOSTR3410_2012_512
}

object RsaKeyPairMetaInfo : KeyPairMetaInfo {
    val encryptMechanismType: IPkcs11MechanismType = CKM_RSA_PKCS
    override val generateMechanismType = CKM_RSA_PKCS_KEY_PAIR_GEN
    override val signMechanismType = CKM_RSA_PKCS
    override val keyType = CKK_RSA

    val publicExponent3 = byteArrayOf(0x3)
    val publicExponent5 = byteArrayOf(0x5)
    val publicExponent17 = byteArrayOf(0x11)
    val publicExponent257 = byteArrayOf(0x01, 0x01)

    /** Default */
    val publicExponent65537 = byteArrayOf(0x01, 0x00, 0x01)
}

object EcdsaKeyPairMetaInfo : KeyPairMetaInfo {
    override val generateMechanismType = CKM_EC_KEY_PAIR_GEN
    override val signMechanismType = CKM_ECDSA
    override val keyType = CKK_EC
}

