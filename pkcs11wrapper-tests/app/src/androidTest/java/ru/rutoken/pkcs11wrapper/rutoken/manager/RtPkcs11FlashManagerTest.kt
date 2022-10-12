package ru.rutoken.pkcs11wrapper.rutoken.manager

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.Assume.assumeTrue
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import ru.rutoken.pkcs11jna.RtPkcs11Constants.TOKEN_FLAGS_HAS_FLASH_DRIVE
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType.CKU_SO
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType.CKU_USER
import ru.rutoken.pkcs11wrapper.main.DEFAULT_ADMIN_PIN
import ru.rutoken.pkcs11wrapper.main.DEFAULT_USER_PIN
import ru.rutoken.pkcs11wrapper.rule.highlevel.RtModuleRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.RtTokenRule
import ru.rutoken.pkcs11wrapper.rule.highlevel.SlotRule
import ru.rutoken.pkcs11wrapper.rutoken.datatype.VolumeFormatInfoExtended
import ru.rutoken.pkcs11wrapper.rutoken.datatype.VolumeInfoExtended

import ru.rutoken.pkcs11wrapper.rutoken.manager.RtPkcs11FlashManager.AccessMode.ACCESS_MODE_RO
import ru.rutoken.pkcs11wrapper.rutoken.manager.RtPkcs11FlashManager.AccessMode.ACCESS_MODE_RW

class RtPkcs11FlashManagerTest {
    @Test
    fun runComplexSingleVolume() {
        with(token.value) {
            assumeTrue(tokenInfoExtended.flags and TOKEN_FLAGS_HAS_FLASH_DRIVE != 0L)

            val driveSize = getDriveSize()
            val formatInfo = VolumeFormatInfoExtended(driveSize, ACCESS_MODE_RW, CKU_USER.asLong, 0)

            flashManager.formatDrive(CKU_SO.asLong, DEFAULT_ADMIN_PIN, listOf(formatInfo))

            Thread.sleep(10000) // Flash drive may need some time to reconnect after formatting

            val volume = getVolumesInfo(expectedVolumes = 1).single()

            flashManager.changeVolumeAttributes(CKU_USER.asLong, DEFAULT_USER_PIN, volume.id, ACCESS_MODE_RO, true)
        }
    }

    @Test
    fun runComplexManyVolumes() {
        assumeTrue(token.value.tokenInfoExtended.flags and TOKEN_FLAGS_HAS_FLASH_DRIVE != 0L)

        val driveSize = getDriveSize()

        val formatInfos = listOf(
            VolumeFormatInfoExtended(100, ACCESS_MODE_RW, CKU_USER.asLong, 0),
            VolumeFormatInfoExtended(150, ACCESS_MODE_RW, CKU_USER.asLong, 0),
            VolumeFormatInfoExtended(driveSize - 100 - 150, ACCESS_MODE_RW, CKU_USER.asLong, 0)
        )

        token.value.flashManager.formatDrive(CKU_SO.asLong, DEFAULT_ADMIN_PIN, formatInfos)

        Thread.sleep(10000) // Flash drive may need some time to reconnect after formatting

        getVolumesInfo(expectedVolumes = 3)
    }

    private fun getDriveSize(): Long {
        val driveSize = token.value.flashManager.driveSize

        driveSize shouldNotBe 0

        return driveSize
    }

    private fun getVolumesInfo(expectedVolumes: Int): List<VolumeInfoExtended> {
        val volumesInfo = token.value.flashManager.volumesInfo

        volumesInfo.size shouldBe expectedVolumes

        return volumesInfo
    }

    companion object {
        private val module = RtModuleRule()
        private val slot = SlotRule(module)
        private val token = RtTokenRule(slot)

        @JvmStatic
        @get:ClassRule
        val sRuleChain: TestRule = RuleChain.outerRule(module).around(slot).around(token)
    }
}