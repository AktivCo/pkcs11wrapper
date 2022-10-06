package ru.rutoken.pkcs11wrapper.rule.highlevel

import org.junit.Assume.assumeTrue
import org.junit.rules.ExternalResource
import ru.rutoken.pkcs11wrapper.`object`.key.Pkcs11KeyObject
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute
import ru.rutoken.pkcs11wrapper.main.isMechanismSupported
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism

class GenerateSecretKeyRule(
    private val session: SessionRule,
    private val mechanism: Pkcs11Mechanism,
    private val keyTemplate: List<Pkcs11Attribute>
) : ExternalResource() {
    private lateinit var _value: Pkcs11KeyObject
    val value get() = _value

    override fun before() {
        assumeTrue(session.value.token.isMechanismSupported(mechanism.mechanismType))

        _value = session.value.keyManager.generateKey(mechanism, keyTemplate)
    }

    override fun after() {
        session.value.objectManager.destroyObject(_value)
    }
}