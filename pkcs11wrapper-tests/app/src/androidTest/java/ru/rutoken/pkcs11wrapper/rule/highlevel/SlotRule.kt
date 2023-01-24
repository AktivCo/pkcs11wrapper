/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rule.highlevel

import io.kotest.assertions.withClue
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import ru.rutoken.pkcs11wrapper.main.Pkcs11Slot

class SlotRule(private val module: ModuleRule) : TestRule {
    private lateinit var _value: Pkcs11Slot
    val value get() = _value

    override fun apply(base: Statement, description: Description) = object : Statement() {
        override fun evaluate() {
            val slots = module.value.getSlotList(true)
            withClue("Slot not found") {
                slots.size.shouldBeGreaterThan(0)
            }
            _value = slots[0]
            _value.module shouldBeSameInstanceAs module.value
            base.evaluate()
        }
    }
}