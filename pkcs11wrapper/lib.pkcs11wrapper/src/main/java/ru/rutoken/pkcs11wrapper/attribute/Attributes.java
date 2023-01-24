/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.attribute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.rutoken.pkcs11jna.Pkcs11Constants;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;

/**
 * Defines basic methods to work with attributes.
 * In most cases user does not need to use this class directly.
 */
public final class Attributes {
    private Attributes() {
    }

    public static List<CkAttribute> makeCkAttributesList(List<Pkcs11Attribute> attributes,
                                                         IPkcs11LowLevelFactory factory) {
        final List<CkAttribute> ckAttributes = new ArrayList<>();
        for (Pkcs11Attribute attribute : attributes) {
            ckAttributes.add(attribute.toCkAttribute(factory));
        }
        return ckAttributes;
    }

    public static void getAttributeValue(Pkcs11Session session, long objectHandle, Pkcs11Attribute attribute) {
        getAttributeValues(session, objectHandle, Collections.singletonList(attribute));
    }

    /**
     * Read attributes values from token
     */
    public static void getAttributeValues(Pkcs11Session session, long objectHandle,
                                          List<Pkcs11Attribute> attributes) {
        final List<CkAttribute> ckAttributes = makeCkAttributesList(attributes, session.getLowLevelFactory());

        long r = session.getLowLevelApi().C_GetAttributeValue(
                session.getSessionHandle(), objectHandle, ckAttributes);
        IPkcs11ReturnValue res = IPkcs11ReturnValue.getInstance(r, session.getVendorExtensions());
        if (res != Pkcs11ReturnValue.CKR_ATTRIBUTE_SENSITIVE &&
                res != Pkcs11ReturnValue.CKR_ATTRIBUTE_TYPE_INVALID) {
            Pkcs11Exception.throwIfNotOk(res, "C_GetAttributeValue can not get attributes lengths");
        }

        // allocate memory
        for (CkAttribute attribute : ckAttributes) {
            final long size = attribute.getValueLen();
            //noinspection ConditionCoveredByFurtherCondition
            if (size != Pkcs11Constants.CK_UNAVAILABLE_INFORMATION && size > 0) {
                attribute.allocate(size);
            }
        }

        r = session.getLowLevelApi().C_GetAttributeValue(
                session.getSessionHandle(), objectHandle, ckAttributes);
        res = IPkcs11ReturnValue.getInstance(r, session.getVendorExtensions());
        // not all return codes are real errors
        if (res != Pkcs11ReturnValue.CKR_ATTRIBUTE_SENSITIVE &&
                res != Pkcs11ReturnValue.CKR_ATTRIBUTE_TYPE_INVALID) {
            Pkcs11Exception.throwIfNotOk(res, "C_GetAttributeValue failed");
        }
        for (int a = 0; a < attributes.size(); ++a) {
            final Pkcs11Attribute attribute = attributes.get(a);
            attribute.assignFromCkAttribute(ckAttributes.get(a), session.getLowLevelFactory(),
                    session.getAttributeFactory());
            if (ckAttributes.get(a).getValueLen() == Pkcs11Constants.CK_UNAVAILABLE_INFORMATION) {
                if (res == Pkcs11ReturnValue.CKR_ATTRIBUTE_SENSITIVE) {
                    attribute.setSensitive(true);
                    attribute.setPresent(true);
                } else if (res == Pkcs11ReturnValue.CKR_ATTRIBUTE_TYPE_INVALID) {
                    attribute.setPresent(false);
                }
            } else {
                attribute.setPresent(true);
            }
        }
    }
}
