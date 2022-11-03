package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna

import io.kotest.matchers.shouldBe
import org.junit.Test
import ru.rutoken.pkcs11wrapper.main.DEFAULT_ADMIN_PIN
import ru.rutoken.pkcs11wrapper.main.DEFAULT_USER_PIN
import ru.rutoken.pkcs11wrapper.rule.highlevel.RtModuleRule
import ru.rutoken.pkcs11wrapper.rutoken.datatype.RutokenInitParam
import ru.rutoken.pkcs11wrapper.rutoken.datatype.RutokenInitParam.ChangeUserPinPolicy.TOKEN_FLAGS_ADMIN_AND_USER_CHANGE_USER_PIN
import ru.rutoken.pkcs11wrapper.rutoken.datatype.RutokenInitParam.SmMode.SECURE_MESSAGING_DEFAULT
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelFactory

class HighLevelToLowLevelTest {
    @Test
    fun toLowLevelRutokenInitParam() {
        val highLevelParam = RutokenInitParam(
            false,
            DEFAULT_ADMIN_PIN,
            DEFAULT_USER_PIN,
            TOKEN_FLAGS_ADMIN_AND_USER_CHANGE_USER_PIN,
            6,
            6,
            10,
            10,
            "Rutoken Label Init",
            SECURE_MESSAGING_DEFAULT
        )

        val lowLevelParam = highLevelParam
            .toCkRutokenInitParam(module.value.lowLevelFactory as IRtPkcs11LowLevelFactory) as CkRutokenInitParamImpl
        val lowLevelParamJnaValue = lowLevelParam.jnaValue

        lowLevelParamJnaValue.UseRepairMode.toLong() shouldBe 0L
        lowLevelParamJnaValue.pNewAdminPin.getByteArray(
            0,
            lowLevelParamJnaValue.ulNewAdminPinLen.toInt()
        ) shouldBe DEFAULT_ADMIN_PIN.toByteArray()
        lowLevelParamJnaValue.ulMinAdminPinLen.toLong() shouldBe 6L
    }

    companion object {
        private val module = RtModuleRule()
    }
}