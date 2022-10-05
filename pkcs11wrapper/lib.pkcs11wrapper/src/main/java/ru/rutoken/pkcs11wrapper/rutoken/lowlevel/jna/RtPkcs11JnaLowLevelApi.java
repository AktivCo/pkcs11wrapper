package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna;

import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11jna.*;
import ru.rutoken.pkcs11wrapper.lowlevel.jna.Pkcs11JnaLowLevelApi;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.*;
import ru.rutoken.pkcs11wrapper.util.Mutable;
import ru.rutoken.pkcs11wrapper.util.MutableLong;

import java.util.ArrayList;
import java.util.List;

import static ru.rutoken.pkcs11jna.Pkcs11Constants.CKR_OK;

public class RtPkcs11JnaLowLevelApi extends Pkcs11JnaLowLevelApi implements IRtPkcs11LowLevelApi {
    public RtPkcs11JnaLowLevelApi(RtPkcs11 jnaPkcs11, IRtPkcs11LowLevelFactory lowLevelFactory) {
        super(jnaPkcs11, lowLevelFactory);
    }

    @Nullable
    private static CK_VENDOR_X509_STORE convertCkVendorX509Store(@Nullable CkVendorX509Store store) {
        return store != null ? ((CkVendorX509StoreImpl) store).getJnaValue() : null;
    }

    @Nullable
    private static CK_VOLUME_INFO_EXTENDED[] convertCkVolumeInfoExtendedArray(CkVolumeInfoExtended @Nullable [] info) {
        if (info == null)
            return null;

        final CK_VOLUME_INFO_EXTENDED[] ckInfo =
                (CK_VOLUME_INFO_EXTENDED[]) new CK_VOLUME_INFO_EXTENDED().toArray(info.length);
        for (int i = 0; i < info.length; ++i) {
            ((CkVolumeInfoExtendedImpl) info[i]).copyToJnaStructure(ckInfo[i]);
        }

        return ckInfo;
    }

    private static void assignCkVolumeInfoExtendedArray(CkVolumeInfoExtended @Nullable [] info,
                                                        CK_VOLUME_INFO_EXTENDED @Nullable [] nativeInfo) {
        if (info == null && nativeInfo == null)
            return;

        if ((info != null && nativeInfo != null) && info.length == nativeInfo.length) {
            for (int i = 0; i < nativeInfo.length; i++) {
                info[i] = new CkVolumeInfoExtendedImpl(nativeInfo[i]);
            }
        } else {
            throw new IllegalArgumentException("Arrays are not in the same state");
        }
    }

    @Nullable
    private static CK_VOLUME_FORMAT_INFO_EXTENDED[] convertCkVolumeFormatInfoExtendedList(
            List<CkVolumeFormatInfoExtended> info) {
        if (info.isEmpty())
            return null;

        final CK_VOLUME_FORMAT_INFO_EXTENDED[] nativeInfo =
                (CK_VOLUME_FORMAT_INFO_EXTENDED[]) new CK_VOLUME_FORMAT_INFO_EXTENDED().toArray(info.size());
        for (int i = 0; i < info.size(); i++) {
            ((CkVolumeFormatInfoExtendedImpl) info.get(i)).copyToJnaStructure(nativeInfo[i]);
        }

        return nativeInfo;
    }

    @Nullable
    private static String[] toArray(@Nullable List<String> array) {
        return array != null ? array.toArray(new String[0]) : null;
    }

    @Nullable
    static Pointer makePointerFromBytes(byte @Nullable [] data) {
        if (null == data)
            return null;

        Pointer pointer = new Memory(data.length);
        pointer.write(0, data, 0, data.length);

        return pointer;
    }

    @Override
    public IRtPkcs11LowLevelFactory getLowLevelFactory() {
        return (IRtPkcs11LowLevelFactory) super.getLowLevelFactory();
    }

    @Override
    public long C_EX_GetFunctionListExtended(Mutable<CkFunctionListExtended> functionList) {
        final PointerByReference functionListPointerRef = new PointerByReference();
        final long result = unsigned(getRtPkcs11().C_EX_GetFunctionListExtended(functionListPointerRef));

        if (result == CKR_OK) {
            final Pointer functionListPointer = functionListPointerRef.getValue();
            if (functionListPointer != null) {
                CK_FUNCTION_LIST_EXTENDED ckFunctionList = new CK_FUNCTION_LIST_EXTENDED(functionListPointer);
                functionList.value = new CkFunctionListExtendedImpl(ckFunctionList);
            }
        }

        return result;
    }

