/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.main;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.object.factory.IPkcs11ObjectFactory;
import ru.rutoken.pkcs11wrapper.object.factory.Pkcs11ObjectFactory;

/**
 * Default module implementation.
 * User should use this class as an entry point to a high-level layer of a pkcs11wrapper.
 */
public class Pkcs11BaseModule implements Pkcs11Module {
    private final IPkcs11Api mApi;
    private final IPkcs11HighLevelFactory mHighLevelFactory;
    private final IPkcs11AttributeFactory mAttributeFactory;
    private final IPkcs11ObjectFactory mObjectFactory;

    /**
     * Uses default high-level factory implementation - {@link Pkcs11HighLevelFactory}
     *
     * @param api pkcs11 interface
     */
    public Pkcs11BaseModule(IPkcs11Api api) {
        this(api, new Pkcs11HighLevelFactory());
    }

    public Pkcs11BaseModule(IPkcs11Api api, IPkcs11HighLevelFactory highLevelFactory) {
        this(api, highLevelFactory, new Pkcs11AttributeFactory());
    }

    public Pkcs11BaseModule(IPkcs11Api api, IPkcs11HighLevelFactory highLevelFactory,
                            IPkcs11AttributeFactory attributeFactory) {
        mApi = Objects.requireNonNull(api);
        mHighLevelFactory = Objects.requireNonNull(highLevelFactory);
        mAttributeFactory = Objects.requireNonNull(attributeFactory);
        mObjectFactory = new Pkcs11ObjectFactory(attributeFactory);
    }

    @Override
    public IPkcs11Api getApi() {
        return mApi;
    }

    @Override
    public IPkcs11HighLevelFactory getHighLevelFactory() {
        return mHighLevelFactory;
    }

    @Override
    public IPkcs11AttributeFactory getAttributeFactory() {
        return mAttributeFactory;
    }

    @Override
    public IPkcs11ObjectFactory getObjectFactory() {
        return mObjectFactory;
    }
}
