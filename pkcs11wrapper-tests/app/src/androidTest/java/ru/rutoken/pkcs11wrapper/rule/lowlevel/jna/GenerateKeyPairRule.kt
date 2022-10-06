package ru.rutoken.pkcs11wrapper.rule.lowlevel.jna

import io.kotest.matchers.shouldNotBe
import org.junit.rules.ExternalResource
import ru.rutoken.pkcs11jna.Pkcs11Constants.*
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute
import ru.rutoken.pkcs11wrapper.main.CRYPTO_PRO_A_GOSTR3410_2012_256_OID
import ru.rutoken.pkcs11wrapper.main.GOSTR3411_2012_256_OID
import ru.rutoken.pkcs11wrapper.util.MutableLong

class GenerateKeyPairRule(private val module: ModuleRule, private val session: SessionRule) : ExternalResource() {
    private var _publicKey = CK_INVALID_HANDLE
    val publicKey get() = _publicKey

    private var _privateKey = CK_INVALID_HANDLE
    val privateKey get() = _privateKey

    override fun before() {
        val publicKeyTemplate = makeGostPublicKeyTemplate()
        val privateKeyTemplate = makeGostPrivateKeyTemplate()
        val mechanism = module.value.lowLevelFactory.makeMechanism().apply {
            setMechanism(CKM_GOSTR3410_KEY_PAIR_GEN, null)
        }

        val publicKeyMutable = MutableLong()
        val privateKeyMutable = MutableLong()

        module.value.C_GenerateKeyPair(
            session.value, mechanism, publicKeyTemplate, privateKeyTemplate, publicKeyMutable, privateKeyMutable
        ).shouldBeOk()

        _publicKey = publicKeyMutable.value
        _privateKey = privateKeyMutable.value

        _publicKey shouldNotBe CK_INVALID_HANDLE
        _privateKey shouldNotBe CK_INVALID_HANDLE
    }

    override fun after() {
        module.value.C_DestroyObject(session.value, _publicKey)
        module.value.C_DestroyObject(session.value, _privateKey)
    }

    private fun makeGostPublicKeyTemplate() = mutableListOf<CkAttribute>().apply {
        add(makeAttribute(CKA_CLASS, CKO_PUBLIC_KEY))
        add(makeAttribute(CKA_KEY_TYPE, CKK_GOSTR3410))
        add(makeAttribute(CKA_PRIVATE, false))
        add(makeAttribute(CKA_GOSTR3410_PARAMS, CRYPTO_PRO_A_GOSTR3410_2012_256_OID))
        add(makeAttribute(CKA_GOSTR3411_PARAMS, GOSTR3411_2012_256_OID))
        add(makeAttribute(CKA_VERIFY, true))
        add(makeAttribute(CKA_TOKEN, true))
    }

    private fun makeGostPrivateKeyTemplate() = mutableListOf<CkAttribute>().apply {
        add(makeAttribute(CKA_CLASS, CKO_PRIVATE_KEY))
        add(makeAttribute(CKA_KEY_TYPE, CKK_GOSTR3410))
        add(makeAttribute(CKA_PRIVATE, true))
        add(makeAttribute(CKA_GOSTR3410_PARAMS, CRYPTO_PRO_A_GOSTR3410_2012_256_OID))
        add(makeAttribute(CKA_GOSTR3411_PARAMS, GOSTR3411_2012_256_OID))
        add(makeAttribute(CKA_SIGN, true))
        add(makeAttribute(CKA_TOKEN, true))
    }

    private fun makeAttribute(type: Long, value: Long) = module.value.lowLevelFactory.makeAttribute()
        .apply {
            this.type = type
            this.longValue = value
        }

    private fun makeAttribute(type: Long, value: Boolean) = module.value.lowLevelFactory.makeAttribute()
        .apply {
            this.type = type
            this.booleanValue = value
        }

    private fun makeAttribute(type: Long, value: ByteArray) = module.value.lowLevelFactory.makeAttribute()
        .apply {
            this.type = type
            this.byteArrayValue = value
        }
}