    @Override
    public long C_EX_GetTokenInfoExtended(long slotId, Mutable<CkTokenInfoExtended> info) {
        final CK_TOKEN_INFO_EXTENDED ckInfo = new CK_TOKEN_INFO_EXTENDED();
        ckInfo.ulSizeofThisStructure = new NativeLong(ckInfo.size());

        final long result = unsigned(getRtPkcs11().C_EX_GetTokenInfoExtended(new NativeLong(slotId), ckInfo));
        info.value = new CkTokenInfoExtendedImpl(ckInfo);
        return result;
    }

    @Override
    public long C_EX_InitToken(long slotId, byte @Nullable [] adminPin, CkRutokenInitParam initInfo) {
        return unsigned(getRtPkcs11().C_EX_InitToken(new NativeLong(slotId), adminPin, length(adminPin),
                ((CkRutokenInitParamImpl) initInfo).getJnaValue()));
    }

    @Override
    public long C_EX_GetVolumesInfo(long slotId, CkVolumeInfoExtended @Nullable [] info, MutableLong infoCount) {
        NativeLongByReference infoCountRef = makeNativeLongRef(infoCount);
        final CK_VOLUME_INFO_EXTENDED[] nativeInfo = convertCkVolumeInfoExtendedArray(info);
        final long result = unsigned(getRtPkcs11().C_EX_GetVolumesInfo(new NativeLong(slotId), nativeInfo,
                infoCountRef));
        assign(infoCount, infoCountRef);
        if (result == CKR_OK)
            assignCkVolumeInfoExtendedArray(info, nativeInfo);
        return result;
    }

    @Override
    public long C_EX_GetDriveSize(long slotId, MutableLong driveSize) {
        final NativeLongByReference driveSizeRef = makeNativeLongRef(driveSize);
        final long result = unsigned(getRtPkcs11().C_EX_GetDriveSize(new NativeLong(slotId), driveSizeRef));
        assign(driveSize, driveSizeRef);
        return result;
    }

    @Override
    public long C_EX_ChangeVolumeAttributes(long slotId, long userType, byte[] pin, long volumeId, long newAccessMode,
                                            boolean permanent) {
        return unsigned(getRtPkcs11().C_EX_ChangeVolumeAttributes(new NativeLong(slotId), new NativeLong(userType), pin,
                new NativeLong(pin.length), new NativeLong(volumeId), new NativeLong(newAccessMode),
                (byte) (permanent ? 1 : 0)));
    }

    @Override
    public long C_EX_FormatDrive(long slotId, long userType, byte[] pin,
                                 List<CkVolumeFormatInfoExtended> formatParams) {
        return unsigned(getRtPkcs11().C_EX_FormatDrive(new NativeLong(slotId), new NativeLong(userType), pin,
                new NativeLong(pin.length), convertCkVolumeFormatInfoExtendedList(formatParams),
                new NativeLong(formatParams.size())));
    }

    @Override
    public long C_EX_TokenManage(long session, long mode, PointerParameter value) {
        return unsigned(getRtPkcs11().C_EX_TokenManage(new NativeLong(session), new NativeLong(mode),
                value == null ? null : ((JnaPointerParameterImpl) value).getPointer()));
    }

    @Override
    public long C_EX_GetJournal(long slotId, byte @Nullable [] journal, MutableLong journalSize) {
        NativeLongByReference journalSizeRef = makeNativeLongRef(journalSize);
        final long result = unsigned(getRtPkcs11().C_EX_GetJournal(new NativeLong(slotId), journal, journalSizeRef));
        assign(journalSize, journalSizeRef);
        return result;
    }

    @Override
    public long C_EX_SlotManage(long slotId, long mode, PointerParameter value) {
        return unsigned(getRtPkcs11().C_EX_SlotManage(new NativeLong(slotId), new NativeLong(mode),
                value == null ? null : ((JnaPointerParameterImpl) value).getPointer()));
    }

    @Override
    public long C_EX_UnblockUserPIN(long session) {
        return unsigned(getRtPkcs11().C_EX_UnblockUserPIN(new NativeLong(session)));
    }

    @Override
    public long C_EX_SetTokenName(long session, byte[] label) {
        return unsigned(getRtPkcs11().C_EX_SetTokenName(new NativeLong(session), label, length(label)));
    }

    @Override
    public long C_EX_SetLicense(long session, long licenseNum, byte[] license) {
        return unsigned(getRtPkcs11().C_EX_SetLicense(new NativeLong(session), new NativeLong(licenseNum),
                license, new NativeLong(license.length)));
    }

