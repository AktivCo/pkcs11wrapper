/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.attribute;

import org.jetbrains.annotations.NotNull;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;

/**
 * Represents a pkcs11 attribute. This is a high-level representation of {@link CkAttribute}.
 * Instances of this hierarchy could be read from token or created by user to pass to pkcs11 in a template.
 * Subclasses may be instantiated directly by calling constructors, or with a {@link IPkcs11AttributeFactory}.
 */
public interface Pkcs11Attribute {

    IPkcs11AttributeType getType();

    /**
     * @return true if attribute value was not set
     */
    boolean isEmpty();

    /**
     * In some cases, attribute may be optional
     */
    boolean isPresent();

    void setPresent(boolean value);

    /**
     * Some attributes can not be read from token, so this flag is set for them
     */
    boolean isSensitive();

    void setSensitive(boolean value);

    void assignFromCkAttribute(CkAttribute from, IPkcs11LowLevelFactory lowLevelFactory,
                               IPkcs11AttributeFactory attributeFactory);

    CkAttribute toCkAttribute(IPkcs11LowLevelFactory factory);

    @NotNull
    Object getValue();

    void setValue(@NotNull Object value);
}
