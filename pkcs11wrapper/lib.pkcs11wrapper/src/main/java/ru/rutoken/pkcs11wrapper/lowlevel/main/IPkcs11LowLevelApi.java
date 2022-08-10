package ru.rutoken.pkcs11wrapper.lowlevel.main;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkCInitializeArgs;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkFunctionList;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkInfo;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanism;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanismInfo;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkSessionInfo;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkSlotInfo;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkTokenInfo;
import ru.rutoken.pkcs11wrapper.main.CkNotify;
import ru.rutoken.pkcs11wrapper.reference.LowLevelFactoryReference;
import ru.rutoken.pkcs11wrapper.util.Mutable;
import ru.rutoken.pkcs11wrapper.util.MutableLong;

/**
 * Declares low-level pkcs11 interface as similar to a C library interface as possible.
 * This interface hides issues related to interaction with a C library from Java.
 * Implementations of this class may use JNA/JNI or something else to call pkcs11 library.
 */
public interface IPkcs11LowLevelApi extends LowLevelFactoryReference {

    long C_Initialize(@Nullable CkCInitializeArgs initArgs);

    long C_Finalize(Object reserved);

    long C_GetInfo(Mutable<CkInfo> info);

    long C_GetFunctionList(Mutable<CkFunctionList> functionList);

    long C_GetSlotList(byte tokenPresent, long[] slotList, MutableLong count);

    long C_GetSlotInfo(long slotId, Mutable<CkSlotInfo> info);

    long C_GetTokenInfo(long slotId, Mutable<CkTokenInfo> info);

    long C_GetMechanismList(long slotId, long[] mechanismList, MutableLong count);

    long C_GetMechanismInfo(long slotId, long type, Mutable<CkMechanismInfo> info);

    long C_InitToken(long slotId, byte @Nullable [] pin, byte[] label);

    long C_InitPIN(long slotId, byte @Nullable [] pin);

    long C_SetPIN(long slotId, byte @Nullable [] oldPin, byte @Nullable [] newPin);

    long C_OpenSession(long slotId, long flags, Object application, CkNotify notify, MutableLong session);

    long C_CloseSession(long session);

    long C_CloseAllSessions(long slotId);

    long C_GetSessionInfo(long session, Mutable<CkSessionInfo> info);

    long C_GetOperationState(long session, byte[] operationState, MutableLong length);

    long C_SetOperationState(long session, byte[] operationState, long encryptionKey, long authenticationKey);

    long C_Login(long session, long userType, byte @Nullable [] pin);

    long C_Logout(long session);

    long C_CreateObject(long session, List<CkAttribute> template, MutableLong object);

    long C_CopyObject(long session, long object, List<CkAttribute> template, MutableLong newObject);

    long C_DestroyObject(long session, long object);

    long C_GetObjectSize(long session, long object, MutableLong size);

    long C_GetAttributeValue(long session, long object, List<CkAttribute> template);

    long C_SetAttributeValue(long session, long object, List<CkAttribute> template);

    long C_FindObjectsInit(long session, List<CkAttribute> template);

    long C_FindObjects(long session, long[] objects, long maxCount, MutableLong count);

    long C_FindObjectsFinal(long session);

    long C_EncryptInit(long session, CkMechanism mechanism, long key);

    long C_Encrypt(long session, byte[] data, byte[] encryptedData, MutableLong encryptedDataLen);

    long C_EncryptUpdate(long session, byte[] part, byte[] encryptedPart, MutableLong encryptedPartLen);

    long C_EncryptFinal(long session, byte[] lastEncryptedPart, MutableLong lastEncryptedPartLen);

    long C_DecryptInit(long session, CkMechanism mechanism, long key);

    long C_Decrypt(long session, byte[] encryptedData, byte[] data, MutableLong dataLen);

    long C_DecryptUpdate(long session, byte[] encryptedPart, byte[] part, MutableLong partLen);

    long C_DecryptFinal(long session, byte[] lastPart, MutableLong lastPartLen);

    long C_DigestInit(long session, CkMechanism mechanism);

    long C_Digest(long session, byte[] data, byte[] digest, MutableLong digestLen);

    long C_DigestUpdate(long session, byte[] part);

    long C_DigestKey(long session, long key);

    long C_DigestFinal(long session, byte[] digest, MutableLong digestLen);

    long C_SignInit(long session, CkMechanism mechanism, long key);

    long C_Sign(long session, byte[] data, byte[] signature, MutableLong signatureLen);

    long C_SignUpdate(long session, byte[] part);

    long C_SignFinal(long session, byte[] signature, MutableLong signatureLen);

    long C_SignRecoverInit(long session, CkMechanism mechanism, long key);

    long C_SignRecover(long session, byte[] data, byte[] signature, MutableLong signatureLen);

    long C_VerifyInit(long session, CkMechanism mechanism, long key);

    long C_Verify(long session, byte[] data, byte[] signature);

    long C_VerifyUpdate(long session, byte[] part);

    long C_VerifyFinal(long session, byte[] signature);

    long C_VerifyRecoverInit(long session, CkMechanism mechanism, long key);

    long C_VerifyRecover(long session, byte[] signature, byte[] data, MutableLong dataLen);

    long C_DigestEncryptUpdate(long session, byte[] part, byte[] encryptedPart, MutableLong encryptedPartLen);

    long C_DecryptDigestUpdate(long session, byte[] encryptedPart, byte[] part, MutableLong partLen);

    long C_SignEncryptUpdate(long session, byte[] part, byte[] encryptedPart, MutableLong encryptedPartLen);

    long C_DecryptVerifyUpdate(long session, byte[] encryptedPart, byte[] part, MutableLong partLen);

    long C_GenerateKey(long session, CkMechanism mechanism, List<CkAttribute> template, MutableLong key);

    long C_GenerateKeyPair(long session, CkMechanism mechanism, List<CkAttribute> publicKeyTemplate,
                           List<CkAttribute> privateKeyTemplate, MutableLong publicKey, MutableLong privateKey);

    long C_WrapKey(long session, CkMechanism mechanism, long wrappingKey, long key, byte[] wrappedKey,
                   MutableLong wrappedKeyLen);

    long C_UnwrapKey(long session, CkMechanism mechanism, long unwrappingKey, byte[] wrappedKey,
                     List<CkAttribute> template, MutableLong key);

    long C_DeriveKey(long session, CkMechanism mechanism, long baseKey, List<CkAttribute> template, MutableLong key);

    long C_SeedRandom(long session, byte[] seed);

    long C_GenerateRandom(long session, byte[] randomData);

    @Deprecated
    long C_GetFunctionStatus(long session);

    @Deprecated
    long C_CancelFunction(long session);

    long C_WaitForSlotEvent(long flags, MutableLong slot, Object reserved);
}
