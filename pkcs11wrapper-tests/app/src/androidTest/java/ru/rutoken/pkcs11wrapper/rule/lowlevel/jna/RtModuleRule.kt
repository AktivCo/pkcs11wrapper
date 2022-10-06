package ru.rutoken.pkcs11wrapper.rule.lowlevel.jna

import androidx.test.platform.app.InstrumentationRegistry
import com.sun.jna.Native
import ru.rutoken.pkcs11jna.Pkcs11Constants.CKF_OS_LOCKING_OK
import ru.rutoken.pkcs11jna.RtPkcs11
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelApi
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna.RtPkcs11JnaLowLevelApi
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna.RtPkcs11JnaLowLevelFactory
import ru.rutoken.rtpcsc.RtPcsc

private val lowLevelModule = RtModule("rtpkcs11ecp")

class RtModuleRule : ModuleRule() {
    override val value: IRtPkcs11LowLevelApi = lowLevelModule

    override fun before() {
        val args = value.lowLevelFactory.makeCInitializeArgs().apply {
            setFlags(CKF_OS_LOCKING_OK)
            setMutexHandler(null)
        }
        value.C_Initialize(args).shouldBeOk()
    }

    override fun after() {
        value.C_Finalize(null).shouldBeOk()
    }
}

private class RtModule(libName: String) : RtPkcs11JnaLowLevelApi(
    Native.load(libName, RtPkcs11::class.java),
    RtPkcs11JnaLowLevelFactory()
) {
    init {
        RtPcsc.setAppContext(InstrumentationRegistry.getInstrumentation().context)
    }
}