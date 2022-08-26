package ru.rutoken.pkcs11wrapper.rule

import org.junit.rules.ExternalResource
import ru.rutoken.pkcs11wrapper.`object`.Pkcs11Object
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute

class CreateObjectRule(private val session: SessionRule, val template: List<Pkcs11Attribute>) : ExternalResource() {
    private lateinit var _value: Pkcs11Object
    val value get() = _value

    override fun before() {
        _value = session.value.objectManager.createObject(template)
    }

    override fun after() {
        session.value.objectManager.destroyObject(_value)
    }
}