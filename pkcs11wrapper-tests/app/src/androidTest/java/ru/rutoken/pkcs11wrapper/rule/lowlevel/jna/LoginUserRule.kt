package ru.rutoken.pkcs11wrapper.rule.lowlevel.jna

import org.junit.rules.ExternalResource
import ru.rutoken.pkcs11jna.Pkcs11Constants.CKU_USER
import ru.rutoken.pkcs11wrapper.main.DEFAULT_USER_PIN

class LoginUserRule(private val module: ModuleRule, private val session: SessionRule) : ExternalResource() {
    override fun before() {
        module.value.C_Login(session.value, CKU_USER, DEFAULT_USER_PIN.toByteArray()).shouldBeOk()
    }

    override fun after() {
        module.value.C_Logout(session.value).shouldBeOk()
    }
}