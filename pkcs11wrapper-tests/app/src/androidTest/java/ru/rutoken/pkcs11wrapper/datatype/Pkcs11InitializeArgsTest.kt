/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.datatype

import io.kotest.matchers.equality.shouldBeEqualToComparingFields
import io.kotest.matchers.shouldBe
import org.junit.ClassRule
import org.junit.Test
import ru.rutoken.pkcs11jna.Pkcs11Constants.CKF_LIBRARY_CANT_CREATE_OS_THREADS
import ru.rutoken.pkcs11jna.Pkcs11Constants.CKF_OS_LOCKING_OK
import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule

class Pkcs11InitializeArgsTest {
    @Test
    fun runTest() {
        val flags = CKF_LIBRARY_CANT_CREATE_OS_THREADS or CKF_OS_LOCKING_OK
        val lowLevelFactory = module.value.lowLevelFactory

        val pkcs11InitializeArgs = Pkcs11InitializeArgs.Builder().apply {
            setMutexHandler(null)
            setOsLockingOk(true)
            setLibraryCantCreateOsThreads(true)
        }.build()

        val lowLevelInitializeArgs = lowLevelFactory.makeCInitializeArgs().apply {
            setMutexHandler(null)
            setFlags(flags)
        }

        pkcs11InitializeArgs.toCkCInitializeArgs(lowLevelFactory) shouldBeEqualToComparingFields lowLevelInitializeArgs
        pkcs11InitializeArgs.isLibraryCantCreateOsThreads shouldBe true
        pkcs11InitializeArgs.isOsLockingOk shouldBe true
    }

    companion object {
        @ClassRule
        @JvmField
        val module = ModuleRule()
    }
}