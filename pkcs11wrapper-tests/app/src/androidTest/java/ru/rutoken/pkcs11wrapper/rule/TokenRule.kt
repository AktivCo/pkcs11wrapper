package ru.rutoken.pkcs11wrapper.rule

import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import ru.rutoken.pkcs11wrapper.main.Pkcs11Token

class TokenRule(private val slot: SlotRule) : TestRule {
    private lateinit var _value: Pkcs11Token
    val value get() = _value

    override fun apply(base: Statement, description: Description) = object : Statement() {
        override fun evaluate() {
            _value = slot.value.token
            _value.slot shouldBeSameInstanceAs slot.value
            base.evaluate()
        }
    }
}