package ru.rutoken.pkcs11wrapper.rule.lowlevel.jna

import io.kotest.matchers.shouldBe
import ru.rutoken.pkcs11jna.Pkcs11Constants.CKR_OK
import ru.rutoken.pkcs11jna.RtPkcs11Constants.*
import ru.rutoken.pkcs11wrapper.main.DEFAULT_ADMIN_PIN
import ru.rutoken.pkcs11wrapper.main.DEFAULT_USER_PIN
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkRutokenInitParam

const val MIN_PIN_LENGTH = 6L
const val MAX_RETRY_COUNT = 10L

fun Long.shouldBeOk() {
    this shouldBe CKR_OK
}

fun CkRutokenInitParam.fillWithDefaultValues() = apply {
    setUseRepairMode(0)
    setNewAdminPin(DEFAULT_ADMIN_PIN.toByteArray())
    setNewUserPin(DEFAULT_USER_PIN.toByteArray())
    setMinAdminPinLen(MIN_PIN_LENGTH)
    setMinUserPinLen(MIN_PIN_LENGTH)
    setChangeUserPinPolicy(TOKEN_FLAGS_ADMIN_CHANGE_USER_PIN or TOKEN_FLAGS_USER_CHANGE_USER_PIN)
    setMaxAdminRetryCount(MAX_RETRY_COUNT)
    setMaxUserRetryCount(MAX_RETRY_COUNT)
    setSmMode(SECURE_MESSAGING_DEFAULT)
    setTokenLabel("Rutoken Label Init".toByteArray())
}