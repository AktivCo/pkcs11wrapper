package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna

import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11jna.Pkcs11Constants.CKU_USER
import ru.rutoken.pkcs11wrapper.main.DEFAULT_USER_PIN
import ru.rutoken.pkcs11wrapper.rule.lowlevel.jna.*
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkFunctionListExtended
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkTokenInfoExtended
import ru.rutoken.pkcs11wrapper.util.Mutable
import ru.rutoken.pkcs11wrapper.util.MutableLong

class RtExtendedTest {
    @Test
    fun getFunctionListExtended() {
        val functionListMutable = Mutable<CkFunctionListExtended>()

        module.value.C_EX_GetFunctionListExtended(functionListMutable).shouldBeOk()
        functionListMutable.value shouldNotBe null

        val jnaFunctionList = (functionListMutable.value as CkFunctionListExtendedImpl).jnaValue

        jnaFunctionList.version.major shouldBe 2
        jnaFunctionList.version.minor shouldBe 40
        jnaFunctionList.C_EX_GetFunctionListExtended shouldNotBe null
    }

    @Test
    fun getTokenInfoExtended() {
        val tokenInfoMutable = Mutable<CkTokenInfoExtended>()

        module.value.C_EX_GetTokenInfoExtended(slot.value, tokenInfoMutable).shouldBeOk()
        tokenInfoMutable.value shouldNotBe null
    }

    @Test
    fun setAndGetTokenName() {
        val tokenName = "New Token Name".toByteArray()

        module.value.C_EX_SetTokenName(session.value, tokenName).shouldBeOk()

        val tokenNameSize = MutableLong()

        module.value.C_EX_GetTokenName(session.value, null, tokenNameSize).shouldBeOk()
        tokenNameSize.value.toInt() shouldBeGreaterThanOrEqual tokenName.size

        val tokenNameFromToken = ByteArray(tokenNameSize.value.toInt())

        module.value.C_EX_GetTokenName(session.value, tokenNameFromToken, tokenNameSize).shouldBeOk()

        tokenNameFromToken.copyOf(tokenNameSize.value.toInt()) shouldBe tokenName
    }

    @Test
    fun setAndGetLicense() {
        val licenseSizeBytes = 72
        val licenseNumber = 1L
        val license = ByteArray(licenseSizeBytes).apply { fill(0xA) }

        module.value.C_EX_SetLicense(session.value, licenseNumber, license).shouldBeOk()

        val licenseSize = MutableLong()

        module.value.C_EX_GetLicense(session.value, licenseNumber, null, licenseSize).shouldBeOk()
        licenseSize.value.toInt() shouldBeGreaterThanOrEqual licenseSizeBytes

        val licenseFromToken = ByteArray(licenseSizeBytes)

        module.value.C_EX_GetLicense(session.value, licenseNumber, licenseFromToken, licenseSize).shouldBeOk()
        licenseFromToken.copyOf(licenseSize.value.toInt()) shouldBe license
    }

    companion object {
        private val module = RtModuleRule()
        private val slot = SlotRule(module)
        private val session = SessionRule(module, slot)
        private val login = LoginRule(module, session, CKU_USER, DEFAULT_USER_PIN)

        @JvmStatic
        @get:ClassRule
        val ruleChain: TestRule = RuleChain.outerRule(module).around(slot).around(session).around(login)
    }
}