package ru.rutoken.pkcs11wrapper.rule.highlevel

import com.sun.jna.Native
import ru.rutoken.pkcs11jna.RtPkcs11
import ru.rutoken.pkcs11wrapper.main.IPkcs11Module
import ru.rutoken.pkcs11wrapper.main.Pkcs11BaseModule
import ru.rutoken.pkcs11wrapper.rutoken.attribute.RtPkcs11AttributeFactory
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.fake.RtPkcs11FakeLowLevelApi
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna.RtPkcs11JnaLowLevelApi
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna.RtPkcs11JnaLowLevelFactory
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Api
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11HighLevelFactory

private val rtModule = RtModule("rtpkcs11ecp")

val rtFakeModule = RtFakeModule(RtPkcs11FakeLowLevelApi())

/**
 * Creates a Pkcs11Module that provides Rutoken extended pkcs11 api.
 */
class RtModuleRule : ModuleRule() {
    override val value: IPkcs11Module = rtModule

    override fun before() {
        value.initializeModule(null)
    }

    override fun after() {
        value.finalizeModule()
    }
}

private class RtModule(name: String) : Pkcs11BaseModule(
    RtPkcs11Api(
        RtPkcs11JnaLowLevelApi(
            Native.load(name, RtPkcs11::class.java),
            RtPkcs11JnaLowLevelFactory()
        )
    ),
    RtPkcs11HighLevelFactory(),
    RtPkcs11AttributeFactory()
)

class RtFakeModule(lowLevelApi: RtPkcs11FakeLowLevelApi) :
    Pkcs11BaseModule(RtPkcs11Api(lowLevelApi), RtPkcs11HighLevelFactory(), RtPkcs11AttributeFactory()) {
    init {
        lowLevelApi.createFakeTokens(objectFactory)
    }
}
