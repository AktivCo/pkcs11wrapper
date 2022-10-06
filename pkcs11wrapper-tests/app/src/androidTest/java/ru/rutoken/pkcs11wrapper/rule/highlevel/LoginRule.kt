package ru.rutoken.pkcs11wrapper.rule.highlevel

import org.junit.rules.ExternalResource
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session.LoginGuard

class LoginRule(private val session: SessionRule, private val userType: Pkcs11UserType, private val pin: String) :
    ExternalResource() {
    private lateinit var _value: LoginGuard
    val value get() = _value

    override fun before() {
        _value = session.value.login(userType, pin)
    }

    override fun after() {
        _value.close()
    }
}