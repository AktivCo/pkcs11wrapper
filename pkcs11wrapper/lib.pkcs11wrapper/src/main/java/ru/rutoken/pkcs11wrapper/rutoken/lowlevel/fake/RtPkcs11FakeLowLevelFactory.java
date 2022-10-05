package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.fake;

import ru.rutoken.pkcs11wrapper.lowlevel.fake.Pkcs11BaseFakeLowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11HardwareFeatureType;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyType;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.*;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.fake.RtPkcs11FakeLowLevelFactory.Builder;

public final class RtPkcs11FakeLowLevelFactory extends Pkcs11BaseFakeLowLevelFactory<Builder>
        implements IRtPkcs11LowLevelFactory {
    public RtPkcs11FakeLowLevelFactory() {
        super(new Builder().setReturnValueVendorFactory(new RtPkcs11ReturnValue.Factory())
                .setMechanismTypeVendorFactory(new RtPkcs11MechanismType.Factory())
                .setKeyTypeVendorFactory(new RtPkcs11KeyType.Factory())
                .setAttributeTypeVendorFactory(new RtPkcs11AttributeType.Factory())
                .setHardwareFeatureTypeVendorFactory(new RtPkcs11HardwareFeatureType.Factory()));
    }

    @Override
    public CkVendorX509Store makeVendorX509Store() {
        return new FakeCkVendorX509StoreImpl();
    }

    @Override
    public CkRutokenInitParam makeRutokenInitParam() {
        return new FakeCkRutokenInitParamImpl();
    }

    @Override
    public CkVolumeInfoExtended makeVolumeInfoExtended() {
        return new FakeCkVolumeInfoExtendedImpl();
    }

    @Override
    public CkVolumeFormatInfoExtended makeVolumeFormatInfoExtended() {
        return new FakeCkVolumeFormatInfoExtendedImpl();
    }

    @Override
    public CkVendorPinParams makeVendorPinParams() {
        return new FakeCkVendorPinParamsImpl();
    }

    @Override
    public CkLocalPinInfo makeLocalPinInfo() {
        return new FakeCkLocalPinInfoImpl();
    }

    @Override
    public CkVendorRestoreFactoryDefaultsParams makeVendorRestoreFactoryDefaultsParams() {
        return new FakeCkVendorRestoreFactoryDefaultsParamsImpl();
    }

    /*package*/ static class Builder extends Pkcs11BaseFakeLowLevelFactory.Builder<Builder> {
        @Override
        public RtPkcs11FakeLowLevelFactory build() {
            return new RtPkcs11FakeLowLevelFactory();
        }

        @Override
        public Builder self() {
            return this;
        }
    }
}
