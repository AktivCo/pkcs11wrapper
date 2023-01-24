/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.main

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldBeOneOf
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.longs.shouldBeGreaterThanOrEqual
import io.kotest.matchers.string.shouldContain
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue.CKR_SESSION_CLOSED
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue.CKR_SESSION_HANDLE_INVALID
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType
import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.SlotRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.TokenRule

class Pkcs11TokenTest {
    @Test
    fun getTokenInfo() {
        val info = token.value.tokenInfo
        info.manufacturerId shouldContain "Aktiv Co."
    }

    @Test
    fun getMechanismList() {
        val mechanisms = token.value.mechanismList
        mechanisms.size shouldBeGreaterThan 0
    }

    @Test
    fun getMechanismInfo() {
        for (mechanism in token.value.mechanismList) {
            val info = token.value.getMechanismInfo(mechanism)
            info.minKeySize shouldBeGreaterThanOrEqual 0
            info.maxKeySize shouldBeGreaterThanOrEqual info.minKeySize
        }
    }

    @Test
    fun openSession() {
        val session: Pkcs11Session
        token.value.openSession(true).use { session = it }
        checkSessionClosed(session)
    }

    @Test
    fun initToken() {
        token.value.apply {
            initToken(DEFAULT_ADMIN_PIN, TOKEN_LABEL)
            openSession(true).use { session ->
                session.login(Pkcs11UserType.CKU_SO, DEFAULT_ADMIN_PIN).use {
                    session.initPin(DEFAULT_USER_PIN)
                }
            }
            tokenInfo.label shouldContain TOKEN_LABEL
        }
    }

    @Test
    fun closeAllSessions() {
        val session1: Pkcs11Session
        val session2: Pkcs11Session

        token.value.apply {
            try {
                session1 = openSession(true)
                session2 = openSession(true)
            } finally {
                closeAllSessions()
            }
        }

        checkSessionClosed(session1)
        checkSessionClosed(session2)
    }

    private fun checkSessionClosed(session: Pkcs11Session) {
        val exception = shouldThrow<Pkcs11Exception> {
            session.sessionInfo
        }
        exception.code shouldBeOneOf listOf(CKR_SESSION_CLOSED, CKR_SESSION_HANDLE_INVALID)
    }

    companion object {
        private val module = ModuleRule()
        private val slot = SlotRule(module)
        private val token = TokenRule(slot)

        @ClassRule
        @JvmField
        val ruleChain: TestRule = RuleChain.outerRule(module).around(slot).around(token)
    }
}