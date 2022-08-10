package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import ru.rutoken.pkcs11wrapper.lowlevel.jna.Pkcs11JnaLowLevelFactory.Builder;

public final class Pkcs11JnaLowLevelFactory extends Pkcs11BaseJnaLowLevelFactory<Builder> {
    private Pkcs11JnaLowLevelFactory(Builder builder) {
        super(builder);
    }

    public static class Builder extends Pkcs11BaseJnaLowLevelFactory.Builder<Builder> {
        @Override
        public Pkcs11JnaLowLevelFactory build() {
            return new Pkcs11JnaLowLevelFactory(this);
        }

        @Override
        public Builder self() {
            return this;
        }
    }
}
