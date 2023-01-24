/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.ktx.metainfo

import ru.rutoken.pkcs11wrapper.constant.IPkcs11KeyType
import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType.CKK_GOST28147
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.*
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyType.*
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.*

interface SecretKeyMetaInfo {
    val cfbEncryptMechanismType: IPkcs11MechanismType
    val ecbEncryptMechanismType: IPkcs11MechanismType

    val wrapMechanismType: IPkcs11MechanismType
    val generateMechanismType: IPkcs11MechanismType
    val macMechanismType: IPkcs11MechanismType

    val keyType: IPkcs11KeyType
    val isGost: Boolean
}

interface GostSecretKeyMetaInfo : SecretKeyMetaInfo {
    val isGost2015: Boolean
    val blockLength: Int
    val ivLength: Int

    /** Digest on a key */
    val hmacGost94MechanismType get() = CKM_GOSTR3411_94_HMAC
    val hmac256Gost2012MechanismType get() = CKM_GOSTR3411_2012_256_HMAC
    val hmac512Gost2012MechanismType get() = CKM_GOSTR3411_2012_512_HMAC

    val kdfHmacMechanismType get() = CKM_KDF_HMAC3411_2012_256
    val kdfTreeMechanismType get() = CKM_KDF_TREE_GOSTR3411_2012_256

    override val isGost get() = true
}

object Gost28147SecretKeyMetaInfo : GostSecretKeyMetaInfo {
    override val cfbEncryptMechanismType = CKM_GOST28147
    override val ecbEncryptMechanismType = CKM_GOST28147_ECB

    override val wrapMechanismType = CKM_GOST28147_KEY_WRAP
    override val generateMechanismType = CKM_GOST28147_KEY_GEN
    override val macMechanismType = CKM_GOST28147_MAC

    override val keyType = CKK_GOST28147
    override val isGost2015 = false
    override val blockLength = 8
    override val ivLength = blockLength
}

interface Gost2015SecretKeyMetaInfo : GostSecretKeyMetaInfo {
    override val isGost2015 get() = true
    val mgmEncryptMechanismType: IPkcs11MechanismType
    val ctrAcpkmEncryptMechanismType: IPkcs11MechanismType
    val twinKeyType: IPkcs11KeyType
}

object MagmaSecretKeyMetaInfo : Gost2015SecretKeyMetaInfo {
    override val cfbEncryptMechanismType = TODO("pkcs11 does not support")
    override val ecbEncryptMechanismType = CKM_MAGMA_ECB
    override val mgmEncryptMechanismType = CKM_MAGMA_MGM
    override val ctrAcpkmEncryptMechanismType = CKM_MAGMA_CTR_ACPKM

    override val wrapMechanismType = CKM_MAGMA_KEXP_15_WRAP
    override val generateMechanismType = CKM_MAGMA_KEY_GEN
    override val macMechanismType = CKM_MAGMA_MAC

    override val keyType = CKK_MAGMA
    override val twinKeyType = CKK_MAGMA_TWIN_KEY
    override val blockLength = 8
    override val ivLength = blockLength / 2
}

object KuznechikSecretKeyMetaInfo : Gost2015SecretKeyMetaInfo {
    override val cfbEncryptMechanismType = TODO("pkcs11 does not support")
    override val ecbEncryptMechanismType = CKM_KUZNECHIK_ECB
    override val mgmEncryptMechanismType = CKM_KUZNECHIK_MGM
    override val ctrAcpkmEncryptMechanismType = CKM_KUZNECHIK_CTR_ACPKM

    override val wrapMechanismType = CKM_KUZNECHIK_KEXP_15_WRAP
    override val generateMechanismType = CKM_KUZNECHIK_KEY_GEN
    override val macMechanismType = CKM_KUZNECHIK_MAC

    override val keyType = CKK_KUZNECHIK
    override val twinKeyType = CKK_KUZNECHIK_TWIN_KEY
    override val blockLength = 16
    override val ivLength = blockLength / 2
}