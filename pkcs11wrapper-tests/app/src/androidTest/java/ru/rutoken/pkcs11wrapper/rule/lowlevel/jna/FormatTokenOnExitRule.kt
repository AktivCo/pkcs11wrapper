/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rule.lowlevel.jna

import org.junit.rules.ExternalResource
import ru.rutoken.pkcs11wrapper.main.DEFAULT_ADMIN_PIN

class FormatTokenOnExitRule(private val module: RtModuleRule, private val slot: SlotRule) :
    ExternalResource() {
    override fun after() {
        val initParams = module.value.lowLevelFactory.makeRutokenInitParam().fillWithDefaultValues()
        module.value.C_EX_InitToken(slot.value, DEFAULT_ADMIN_PIN.toByteArray(), initParams).shouldBeOk()
    }
}