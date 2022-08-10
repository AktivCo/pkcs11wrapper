package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna;

import ru.rutoken.pkcs11jna.CK_VENDOR_X509_STORE;
import ru.rutoken.pkcs11wrapper.lowlevel.jna.Pkcs11BaseJnaLowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11HardwareFeatureType;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyType;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorX509Store;
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
