package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.fake;

import static java.util.Collections.emptyList;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import ru.rutoken.pkcs11wrapper.lowlevel.fake.FakeSlot;
import ru.rutoken.pkcs11wrapper.lowlevel.fake.FakeToken;
import ru.rutoken.pkcs11wrapper.lowlevel.fake.Pkcs11FakeLowLevelApi;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.*;
import ru.rutoken.pkcs11wrapper.util.Mutable;
import ru.rutoken.pkcs11wrapper.util.MutableLong;

public class RtPkcs11FakeLowLevelApi extends Pkcs11FakeLowLevelApi implements IRtPkcs11LowLevelApi {
    public RtPkcs11FakeLowLevelApi() {
        super(new RtPkcs11FakeLowLevelFactory());
    }

    @Override
    public IRtPkcs11LowLevelFactory getLowLevelFactory() {
        return (IRtPkcs11LowLevelFactory) super.getLowLevelFactory();
    }

    @Override
    public long C_EX_GetFunctionListExtended(Mutable<CkFunctionListExtended> functionList) {
        return ok();
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
    synchronized public long C_EX_InitToken(long slotId, byte @Nullable [] adminPin, CkRutokenInitParam initInfo) {
        return ok();
    }

    @Override
    public long C_EX_GetVolumesInfo(long slotId, CkVolumeInfoExtended @Nullable [] info, MutableLong infoCount) {
        return ok();
    }

    @Override
    public long C_EX_GetDriveSize(long slotId, MutableLong driveSize) {
        return ok();
    }

    @Override
    public long C_EX_ChangeVolumeAttributes(long slotId, long userType, byte[] pin, long volumeId, long newAccessMode,
                                            boolean permanent) {
        return ok();
    }

    @Override
    public long C_EX_FormatDrive(long slotId, long userType, byte[] pin,
                                 List<CkVolumeFormatInfoExtended> formatParams) {
        return ok();
    }

    @Override
    public long C_EX_TokenManage(long session, long mode, PointerParameter value) {
        return ok();
    }

    @Override
    public long C_EX_GetJournal(long slotId, byte @Nullable [] journal, MutableLong journalSize) {
        return ok();
    }

    @Override
    public long C_EX_SlotManage(long slotId, long mode, PointerParameter value) {
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
    public long C_EX_SetLicense(long session, long licenseNum, byte[] license) {
        return ok();
    }

    @Override
    public long C_EX_GetLicense(long session, long licenseNum, byte @Nullable [] license, MutableLong licenseLen) {
        return ok();
    }

    @Override
    public long C_EX_GetCertificateInfoText(long session, long certificate, Mutable<byte[]> certificateInfo) {
        certificateInfo.value = new byte[0];
        return ok();
    }

    @Override
    public long C_EX_GetTokenName(long session, byte @Nullable [] label, MutableLong labelLen) {
        return ok();
    }

    @Override
    public long C_EX_SetLocalPIN(long slotId, byte[] currentPin, byte[] newLocalPin, long localPinId) {
        return ok();
    }

    @Override
    public long C_EX_CreateCSR(long session, long publicKey, @Nullable List<String> dn, Mutable<byte[]> csr,
                               long privateKey, @Nullable List<String> attributes, @Nullable List<String> extensions) {
        csr.value = new byte[0];
        return ok();
    }

    @Override
    public long C_EX_PKCS7Sign(long session, byte[] data, long signerCertificate, Mutable<byte[]> cms,
                               long signerPrivateKey, long @Nullable [] additionalCertificates, long flags) {
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
