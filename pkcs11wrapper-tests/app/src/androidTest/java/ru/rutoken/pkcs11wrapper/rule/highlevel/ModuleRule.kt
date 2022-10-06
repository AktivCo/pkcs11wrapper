package ru.rutoken.pkcs11wrapper.rule.highlevel

import androidx.test.platform.app.InstrumentationRegistry
import com.sun.jna.Native
import org.junit.rules.ExternalResource
import ru.rutoken.pkcs11jna.Pkcs11
import ru.rutoken.pkcs11wrapper.lowlevel.fake.Pkcs11FakeLowLevelApi
import ru.rutoken.pkcs11wrapper.lowlevel.jna.Pkcs11JnaLowLevelApi
import ru.rutoken.pkcs11wrapper.lowlevel.jna.Pkcs11JnaLowLevelFactory
import ru.rutoken.pkcs11wrapper.main.IPkcs11Module
import ru.rutoken.pkcs11wrapper.main.Pkcs11Api
import ru.rutoken.pkcs11wrapper.main.Pkcs11BaseModule
import ru.rutoken.rtpcsc.RtPcsc

private val pkcs11Module = Module("rtpkcs11ecp")
//private val pkcs11Module = FakeModule(Pkcs11FakeLowLevelApi())

/**
 * Use FakeModule to run tests against fake pkcs11 implementation
 */
open class ModuleRule : ExternalResource() {
    open val value: IPkcs11Module = pkcs11Module

    override fun before() {
        value.initializeModule(null)
    }

    override fun after() {
        value.finalizeModule()
    }
}

private class Module(name: String) : Pkcs11BaseModule(
    Pkcs11Api(
        Pkcs11JnaLowLevelApi(
            Native.load(name, Pkcs11::class.java),
            Pkcs11JnaLowLevelFactory.Builder().build()
        )
    )
) {
    init {
        RtPcsc.setAppContext(InstrumentationRegistry.getInstrumentation().context)
    }
}

private class FakeModule(lowLevelApi: Pkcs11FakeLowLevelApi) : Pkcs11BaseModule(Pkcs11Api(lowLevelApi)) {
    init {
        lowLevelApi.createFakeTokens(objectFactory)
    }
}
