/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;

public class FakeObject {
    private final Map<Long, CkAttribute> mAttributes = new HashMap<>();

    FakeObject(List<CkAttribute> attributes) {
        for (CkAttribute attribute : attributes) {
            mAttributes.put(attribute.getType(), attribute);
        }
    }

    @Nullable
    CkAttribute getAttribute(IPkcs11AttributeType type) {
        return mAttributes.get(type.getAsLong());
    }
}
