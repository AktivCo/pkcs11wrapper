package ru.rutoken.pkcs11wrapper.rule.lowlevel.jna

import io.kotest.matchers.shouldNotBe
import org.junit.rules.ExternalResource
import ru.rutoken.pkcs11jna.Pkcs11Constants.*
import ru.rutoken.pkcs11wrapper.util.MutableLong

open class SessionRule(private val module: ModuleRule, private val slot: SlotRule) : ExternalResource() {
    private var _value = CK_INVALID_HANDLE
    open val value get() = _value

    override fun before() {
        val flags = CKF_SERIAL_SESSION or CKF_RW_SESSION
        val session = MutableLong()

        module.value.C_OpenSession(slot.value, flags, null, null, session).shouldBeOk()
        session.value shouldNotBe CK_INVALID_HANDLE

        _value = session.value
    }

    override fun after() {
        module.value.C_CloseSession(_value).shouldBeOk()
    }
}