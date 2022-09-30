package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna;

import ru.rutoken.pkcs11jna.*;
import ru.rutoken.pkcs11wrapper.lowlevel.jna.Pkcs11BaseJnaLowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11HardwareFeatureType;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyType;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.*;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna.RtPkcs11JnaLowLevelFactory.Builder;

public final class RtPkcs11JnaLowLevelFactory extends Pkcs11BaseJnaLowLevelFactory<Builder>
        implements IRtPkcs11LowLevelFactory {
    public RtPkcs11JnaLowLevelFactory() {
        // TODO add all vendor defined values
        super(new Builder().setReturnValueVendorFactory(new RtPkcs11ReturnValue.Factory())
                .setMechanismTypeVendorFactory(new RtPkcs11MechanismType.Factory())
                .setKeyTypeVendorFactory(new RtPkcs11KeyType.Factory())
                .setAttributeTypeVendorFactory(new RtPkcs11AttributeType.Factory())
                .setHardwareFeatureTypeVendorFactory(new RtPkcs11HardwareFeatureType.Factory()));
    }

    @Override
    public CkVendorX509Store makeVendorX509Store() {
        return new CkVendorX509StoreImpl(new CK_VENDOR_X509_STORE());
    }

    @Override
    public CkRutokenInitParam makeRutokenInitParam() {
        return new CkRutokenInitParamImpl(new CK_RUTOKEN_INIT_PARAM());
    }

    @Override
    public CkVolumeFormatInfoExtended makeVolumeFormatInfoExtended() {
        return new CkVolumeFormatInfoExtendedImpl(new CK_VOLUME_FORMAT_INFO_EXTENDED());
    }

    @Override
    public CkVendorPinParams makeVendorPinParams() {
        return new CkVendorPinParamsImpl(new CK_VENDOR_PIN_PARAMS());
    }

    @Override
    public CkLocalPinInfo makeLocalPinInfo() {
        return new CkLocalPinInfoImpl(new CK_LOCAL_PIN_INFO());
    }

    @Override
    public CkVendorRestoreFactoryDefaultsParams makeVendorRestoreFactoryDefaultsParams() {
        return new CkVendorRestoreFactoryDefaultsParamsImpl(new CK_VENDOR_RESTORE_FACTORY_DEFAULTS_PARAMS());
    }

    /*package*/ static class Builder extends Pkcs11BaseJnaLowLevelFactory.Builder<Builder> {
        @Override
        public RtPkcs11JnaLowLevelFactory build() {
            return new RtPkcs11JnaLowLevelFactory();
        }

        @Override
        public RtPkcs11JnaLowLevelFactory.Builder self() {
            return this;
        }
    }
}
