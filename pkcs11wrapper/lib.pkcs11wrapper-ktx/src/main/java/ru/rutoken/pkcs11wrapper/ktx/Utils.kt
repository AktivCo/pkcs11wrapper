/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.ktx

import ru.rutoken.pkcs11wrapper.`object`.Pkcs11Object
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute
import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory
import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType
import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism
import ru.rutoken.pkcs11wrapper.mechanism.parameter.Pkcs11ByteArrayMechanismParams
import ru.rutoken.pkcs11wrapper.mechanism.parameter.Pkcs11MechanismParams
import java.util.*

typealias Template = List<Pkcs11Attribute>
typealias ByteArrayParams = Pkcs11ByteArrayMechanismParams

fun mechanism(type: IPkcs11MechanismType, parameter: Pkcs11MechanismParams?): Pkcs11Mechanism =
    Pkcs11Mechanism.make(type, parameter)

fun mechanism(type: IPkcs11MechanismType): Pkcs11Mechanism = Pkcs11Mechanism.make(type)

fun IPkcs11AttributeFactory.attribute(type: IPkcs11AttributeType, value: Any): Pkcs11Attribute =
    makeAttribute(type, value)

fun randomData(size: Int) = ByteArray(size).apply { Random().nextBytes(this) }

fun <Object : Pkcs11Object, R> Object.scoped(session: Pkcs11Session, block: Object.() -> R): R {
    return try {
        block()
    } finally {
        session.objectManager.destroyObject(this)
    }
}