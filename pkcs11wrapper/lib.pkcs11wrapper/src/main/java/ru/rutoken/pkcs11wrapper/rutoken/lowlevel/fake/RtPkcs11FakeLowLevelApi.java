package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.fake;

import static java.util.Collections.emptyList;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import ru.rutoken.pkcs11wrapper.lowlevel.fake.FakeSlot;
import ru.rutoken.pkcs11wrapper.lowlevel.fake.FakeToken;
import ru.rutoken.pkcs11wrapper.lowlevel.fake.Pkcs11FakeLowLevelApi;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkTokenInfoExtended;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorX509Store;
import ru.rutoken.pkcs11wrapper.util.Mutable;
import ru.rutoken.pkcs11wrapper.util.MutableLong;

public class RtPkcs11FakeLowLevelApi extends Pkcs11FakeLowLevelApi implements IRtPkcs11LowLevelApi {
    public RtPkcs11FakeLowLevelApi() {
        super(new RtPkcs11FakeLowLevelFactory());
    }

    @Override
    synchronized public long C_EX_GetTokenInfoExtended(long slotId, Mutable<CkTokenInfoExtended> info) {
        final FakeSlot slot = mSlots.get(slotId);
        if (slot == null) return fail();

        final FakeToken token = slot.token;
        if (token == null) return fail();

        info.value = token.getTokenInfoExtended();
        return ok();
    }

    @Override
    synchronized public long C_EX_SetActivationPassword(long slotId, byte[] password) {
        return ok();
    }

    @Override
    synchronized public long C_EX_UnblockUserPIN(long session) {
        return ok();
    }

    @Override
    synchronized public long C_EX_SetTokenName(long session, byte[] label) {
        return ok();
    }

    @Override
    public long C_EX_GetTokenName(long session, byte[] label, MutableLong labelLen) {
        return ok();
    }

    @Override
    public long C_EX_CreateCSR(long session, long publicKey, String[] dn, Mutable<byte[]> csr, long privateKey,
                               String[] attributes, String[] extensions) {
        csr.value = new byte[0];
        return ok();
    }

    @Override
    public long C_EX_PKCS7Sign(long session, byte[] data, long signerCertificate, Mutable<byte[]> cms,
                               long signerPrivateKey, long[] additionalCertificates, long flags) {
        cms.value = new byte[0];
        return ok();
    }

    @Override
    public long C_EX_PKCS7VerifyInit(long session, byte[] cms, @Nullable CkVendorX509Store store, long mode,
                                     long flags) {
        return ok();
    }

    @Override
    public long C_EX_PKCS7Verify(long session, @Nullable Mutable<byte[]> data,
                                 @Nullable Mutable<List<byte[]>> signerCertificates) {
        if (data != null)
            data.value = new byte[0];
        if (signerCertificates != null)
            signerCertificates.value = emptyList();
        return ok();
    }

    @Override
    public long C_EX_PKCS7VerifyUpdate(long session, byte[] data) {
        return ok();
    }

    @Override
    public long C_EX_PKCS7VerifyFinal(long session, @Nullable Mutable<List<byte[]>> signerCertificates) {
        if (signerCertificates != null)
            signerCertificates.value = emptyList();
        return ok();
    }
}
