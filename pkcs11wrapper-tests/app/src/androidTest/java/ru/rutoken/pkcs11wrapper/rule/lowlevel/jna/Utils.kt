package ru.rutoken.pkcs11wrapper.rule.lowlevel.jna

import io.kotest.matchers.shouldBe
import ru.rutoken.pkcs11jna.Pkcs11Constants.CKR_OK

fun Long.shouldBeOk() {
    this shouldBe CKR_OK
}