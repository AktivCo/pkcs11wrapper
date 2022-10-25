package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna

import org.junit.Assume.assumeTrue
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11jna.Pkcs11Constants.CKU_USER
import ru.rutoken.pkcs11jna.RtPkcs11Constants.MODE_GET_PIN_SET_TO_BE_CHANGED
import ru.rutoken.pkcs11jna.RtPkcs11Constants.MODE_RESTORE_FACTORY_DEFAULTS
import ru.rutoken.pkcs11wrapper.main.DEFAULT_ADMIN_PIN
import ru.rutoken.pkcs11wrapper.rule.lowlevel.jna.RtModuleRule
import ru.rutoken.pkcs11wrapper.rule.lowlevel.jna.SlotRule
import ru.rutoken.pkcs11wrapper.rule.lowlevel.jna.fillWithDefaultValues
import ru.rutoken.pkcs11wrapper.rule.lowlevel.jna.shouldBeOk
import ru.rutoken.pkcs11wrapper.util.Mutable

private val DEFAULT_EMITENT_KEY = byteArrayOf(
    0x57, 0x65, 0x6c, 0x63, 0x6f, 0x6d, 0x65, 0x20, 0x74, 0x6f, 0x20,
    0x74, 0x68, 0x65, 0x20, 0x68, 0x6f, 0x74, 0x65, 0x6c, 0x20, 0x43,
    0x61, 0x6c, 0x69, 0x66, 0x6f, 0x72, 0x6e, 0x69, 0x61, 0x21
)

class RtExtendedSlotManageTest {
    @Test
    fun restoreFactoryDefaults() {
        with(module.value) {
            assumeTrue(isSupportKti(slot.value))

            val rutokenInitParam = lowLevelFactory.makeRutokenInitParam().fillWithDefaultValues()
            val params = lowLevelFactory.makeVendorRestoreFactoryDefaultsParams().apply {
                setAdminPin(DEFAULT_ADMIN_PIN.toByteArray())
                setInitParam(rutokenInitParam)
                setNewEmitentKey(DEFAULT_EMITENT_KEY)
                setNewEmitentKeyRetryCount(10)
            }

            C_EX_SlotManage(
                slot.value, MODE_RESTORE_FACTORY_DEFAULTS, Mutable(JnaPointerParameterImpl(params))
            ).shouldBeOk()
        }
    }

    @Test
    fun getPinSetToBeChanged() {
        module.value.C_EX_SlotManage(
            slot.value, MODE_GET_PIN_SET_TO_BE_CHANGED, Mutable(JnaPointerParameterImpl(CKU_USER))
        ).shouldBeOk()
    }

    companion object {
        private val module = RtModuleRule()
        private val slot = SlotRule(module)

        @JvmStatic
        @get:ClassRule
        val ruleChain: TestRule = RuleChain.outerRule(module).around(slot)
    }
}