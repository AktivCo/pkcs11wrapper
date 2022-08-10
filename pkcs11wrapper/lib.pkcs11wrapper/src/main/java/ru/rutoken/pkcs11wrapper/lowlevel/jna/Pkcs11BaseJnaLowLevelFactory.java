package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import com.sun.jna.Pointer;

import ru.rutoken.pkcs11jna.CK_ATTRIBUTE;
import ru.rutoken.pkcs11jna.CK_C_INITIALIZE_ARGS;
import ru.rutoken.pkcs11jna.CK_DATE;
import ru.rutoken.pkcs11jna.CK_MECHANISM;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkCInitializeArgs;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkDate;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanism;
import ru.rutoken.pkcs11wrapper.lowlevel.jna.Pkcs11BaseJnaLowLevelFactory.Builder;
import ru.rutoken.pkcs11wrapper.lowlevel.main.Pkcs11BaseLowLevelFactory;

public abstract class Pkcs11BaseJnaLowLevelFactory<B extends Builder<B>> extends Pkcs11BaseLowLevelFactory<B> {
    protected Pkcs11BaseJnaLowLevelFactory(B builder) {
        super(builder);
    }

    @Override
    public CkCInitializeArgs makeCInitializeArgs() {
        return new CkCInitializeArgsImpl(new CK_C_INITIALIZE_ARGS());
    }

    @Override
    public CkAttribute makeAttribute() {
        return new CkAttributeImpl(new CK_ATTRIBUTE());
    }

    @Override
    public CkMechanism makeMechanism() {
        return new CkMechanismImpl(new CK_MECHANISM());
    }

    @Override
    public CkDate makeDate() {
        return new CkDateImpl(new CK_DATE());
    }

    CkDate makeDate(Pointer pointer) {
        return new CkDateImpl(new CK_DATE(pointer));
    }

    public abstract static class Builder<BuilderType extends Builder<BuilderType>>
            extends Pkcs11BaseLowLevelFactory.Builder<BuilderType> {
    }
}
