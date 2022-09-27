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
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkFunctionListExtended;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkRutokenInitParam;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkTokenInfoExtended;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorX509Store;
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
    static Pointer makePointerFromBytes(byte @Nullable [] data) {
        if (null == data)
            return null;

        Pointer pointer = new Memory(data.length);
        pointer.write(0, data, 0, data.length);

        return pointer;
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
    public long C_EX_InitToken(long slotId, byte[] pin, CkRutokenInitParam initInfo) {
        return unsigned(getRtPkcs11().C_EX_InitToken(new NativeLong(slotId), pin, new NativeLong(pin.length),
                ((CkRutokenInitParamImpl) initInfo).getJnaValue()));
    }

    @Override
    public long C_EX_SetActivationPassword(long slotId, byte[] password) {
        return unsigned(getRtPkcs11().C_EX_SetActivationPassword(new NativeLong(slotId), password));
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
    public long C_EX_GetLicense(long session, long licenseNum, byte[] license, MutableLong licenseLen) {
        NativeLongByReference licenseLenRef = new NativeLongByReference();
        final long result = unsigned(getRtPkcs11().C_EX_GetLicense(new NativeLong(session), new NativeLong(licenseNum),
                license, licenseLenRef));

        if (result == CKR_OK)
            assign(licenseLen, licenseLenRef);

        return result;
    }

    @Override
    public long C_EX_GetTokenName(long session, byte[] label, MutableLong labelLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(labelLen);
        final long result = unsigned(getRtPkcs11().C_EX_GetTokenName(new NativeLong(session), label, lengthRef));
        assign(labelLen, lengthRef);
        return result;
    }

    @Override
    public long C_EX_SetLocalPIN(long slotId, byte[] userPin, byte[] newLocalPin, long localId) {
        return unsigned(getRtPkcs11().C_EX_SetLocalPIN(new NativeLong(slotId), userPin, new NativeLong(userPin.length),
                newLocalPin, new NativeLong(newLocalPin.length), new NativeLong(localId)));
    }

    @Override
    public long C_EX_CreateCSR(long session, long publicKey, String[] dn, Mutable<byte[]> csr, long privateKey,
                               String[] attributes, String[] extensions) {
        final PointerByReference csrPointerRef = new PointerByReference();
        final NativeLongByReference csrLengthRef = new NativeLongByReference();

        final long result = unsigned(getRtPkcs11().C_EX_CreateCSR(
                new NativeLong(session),
                new NativeLong(publicKey),
                dn, length(dn),
                csrPointerRef, csrLengthRef,
                new NativeLong(privateKey),
                attributes, length(attributes),
                extensions, length(extensions)
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

    @Override
    public long C_EX_PKCS7Sign(long session, byte[] data, long signerCertificate, Mutable<byte[]> cms,
                               long signerPrivateKey, long[] additionalCertificates, long flags) {
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
