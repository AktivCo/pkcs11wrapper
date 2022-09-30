package ru.rutoken.pkcs11wrapper.rutoken.main;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Api;
import ru.rutoken.pkcs11wrapper.rutoken.datatype.AttachedCmsVerifyResult;
import ru.rutoken.pkcs11wrapper.rutoken.datatype.DetachedCmsVerifyResult;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkRutokenInitParam;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkTokenInfoExtended;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorX509Store;
import ru.rutoken.pkcs11wrapper.util.Mutable;
import ru.rutoken.pkcs11wrapper.util.MutableLong;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue.CKR_SIGNATURE_INVALID;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11ReturnValue.CKR_CERT_CHAIN_NOT_VERIFIED;

@SuppressWarnings("unused")
public class RtPkcs11Api extends Pkcs11Api {
    public RtPkcs11Api(IRtPkcs11LowLevelApi api) {
        super(api);
    }

    @Nullable
    private static String[] toArray(@Nullable List<String> array) {
        return array != null ? array.toArray(new String[0]) : null;
    }

    @Override
    public IRtPkcs11LowLevelApi getLowLevelApi() {
        return (IRtPkcs11LowLevelApi) super.getLowLevelApi();
    }

    public CkTokenInfoExtended C_EX_GetTokenInfoExtended(long slotId) {
        final Mutable<CkTokenInfoExtended> info = new Mutable<>();
        call(getLowLevelApi().C_EX_GetTokenInfoExtended(slotId, info));
        return info.value;
    }

    public void C_EX_InitToken(long slotId, byte @Nullable [] adminPin, CkRutokenInitParam initInfo) {
        call(getLowLevelApi().C_EX_InitToken(slotId, adminPin, initInfo));
    }

    public void C_EX_UnblockUserPIN(long session) {
        call(getLowLevelApi().C_EX_UnblockUserPIN(session));
    }

    public void C_EX_SetTokenName(long session, byte[] label) {
        call(getLowLevelApi().C_EX_SetTokenName(session, label));
    }

    public void C_EX_SetLicense(long session, long licenseNum, byte[] license) {
        call(getLowLevelApi().C_EX_SetLicense(session, licenseNum, license));
    }

    public void C_EX_GetLicense(long session, long licenseNum, byte[] license, MutableLong licenseLen) {
        call(getLowLevelApi().C_EX_GetLicense(session, licenseNum, license, licenseLen));
    }

    public String C_EX_GetCertificateInfoText(long session, long certificate) {
        final Mutable<byte[]> certificateInfo = new Mutable<>();
        call(getLowLevelApi().C_EX_GetCertificateInfoText(session, certificate, certificateInfo));
        return new String(Objects.requireNonNull(certificateInfo.value));
    }

    public void C_EX_GetTokenName(long session, byte[] label, MutableLong labelLen) {
        call(getLowLevelApi().C_EX_GetTokenName(session, label, labelLen));
    }

    public void C_EX_SetLocalPIN(long slotId, byte[] currentPin, byte[] newLocalPin, long localPinId) {
        call(getLowLevelApi().C_EX_SetLocalPIN(slotId, currentPin, newLocalPin, localPinId));
    }

    public byte[] C_EX_CreateCSR(long session, long publicKey, List<String> dn, long privateKey,
                                 List<String> attributes, List<String> extensions) {
        final Mutable<byte[]> csr = new Mutable<>();
        call(getLowLevelApi().C_EX_CreateCSR(session, publicKey, toArray(dn), csr, privateKey, toArray(attributes),
                toArray(extensions)));
        return csr.value;
    }

    public byte[] C_EX_PKCS7Sign(long session, byte[] data, long signerCertificate, long signerPrivateKey,
                                 long[] additionalCertificates, long flags) {
        final Mutable<byte[]> cms = new Mutable<>();
        call(getLowLevelApi().C_EX_PKCS7Sign(session, data, signerCertificate, cms, signerPrivateKey,
                additionalCertificates, flags));
        return cms.value;
    }

    public void C_EX_PKCS7VerifyInit(long session, byte[] cms, @Nullable CkVendorX509Store store, long mode,
                                     long flags) {
        call(getLowLevelApi().C_EX_PKCS7VerifyInit(session, cms, store, mode, flags));
    }

    public AttachedCmsVerifyResult C_EX_PKCS7Verify(long session) {
        final Mutable<byte[]> data = new Mutable<>();
        final Mutable<List<byte[]>> signerCertificates = new Mutable<>();
        final IPkcs11ReturnValue result =
                callAllowing(getLowLevelApi().C_EX_PKCS7Verify(session, data, signerCertificates),
                        CKR_SIGNATURE_INVALID, CKR_CERT_CHAIN_NOT_VERIFIED);
        return new AttachedCmsVerifyResult(result, data.value, signerCertificates.value);
    }

    public void C_EX_PKCS7VerifyUpdate(long session, byte[] data) {
        call(getLowLevelApi().C_EX_PKCS7VerifyUpdate(session, data));
    }

    public DetachedCmsVerifyResult C_EX_PKCS7VerifyFinal(long session) {
        final Mutable<List<byte[]>> signerCertificates = new Mutable<>();
        final IPkcs11ReturnValue result =
                callAllowing(getLowLevelApi().C_EX_PKCS7VerifyFinal(session, signerCertificates),
                        CKR_SIGNATURE_INVALID, CKR_CERT_CHAIN_NOT_VERIFIED);

        return new DetachedCmsVerifyResult(result, signerCertificates.value);
    }
}
