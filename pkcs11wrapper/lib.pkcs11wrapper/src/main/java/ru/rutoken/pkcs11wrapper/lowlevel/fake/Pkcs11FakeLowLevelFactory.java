package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import ru.rutoken.pkcs11wrapper.lowlevel.fake.Pkcs11FakeLowLevelFactory.Builder;

public final class Pkcs11FakeLowLevelFactory extends Pkcs11BaseFakeLowLevelFactory<Builder> {
    private Pkcs11FakeLowLevelFactory(Builder builder) {
        super(builder);
    }

    public static class Builder extends Pkcs11BaseFakeLowLevelFactory.Builder<Builder> {
        @Override
        public Pkcs11FakeLowLevelFactory build() {
            return new Pkcs11FakeLowLevelFactory(this);
        }

        @Override
        public Builder self() {
            return this;
        }
    }
}
