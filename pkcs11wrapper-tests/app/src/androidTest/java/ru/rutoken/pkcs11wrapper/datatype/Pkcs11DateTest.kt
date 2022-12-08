package ru.rutoken.pkcs11wrapper.datatype

import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import org.junit.ClassRule
import org.junit.Test
import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule
import ru.rutoken.pkcs11wrapper.util.Pkcs11Utility

class Pkcs11DateTest {
    @Test
    fun testPkcs11Date() {
        val yearAsArray = "2021".toByteArray()
        val monthAsArray = "12".toByteArray()
        val dayAsArray = "08".toByteArray()

        val ckDate = module.value.lowLevelFactory.makeDate().apply {
            year = yearAsArray
            month = monthAsArray
            day = dayAsArray
        }

        val pkcs11Date = Pkcs11Date(ckDate)
        val date = Pkcs11Utility.parseDate(yearAsArray, monthAsArray, dayAsArray)

        pkcs11Date.date shouldBeEqualToComparingFields date
        pkcs11Date.toCkDate(module.value.lowLevelFactory) shouldBeEqualToComparingFields ckDate
    }

    companion object {
        @get:ClassRule
        @JvmStatic
        val module = ModuleRule()
    }
}