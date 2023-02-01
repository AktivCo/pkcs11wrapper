/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.attribute;

import org.jetbrains.annotations.NotNull;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;

/**
 * Attribute factory helps to create {@link Pkcs11Attribute} instances in a generic way,
 * without specifying concrete attribute class.
 * You can still use raw constructors to create attribute instances.
 */
public interface IPkcs11AttributeFactory {
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean isAttributeRegistered(IPkcs11AttributeType type);

    /**
     * Registers attribute in factory. Is used for vendor defined attributes.
     *
     * @param type      type of the attribute
     * @param attribute class of pkcs11 attribute
     */
    void registerAttribute(IPkcs11AttributeType type, Class<? extends Pkcs11Attribute> attribute);

    Pkcs11Attribute makeAttribute(IPkcs11AttributeType type);

    Pkcs11Attribute makeAttribute(IPkcs11AttributeType type, @NotNull Object value);

    /**
     * Makes attribute by casting it to specified class. Type check is performed.
     *
     * @param <Attr>         subclass of Pkcs11Attribute
     * @param attributeClass pkcs11 attribute class
     * @param type           type of the attribute
     * @return newly created attribute
     * @throws ClassCastException if user passes inconsistent type and class
     */
    <Attr extends Pkcs11Attribute> Attr makeAttribute(Class<Attr> attributeClass, IPkcs11AttributeType type);

    <Attr extends Pkcs11Attribute> Attr makeAttribute(Class<Attr> attributeClass, IPkcs11AttributeType type,
                                                      @NotNull Object value);
}