    @Override
    public long C_EX_GetLicense(long session, long licenseNum, byte @Nullable [] license, MutableLong licenseLen) {
        NativeLongByReference licenseLenRef = makeNativeLongRef(licenseLen);
        final long result = unsigned(getRtPkcs11().C_EX_GetLicense(new NativeLong(session), new NativeLong(licenseNum),
                license, licenseLenRef));
        assign(licenseLen, licenseLenRef);
        return result;
    }

    /**
     * C_EX_FreeBuffer is called in this implementation.
     */
    @Override
    public long C_EX_GetCertificateInfoText(long session, long certificate, Mutable<byte[]> certificateInfo) {
        final PointerByReference certificateInfoPointerRef = new PointerByReference();
        final NativeLongByReference certificateInfoLengthRef = new NativeLongByReference();

        final long result = unsigned(getRtPkcs11().C_EX_GetCertificateInfoText(new NativeLong(session),
                new NativeLong(certificate), certificateInfoPointerRef, certificateInfoLengthRef));

        if (result == CKR_OK) {
            final Pointer certificateInfoPointer = certificateInfoPointerRef.getValue();
            if (certificateInfoPointer != null) {
                certificateInfo.value =
                        certificateInfoPointer.getByteArray(0, certificateInfoLengthRef.getValue().intValue());
                getRtPkcs11().C_EX_FreeBuffer(certificateInfoPointer);
            }
        }

        return result;
    }

    @Override
    public long C_EX_GetTokenName(long session, byte @Nullable [] label, MutableLong labelLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(labelLen);
        final long result = unsigned(getRtPkcs11().C_EX_GetTokenName(new NativeLong(session), label, lengthRef));
        assign(labelLen, lengthRef);
        return result;
    }

    @Override
    public long C_EX_SetLocalPIN(long slotId, byte[] currentPin, byte[] newLocalPin, long localPinId) {
        return unsigned(getRtPkcs11().C_EX_SetLocalPIN(new NativeLong(slotId), currentPin,
                new NativeLong(currentPin.length), newLocalPin, new NativeLong(newLocalPin.length),
                new NativeLong(localPinId)));
    }

    /**
     * C_EX_FreeBuffer is called in this implementation.
     */
    @Override
    public long C_EX_CreateCSR(long session, long publicKey, @Nullable List<String> dn, Mutable<byte[]> csr,
                               long privateKey, @Nullable List<String> attributes, @Nullable List<String> extensions) {
        final PointerByReference csrPointerRef = new PointerByReference();
        final NativeLongByReference csrLengthRef = new NativeLongByReference();

        final long result = unsigned(getRtPkcs11().C_EX_CreateCSR(
                new NativeLong(session),
                new NativeLong(publicKey),
                toArray(dn), length(dn),
                csrPointerRef, csrLengthRef,
                new NativeLong(privateKey),
                toArray(attributes), length(attributes),
                toArray(extensions), length(extensions)
        ));

        if (result == CKR_OK) {
            final Pointer csrPointer = csrPointerRef.getValue();
            if (csrPointer != null) {
                csr.value = csrPointer.getByteArray(0, csrLengthRef.getValue().intValue());
                getRtPkcs11().C_EX_FreeBuffer(csrPointer);
            }
        }

        return result;
    }

    /**
     * C_EX_FreeBuffer is called in this implementation.
     */
    @Override
    public long C_EX_PKCS7Sign(long session, byte[] data, long signerCertificate, Mutable<byte[]> cms,
                               long signerPrivateKey, long @Nullable [] additionalCertificates, long flags) {
        final PointerByReference envelopePointerRef = new PointerByReference();
        final NativeLongByReference envelopeLengthRef = new NativeLongByReference();

        final long result = unsigned(getRtPkcs11().C_EX_PKCS7Sign(
                new NativeLong(session),
                data, length(data),
                new NativeLong(signerCertificate),
                envelopePointerRef, envelopeLengthRef,
                new NativeLong(signerPrivateKey),
                convertLongArray(additionalCertificates), length(additionalCertificates),
                new NativeLong(flags)
        ));

        if (result == CKR_OK) {
            final Pointer envelopePointer = envelopePointerRef.getValue();
            if (envelopePointer != null) {
                cms.value = envelopePointer.getByteArray(0, envelopeLengthRef.getValue().intValue());
                getRtPkcs11().C_EX_FreeBuffer(envelopePointer);
            }
        }

        return result;
    }

