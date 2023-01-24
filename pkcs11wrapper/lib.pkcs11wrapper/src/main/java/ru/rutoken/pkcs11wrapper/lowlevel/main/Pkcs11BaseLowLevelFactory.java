/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.main;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11CertificateType;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11HardwareFeatureType;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11KeyType;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11ObjectClass;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanism;
import ru.rutoken.pkcs11wrapper.lowlevel.jna.Pkcs11JnaMechanismParamsLowLevelConverterVisitor;
import ru.rutoken.pkcs11wrapper.lowlevel.main.Pkcs11BaseLowLevelFactory.Builder;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.Pkcs11MechanismParams;

/**
 * Base low-level factory implementation. Subclass instances should be created using a builder pattern.
 */
public abstract class Pkcs11BaseLowLevelFactory<B extends Builder<B>> implements IPkcs11LowLevelFactory {
    protected final B mBuilder;

    protected Pkcs11BaseLowLevelFactory(B builder) {
        mBuilder = Objects.requireNonNull(builder);
    }

    @Override
    public IPkcs11AttributeType.VendorFactory getAttributeTypeVendorFactory() {
        return mBuilder.attributeTypeVendorFactory;
    }

    @Override
    public IPkcs11CertificateType.VendorFactory getCertificateTypeVendorFactory() {
        return mBuilder.certificateTypeVendorFactory;
    }

    @Override
    public IPkcs11HardwareFeatureType.VendorFactory getHardwareFeatureTypeVendorFactory() {
        return mBuilder.hardwareFeatureTypeVendorFactory;
    }

    @Override
    public IPkcs11KeyType.VendorFactory getKeyTypeVendorFactory() {
        return mBuilder.keyTypeVendorFactory;
    }

    @Override
    public IPkcs11MechanismType.VendorFactory getMechanismTypeVendorFactory() {
        return mBuilder.mechanismTypeVendorFactory;
    }

    @Override
    public IPkcs11ObjectClass.VendorFactory getObjectClassVendorFactory() {
        return mBuilder.objectClassVendorFactory;
    }

    @Override
    public IPkcs11ReturnValue.VendorFactory getReturnValueVendorFactory() {
        return mBuilder.returnValueVendorFactory;
    }

    @NotNull
    @Override
    public CkMechanism.Parameter convertMechanismParams(Pkcs11MechanismParams parameter) {
        final Pkcs11MechanismParams.LowLevelConverterVisitor visitor = mBuilder.mechanismParamsConverterFactory.make();
        parameter.acceptVisitor(visitor);
        return visitor.getConverted();
    }

    public abstract static class Builder<BuilderType extends Builder<BuilderType>> {
        protected Pkcs11MechanismParams.LowLevelConverterVisitor.Factory mechanismParamsConverterFactory =
                Pkcs11JnaMechanismParamsLowLevelConverterVisitor::new;
        protected IPkcs11AttributeType.VendorFactory attributeTypeVendorFactory = value -> null;
        protected IPkcs11CertificateType.VendorFactory certificateTypeVendorFactory = value -> null;
        protected IPkcs11HardwareFeatureType.VendorFactory hardwareFeatureTypeVendorFactory = value -> null;
        protected IPkcs11KeyType.VendorFactory keyTypeVendorFactory = value -> null;
        protected IPkcs11MechanismType.VendorFactory mechanismTypeVendorFactory = value -> null;
        protected IPkcs11ObjectClass.VendorFactory objectClassVendorFactory = value -> null;
        protected IPkcs11ReturnValue.VendorFactory returnValueVendorFactory = value -> null;

        public abstract Pkcs11BaseLowLevelFactory<?> build();

        public abstract BuilderType self();

        /**
         * @param factory factory to make Visitor which converts Pkcs11MechanismParams to low-level representation
         */
        public BuilderType setMechanismParamsConverterFactory(
                Pkcs11MechanismParams.LowLevelConverterVisitor.Factory factory) {
            mechanismParamsConverterFactory = Objects.requireNonNull(factory);
            return self();
        }

        public BuilderType setAttributeTypeVendorFactory(IPkcs11AttributeType.VendorFactory factory) {
            attributeTypeVendorFactory = Objects.requireNonNull(factory);
            return self();
        }

        public BuilderType setCertificateTypeVendorFactory(IPkcs11CertificateType.VendorFactory factory) {
            certificateTypeVendorFactory = factory;
            return self();
        }

        public BuilderType setHardwareFeatureTypeVendorFactory(IPkcs11HardwareFeatureType.VendorFactory factory) {
            hardwareFeatureTypeVendorFactory = factory;
            return self();
        }

        public BuilderType setKeyTypeVendorFactory(IPkcs11KeyType.VendorFactory factory) {
            keyTypeVendorFactory = factory;
            return self();
        }

        public BuilderType setMechanismTypeVendorFactory(IPkcs11MechanismType.VendorFactory factory) {
            mechanismTypeVendorFactory = Objects.requireNonNull(factory);
            return self();
        }

        public BuilderType setReturnValueVendorFactory(IPkcs11ReturnValue.VendorFactory factory) {
            returnValueVendorFactory = Objects.requireNonNull(factory);
            return self();
        }

        public BuilderType setObjectClassVendorFactory(IPkcs11ObjectClass.VendorFactory factory) {
            objectClassVendorFactory = factory;
            return self();
        }
    }
}
