/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkCInitializeArgs;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkDate;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanism;
import ru.rutoken.pkcs11wrapper.lowlevel.fake.Pkcs11BaseFakeLowLevelFactory.Builder;
import ru.rutoken.pkcs11wrapper.lowlevel.main.Pkcs11BaseLowLevelFactory;

public abstract class Pkcs11BaseFakeLowLevelFactory<B extends Builder<B>> extends Pkcs11BaseLowLevelFactory<B> {
    protected Pkcs11BaseFakeLowLevelFactory(B builder) {
        super(builder);
    }

    @Override
    public CkCInitializeArgs makeCInitializeArgs() {
        return new FakeCkCInitializeArgsImpl();
    }

    @Override
    public CkAttribute makeAttribute() {
        return new FakeCkAttributeImpl();
    }

    @Override
    public CkMechanism makeMechanism() {
        return new FakeCkMechanismImpl();
    }

    @Override
    public CkDate makeDate() {
        return new FakeCkDateImpl();
    }

    public abstract static class Builder<BuilderType extends Builder<BuilderType>>
            extends Pkcs11BaseLowLevelFactory.Builder<BuilderType> {
    }
}
