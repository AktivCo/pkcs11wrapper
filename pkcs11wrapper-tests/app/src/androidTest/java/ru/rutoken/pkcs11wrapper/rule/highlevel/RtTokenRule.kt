package ru.rutoken.pkcs11wrapper.rule.highlevel

import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Token

class RtTokenRule(slot: SlotRule) : TokenRule(slot) {
    override val value get() = _value as RtPkcs11Token
}