package ru.rutoken.pkcs11wrapper.rule.highlevel

import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import ru.rutoken.pkcs11wrapper.main.Pkcs11Token

open class TokenRule(protected val slot: SlotRule) : TestRule {
    protected lateinit var _value: Pkcs11Token
    open val value get() = _value

    override fun apply(base: Statement, description: Description) = object : Statement() {
        override fun evaluate() {
            _value = slot.value.token
            _value.slot shouldBeSameInstanceAs slot.value
            base.evaluate()
        }
    }
}