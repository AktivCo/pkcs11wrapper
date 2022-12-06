package ru.rutoken.pkcs11wrapper.rutoken.constant

import org.junit.ClassRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.RtModuleRule

open class RtPkcs11ConstantTest {
    companion object {
        @get:ClassRule
        @JvmStatic
        val module = RtModuleRule()
    }
}