    @Override
    public long C_EX_PKCS7VerifyInit(long session, byte[] cms, @Nullable CkVendorX509Store store, long mode,
                                     long flags) {
        // We do not save this pointer (to avoid getting cleaned up by GC) because we have verified that pkcs11ecp
        // is working properly in this case.
        final Pointer cmsPointer = makePointerFromBytes(cms);
        final NativeLong cmsLength = length(cms);

        return unsigned(getRtPkcs11().C_EX_PKCS7VerifyInit(
                new NativeLong(session),
                cmsPointer, cmsLength,
                convertCkVendorX509Store(store),
                new NativeLong(mode),
                new NativeLong(flags)
        ));
    }

    /**
     * C_EX_FreeBuffer is called in this implementation.
     */
    @Override
    public long C_EX_PKCS7Verify(long session, @Nullable Mutable<byte[]> data,
                                 @Nullable Mutable<List<byte[]>> signerCertificates) {
        final PointerByReference dataPointerRef = data == null ? null : new PointerByReference();
        final NativeLongByReference dataLengthRef = data == null ? null : new NativeLongByReference();
        final PointerByReference signerCertificatesPointerRef = signerCertificates == null ? null :
                new PointerByReference();
        final NativeLongByReference signerCertificatesLengthRef = signerCertificates == null ? null :
                new NativeLongByReference();

        final long result = unsigned(getRtPkcs11().C_EX_PKCS7Verify(
                new NativeLong(session),
                dataPointerRef, dataLengthRef,
                signerCertificatesPointerRef, signerCertificatesLengthRef
        ));

        if (result == CKR_OK) {
            if (dataPointerRef != null) {
                final Pointer dataPointer = dataPointerRef.getValue();
                if (dataPointer != null) {
                    data.value = dataPointer.getByteArray(0, dataLengthRef.getValue().intValue());
                    getRtPkcs11().C_EX_FreeBuffer(dataPointer);
                }
            }

            if (signerCertificatesPointerRef != null) {
                final Pointer signerCertificatesPointer = signerCertificatesPointerRef.getValue();
                if (signerCertificatesPointer != null) {
                    signerCertificates.value = extractSignerCertificatesValue(signerCertificatesPointer,
                            signerCertificatesLengthRef.getValue());
                    getRtPkcs11().C_EX_FreeBuffer(signerCertificatesPointer);
                }
            }
        }

        return result;
    }

    @Override
    public long C_EX_PKCS7VerifyUpdate(long session, byte[] data) {
        return unsigned(getRtPkcs11().C_EX_PKCS7VerifyUpdate(new NativeLong(session), data, length(data)));
    }

    /**
     * C_EX_FreeBuffer is called in this implementation.
     */
    @Override
    public long C_EX_PKCS7VerifyFinal(long session, @Nullable Mutable<List<byte[]>> signerCertificates) {
        final PointerByReference signerCertificatesPointerRef = signerCertificates == null ? null :
                new PointerByReference();
        final NativeLongByReference signerCertificatesLengthRef = signerCertificates == null ? null :
                new NativeLongByReference();

        final long result = unsigned(getRtPkcs11().C_EX_PKCS7VerifyFinal(
                new NativeLong(session),
                signerCertificatesPointerRef, signerCertificatesLengthRef
        ));

        if (result == CKR_OK) {
            if (signerCertificatesPointerRef != null) {
                final Pointer signerCertificatesPointer = signerCertificatesPointerRef.getValue();
                if (signerCertificatesPointer != null) {
                    signerCertificates.value = extractSignerCertificatesValue(signerCertificatesPointer,
                            signerCertificatesLengthRef.getValue());
                    getRtPkcs11().C_EX_FreeBuffer(signerCertificatesPointer);
                }
            }
        }

        return result;
    }

    private RtPkcs11 getRtPkcs11() {
        return (RtPkcs11) mJnaPkcs11;
    }

    /**
     * Gets signer certificates value from signerCertificatesPointer and frees its memory using C_EX_FreeBuffer.
     */
    private List<byte[]> extractSignerCertificatesValue(Pointer signerCertificatesPointer,
                                                        NativeLong signerCertificatesLength) {
        CK_VENDOR_BUFFER firstSignerCertificate = new CK_VENDOR_BUFFER(signerCertificatesPointer);
        CK_VENDOR_BUFFER[] signerCertificates = (CK_VENDOR_BUFFER[]) firstSignerCertificate
                .toArray(signerCertificatesLength.intValue());

        List<byte[]> signerCertificatesValue = new ArrayList<>();
        for (CK_VENDOR_BUFFER buffer : signerCertificates) {
            signerCertificatesValue.add(buffer.pData.getByteArray(0, buffer.ulSize.intValue()));
            getRtPkcs11().C_EX_FreeBuffer(buffer.pData);
        }

        return signerCertificatesValue;
    }
}
