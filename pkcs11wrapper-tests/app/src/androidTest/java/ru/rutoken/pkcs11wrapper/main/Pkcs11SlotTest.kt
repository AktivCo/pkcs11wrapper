/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.main

import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeSameInstanceAs
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.SlotRule

class Pkcs11SlotTest {
    @Test
    fun getSlotInfo() {
        slot.value.slotInfo.slotDescription shouldContain "Rutoken"
    }

    @Test
    fun getToken() {
        val token = slot.value.token
        token shouldNotBe null
        token.slot shouldBeSameInstanceAs slot.value
    }

    companion object {
        private val module = ModuleRule()
        private val slot = SlotRule(module)

        @ClassRule
        @JvmField
        val ruleChain: TestRule = RuleChain.outerRule(module).around(slot)
    }
}