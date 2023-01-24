/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rule.lowlevel.jna

import org.junit.rules.ExternalResource

class LoginRule(
    private val module: ModuleRule,
    private val session: SessionRule,
    private val userType: Long,
    private val pin: String
) : ExternalResource() {
    override fun before() {
        module.value.C_Login(session.value, userType, pin.toByteArray()).shouldBeOk()
    }

    override fun after() {
        module.value.C_Logout(session.value).shouldBeOk()
    }
}