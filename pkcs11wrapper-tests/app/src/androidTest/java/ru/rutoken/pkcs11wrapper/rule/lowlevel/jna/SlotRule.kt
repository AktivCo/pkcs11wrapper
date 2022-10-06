package ru.rutoken.pkcs11wrapper.rule.lowlevel.jna

import io.kotest.assertions.withClue
import io.kotest.matchers.longs.shouldBeGreaterThan
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import ru.rutoken.pkcs11jna.Pkcs11Constants.CK_TRUE
import ru.rutoken.pkcs11wrapper.util.MutableLong

class SlotRule(private val module: ModuleRule) : TestRule {
    private var _value = -1L
    val value get() = _value

    override fun apply(base: Statement, description: Description) = object : Statement() {
        override fun evaluate() {
            val slotCount = MutableLong()

            module.value.C_GetSlotList(CK_TRUE, null, slotCount).shouldBeOk()
            withClue("Slot not found") {
                slotCount.value shouldBeGreaterThan 0
            }

            val slots = LongArray(slotCount.value.toInt())

            module.value.C_GetSlotList(CK_TRUE, slots, slotCount).shouldBeOk()
            withClue("Slot not found") {
                slotCount.value shouldBeGreaterThan 0
            }

            _value = slots[0]
            base.evaluate()
        }
    }
}