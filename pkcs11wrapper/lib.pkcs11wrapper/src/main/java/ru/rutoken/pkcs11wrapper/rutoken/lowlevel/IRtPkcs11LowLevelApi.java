package ru.rutoken.pkcs11wrapper.rutoken.lowlevel;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkTokenInfoExtended;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorX509Store;
import ru.rutoken.pkcs11wrapper.util.Mutable;
import ru.rutoken.pkcs11wrapper.util.MutableLong;

/**
 * Defines Rutoken C_EX_ (extended) functions.
 */
// TODO add all extended Rutoken functions support
public interface IRtPkcs11LowLevelApi extends IPkcs11LowLevelApi {
    long C_EX_GetTokenInfoExtended(long slotId, Mutable<CkTokenInfoExtended> info);

    long C_EX_SetActivationPassword(long slotId, byte[] password);

    long C_EX_UnblockUserPIN(long session);

    long C_EX_SetTokenName(long session, byte[] label);

    long C_EX_GetTokenName(long session, byte[] label, MutableLong labelLen);

    long C_EX_CreateCSR(long session, long publicKey, String[] dn, Mutable<byte[]> csr, long privateKey,
                        String[] attributes, String[] extensions);

    long C_EX_PKCS7Sign(long session, byte[] data, long signerCertificate, Mutable<byte[]> cms, long signerPrivateKey,
                        long[] additionalCertificates, long flags);

    long C_EX_PKCS7VerifyInit(long session, byte[] cms, @Nullable CkVendorX509Store store, long mode, long flags);

    long C_EX_PKCS7Verify(long session, @Nullable Mutable<byte[]> data,
                          @Nullable Mutable<List<byte[]>> signerCertificates);

    long C_EX_PKCS7VerifyUpdate(long session, byte[] data);

    long C_EX_PKCS7VerifyFinal(long session, @Nullable Mutable<List<byte[]>> signerCertificates);
}
