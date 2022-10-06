package ru.rutoken.pkcs11wrapper.main

import io.kotest.matchers.comparables.shouldBeGreaterThanOrEqualTo
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.junit.ClassRule
import org.junit.Test
import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.rtFakeModule

class ModuleTest {
    @Test
    fun waitForSlotEvent() {
        val slot = module.value.waitForSlotEvent(true)
    }

    @Test
    fun getSlotList() {
        val slots = module.value.getSlotList(false)
        slots.size shouldBeGreaterThan 0
    }

    @Test
    fun getInfo() {
        val info = module.value.info
        info.manufacturerId shouldBe "Aktiv Co.                       "
        info.libraryDescription shouldBe "Rutoken ECP PKCS #11 library    "

        val version = info.cryptokiVersion
        version.major shouldBeGreaterThanOrEqualTo 2.toByte()
        version.minor shouldBeGreaterThanOrEqualTo 20.toByte()
    }

    @Test
    fun useTwoModules() {
        rtFakeModule.initializeModule(null)

        try {
            rtFakeModule.attributeFactory shouldNotBeSameInstanceAs module.value.attributeFactory
            rtFakeModule.objectFactory shouldNotBeSameInstanceAs module.value.objectFactory
        } finally {
            rtFakeModule.finalizeModule()
        }
    }

    companion object {
        @JvmStatic
        @get:ClassRule
        val module = ModuleRule()
    }
}