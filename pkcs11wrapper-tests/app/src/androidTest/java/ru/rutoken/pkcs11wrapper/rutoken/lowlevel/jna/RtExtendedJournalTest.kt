/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna

import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldNotBe
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11jna.Pkcs11Constants.CKU_USER
import ru.rutoken.pkcs11jna.Pkcs11Tc26Constants.CKM_GOSTR3410_WITH_GOSTR3411_12_256
import ru.rutoken.pkcs11wrapper.main.DEFAULT_USER_PIN
import ru.rutoken.pkcs11wrapper.rule.lowlevel.jna.*
import ru.rutoken.pkcs11wrapper.util.MutableLong

class RtExtendedJournalTest {
    @Test
    fun getJournal() {
        signData(byteArrayOf(1, 2, 3, 4, 5, 6, 7, 8))

        val journalSize = MutableLong()

        module.value.C_EX_GetJournal(slot.value, null, journalSize).shouldBeOk()

        journalSize.value shouldBeGreaterThan 0L

        val journalBuffer = ByteArray(journalSize.value.toInt())
        val emptyJournalBuffer = journalBuffer.copyOf(journalSize.value.toInt())

        module.value.C_EX_GetJournal(slot.value, journalBuffer, journalSize).shouldBeOk()
        journalSize.value shouldBeGreaterThan 0L
        journalSize.value.toInt() shouldBeLessThanOrEqual journalBuffer.size
        journalBuffer shouldNotBe emptyJournalBuffer
    }

    private fun signData(data: ByteArray) {
        with(module.value) {
            val mechanism = lowLevelFactory.makeMechanism().apply {
                setMechanism(CKM_GOSTR3410_WITH_GOSTR3411_12_256, null)
            }

            C_SignInit(session.value, mechanism, keyPair.privateKey).shouldBeOk()
            val signSize = MutableLong()
            C_Sign(session.value, data, null, signSize).shouldBeOk()
            signSize.value shouldNotBe 0
            C_Sign(session.value, data, ByteArray(signSize.value.toInt()), signSize).shouldBeOk()

        }
    }

    companion object {
        private val module = RtModuleRule()
        private val slot = SlotRule(module)
        private val session = SessionRule(module, slot)
        private val login = LoginRule(module, session, CKU_USER, DEFAULT_USER_PIN)
        private val keyPair = GenerateKeyPairRule(module, session)

        @ClassRule
        @JvmField
        val ruleChain: TestRule = RuleChain.outerRule(module).around(slot).around(session).around(login).around(keyPair)
    }
}