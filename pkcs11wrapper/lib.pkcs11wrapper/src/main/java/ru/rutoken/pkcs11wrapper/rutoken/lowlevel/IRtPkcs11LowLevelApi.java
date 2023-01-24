/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel;

import org.jetbrains.annotations.Nullable;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.*;
import ru.rutoken.pkcs11wrapper.util.Mutable;
import ru.rutoken.pkcs11wrapper.util.MutableLong;

import java.util.List;

/**
 * Defines Rutoken C_EX_ (extended) functions.
 */
public interface IRtPkcs11LowLevelApi extends IPkcs11LowLevelApi {
    @Override
    IRtPkcs11LowLevelFactory getLowLevelFactory();

    long C_EX_GetFunctionListExtended(Mutable<CkFunctionListExtended> functionList);

    long C_EX_GetTokenInfoExtended(long slotId, Mutable<CkTokenInfoExtended> info);

    long C_EX_InitToken(long slotId, byte @Nullable [] adminPin, CkRutokenInitParam initInfo);

    long C_EX_GetVolumesInfo(long slotId, CkVolumeInfoExtended @Nullable [] info, MutableLong infoCount);

    long C_EX_GetDriveSize(long slotId, MutableLong driveSize);

    long C_EX_ChangeVolumeAttributes(long slotId, long userType, byte[] pin, long volumeId, long newAccessMode,
                                     boolean permanent);

    long C_EX_FormatDrive(long slotId, long userType, byte[] pin, List<CkVolumeFormatInfoExtended> formatParams);

    long C_EX_TokenManage(long session, long mode, PointerParameter value);

    long C_EX_GetJournal(long slotId, byte @Nullable [] journal, MutableLong journalSize);

    long C_EX_SlotManage(long slotId, long mode, Mutable<PointerParameter> value);

    long C_EX_UnblockUserPIN(long session);

    long C_EX_SetTokenName(long session, byte[] label);

    long C_EX_SetLicense(long session, long licenseNum, byte[] license);

    long C_EX_GetLicense(long session, long licenseNum, byte @Nullable [] license, MutableLong licenseLen);

    long C_EX_GetCertificateInfoText(long session, long certificate, Mutable<byte[]> certificateInfo);

    long C_EX_GetTokenName(long session, byte @Nullable [] label, MutableLong labelLen);

    long C_EX_SetLocalPIN(long slotId, byte[] currentPin, byte[] newLocalPin, long localPinId);

    long C_EX_CreateCSR(long session, long publicKey, @Nullable List<String> dn, Mutable<byte[]> csr, long privateKey,
                        @Nullable List<String> attributes, @Nullable List<String> extensions);

    long C_EX_PKCS7Sign(long session, byte[] data, long signerCertificate, Mutable<byte[]> cms, long signerPrivateKey,
                        long @Nullable [] additionalCertificates, long flags);

    long C_EX_PKCS7VerifyInit(long session, byte[] cms, @Nullable CkVendorX509Store store, long mode, long flags);

    long C_EX_PKCS7Verify(long session, @Nullable Mutable<byte[]> data,
                          @Nullable Mutable<List<byte[]>> signerCertificates);

    long C_EX_PKCS7VerifyUpdate(long session, byte[] data);

    long C_EX_PKCS7VerifyFinal(long session, @Nullable Mutable<List<byte[]>> signerCertificates);

    interface PointerParameter {
    }
}
