package ru.rutoken.pkcs11wrapper.rule.lowlevel.jna

import androidx.test.platform.app.InstrumentationRegistry
import com.sun.jna.Native
import org.junit.rules.ExternalResource
import ru.rutoken.pkcs11jna.Pkcs11
import ru.rutoken.pkcs11jna.Pkcs11Constants.CKF_OS_LOCKING_OK
import ru.rutoken.pkcs11wrapper.lowlevel.jna.Pkcs11JnaLowLevelApi
import ru.rutoken.pkcs11wrapper.lowlevel.jna.Pkcs11JnaLowLevelFactory
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelApi
import ru.rutoken.rtpcsc.RtPcsc

private val lowLevelModule = LowLevelModule("rtpkcs11ecp")

open class ModuleRule : ExternalResource() {
    open val value: IPkcs11LowLevelApi = lowLevelModule

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

private class LowLevelModule(libName: String) : Pkcs11JnaLowLevelApi(
    Native.load(libName, Pkcs11::class.java),
    Pkcs11JnaLowLevelFactory.Builder().build()
) {
    init {
        RtPcsc.setAppContext(InstrumentationRegistry.getInstrumentation().context)
    }
}