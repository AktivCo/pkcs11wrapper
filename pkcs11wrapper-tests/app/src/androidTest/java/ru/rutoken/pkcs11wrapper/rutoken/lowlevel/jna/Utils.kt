/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna

import io.kotest.matchers.shouldNotBe
import ru.rutoken.pkcs11jna.Pkcs11Constants.*
import ru.rutoken.pkcs11jna.RtPkcs11Constants.CKA_VENDOR_SUPPORT_INTERNAL_TRUSTED_CERTS
import ru.rutoken.pkcs11jna.RtPkcs11Constants.CKH_VENDOR_TOKEN_INFO
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute
import ru.rutoken.pkcs11wrapper.rule.lowlevel.jna.shouldBeOk
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelApi
import ru.rutoken.pkcs11wrapper.util.MutableLong

private fun IRtPkcs11LowLevelApi.findObjects(session: Long, template: List<CkAttribute>, maxCount: Int): LongArray {
    C_FindObjectsInit(session, template).shouldBeOk()

    val objects = LongArray(maxCount)
    val objectsCount = MutableLong()

    C_FindObjects(session, objects, objects.size.toLong(), objectsCount).shouldBeOk()
    C_FindObjectsFinal(session).shouldBeOk()

    return objects.copyOf(objectsCount.value.toInt())
}

private fun IRtPkcs11LowLevelApi.getAttributeValues(session: Long, obj: Long, attributes: List<CkAttribute>) {
    C_GetAttributeValue(session, obj, attributes).shouldBeOk()

    for (attribute in attributes) {
        val size = attribute.valueLen
        if (size != CK_UNAVAILABLE_INFORMATION && size > 0)
            attribute.allocate(size)
    }

    C_GetAttributeValue(session, obj, attributes).shouldBeOk()
}

private fun IRtPkcs11LowLevelApi.findVendorTokenInfoObject(session: Long): Long {
    val objects = findObjects(
        session, listOf(
            lowLevelFactory.makeAttribute().apply {
                type = CKA_CLASS
                longValue = CKO_HW_FEATURE
            },
            lowLevelFactory.makeAttribute().apply {
                type = CKA_HW_FEATURE_TYPE
                longValue = CKH_VENDOR_TOKEN_INFO
            },
        ), 100
    )

    return objects.single()
}

fun IRtPkcs11LowLevelApi.isSupportKti(slot: Long): Boolean {
    val session = MutableLong()
    C_OpenSession(slot, CKF_SERIAL_SESSION or CKF_RW_SESSION, null, null, session).shouldBeOk()
    session.value shouldNotBe CK_INVALID_HANDLE

    try {
        val vendorTokenInfoObject = findVendorTokenInfoObject(session.value)

        val attributes =
            listOf(lowLevelFactory.makeAttribute().apply { type = CKA_VENDOR_SUPPORT_INTERNAL_TRUSTED_CERTS })
        getAttributeValues(session.value, vendorTokenInfoObject, attributes)

        return attributes.single().booleanValue
    } finally {
        C_CloseSession(session.value).shouldBeOk()
    }
}