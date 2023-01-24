/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.constant

import org.junit.ClassRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.RtModuleRule

open class RtPkcs11ConstantTest {
    companion object {
        @ClassRule
        @JvmField
        val module = RtModuleRule()
    }
}