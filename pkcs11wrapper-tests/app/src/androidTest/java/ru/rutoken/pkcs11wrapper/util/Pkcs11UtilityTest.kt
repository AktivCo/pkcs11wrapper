/*
 * Copyright (c) 2024, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.util

import io.kotest.matchers.shouldBe
import org.junit.ClassRule
import org.junit.Test
import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule
import java.time.LocalDate

class Pkcs11UtilityTest {
    @Test
    fun dateConversionTest() {
        val originalDate = LocalDate.now()

        val ckDate = module.value.lowLevelFactory.makeDate()
        Pkcs11Utility.assignCkDate(ckDate, originalDate)

        val dateFromCkDate = Pkcs11Utility.parseDate(ckDate.year, ckDate.month, ckDate.day)

        dateFromCkDate shouldBe originalDate
    }

    companion object {
        @ClassRule
        @JvmField
        val module = ModuleRule()
    }
}