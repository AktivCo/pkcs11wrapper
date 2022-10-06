package ru.rutoken.pkcs11wrapper.rule.lowlevel.jna

import org.junit.rules.ExternalResource
import ru.rutoken.pkcs11jna.RtPkcs11Constants.*
import ru.rutoken.pkcs11wrapper.main.DEFAULT_ADMIN_PIN
import ru.rutoken.pkcs11wrapper.main.DEFAULT_USER_PIN

class FormatTokenOnExitRule(private val module: RtModuleRule, private val slot: SlotRule) :
    ExternalResource() {
    override fun after() {
        val initParams = module.value.lowLevelFactory.makeRutokenInitParam().apply {
            setUseRepairMode(0)
            setNewAdminPin(DEFAULT_ADMIN_PIN.toByteArray())
            setNewUserPin(DEFAULT_USER_PIN.toByteArray())
            setMinAdminPinLen(6)
            setMinUserPinLen(6)
            setChangeUserPinPolicy(TOKEN_FLAGS_ADMIN_CHANGE_USER_PIN or TOKEN_FLAGS_USER_CHANGE_USER_PIN)
            setMaxAdminRetryCount(10)
            setMaxUserRetryCount(10)
            setSmMode(SECURE_MESSAGING_DEFAULT)
            setTokenLabel("Rutoken Label Init".toByteArray())
        }

        module.value.C_EX_InitToken(slot.value, DEFAULT_ADMIN_PIN.toByteArray(), initParams).shouldBeOk()
    }
}