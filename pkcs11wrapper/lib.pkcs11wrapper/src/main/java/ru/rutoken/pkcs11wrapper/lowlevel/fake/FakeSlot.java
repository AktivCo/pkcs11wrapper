package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11Flag;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkSlotInfo;

public class FakeSlot {
    @Nullable
    public final FakeToken token;

    FakeSlot(@Nullable FakeToken token) {
        this.token = token;
    }

    CkSlotInfo getSlotInfo() {
        return new FakeCkSlotInfoImpl(token != null ? Pkcs11Flag.CKF_TOKEN_PRESENT.getAsLong() : 0);
    }
}
