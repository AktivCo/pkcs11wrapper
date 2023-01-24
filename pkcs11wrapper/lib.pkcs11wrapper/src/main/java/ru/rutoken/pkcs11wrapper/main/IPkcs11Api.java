/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.main;

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
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.reference.LowLevelApiReference;
import ru.rutoken.pkcs11wrapper.util.MutableLong;

/**
 * Defines pkcs11 interface in Java way. It's just a simple wrapper around
 * {@link IPkcs11LowLevelApi}, adding exceptions and method return values.
 * This class is part of high-level API, and is used for making pkcs11 calls simpler on OOP high-level layer.
 */
public interface IPkcs11Api extends LowLevelApiReference {
    void C_Initialize(@Nullable CkCInitializeArgs initArgs);

    void C_Finalize(Object reserved);

    CkInfo C_GetInfo();

    CkFunctionList C_GetFunctionList();

    void C_GetSlotList(byte tokenPresent, long[] slotList, MutableLong count);

    CkSlotInfo C_GetSlotInfo(long slotId);

    CkTokenInfo C_GetTokenInfo(long slotId);

    void C_GetMechanismList(long slotId, long[] mechanismList, MutableLong count);

    CkMechanismInfo C_GetMechanismInfo(long slotId, long type);

    void C_InitToken(long slotId, byte @Nullable [] pin, byte[] label);

    void C_InitPIN(long slotId, byte @Nullable [] pin);

    void C_SetPIN(long slotId, byte @Nullable [] oldPin, byte @Nullable [] newPin);

    long C_OpenSession(long slotId, long flags, Object application, CkNotify notify);

    void C_CloseSession(long session);

    void C_CloseAllSessions(long slotId);

    CkSessionInfo C_GetSessionInfo(long session);

    void C_GetOperationState(long session, byte[] operationState, MutableLong length);

    void C_SetOperationState(long session, byte[] operationState, long encryptionKey, long authenticationKey);

    void C_Login(long session, long userType, byte @Nullable [] pin);

    void C_Logout(long session);

    long C_CreateObject(long session, List<CkAttribute> template);

    long C_CopyObject(long session, long object, List<CkAttribute> template);

    void C_DestroyObject(long session, long object);

    long C_GetObjectSize(long session, long object);

    void C_GetAttributeValue(long session, long object, List<CkAttribute> template);

    void C_SetAttributeValue(long session, long object, List<CkAttribute> template);

    void C_FindObjectsInit(long session, List<CkAttribute> template);

    void C_FindObjects(long session, long[] objects, long maxCount, MutableLong count);

    void C_FindObjectsFinal(long session);

    void C_EncryptInit(long session, CkMechanism mechanism, long key);

    void C_Encrypt(long session, byte[] data, byte[] encryptedData, MutableLong encryptedDataLen);

    void C_EncryptUpdate(long session, byte[] part, byte[] encryptedPart, MutableLong encryptedPartLen);

    void C_EncryptFinal(long session, byte[] lastEncryptedPart, MutableLong lastEncryptedPartLen);

    void C_DecryptInit(long session, CkMechanism mechanism, long key);

    void C_Decrypt(long session, byte[] encryptedData, byte[] data, MutableLong dataLen);

    void C_DecryptUpdate(long session, byte[] encryptedPart, byte[] part, MutableLong partLen);

    void C_DecryptFinal(long session, byte[] lastPart, MutableLong lastPartLen);

    void C_DigestInit(long session, CkMechanism mechanism);

    void C_Digest(long session, byte[] data, byte[] digest, MutableLong digestLen);

    void C_DigestUpdate(long session, byte[] part);

    void C_DigestKey(long session, long key);

    void C_DigestFinal(long session, byte[] digest, MutableLong digestLen);

    void C_SignInit(long session, CkMechanism mechanism, long key);

    void C_Sign(long session, byte[] data, byte[] signature, MutableLong signatureLen);

    void C_SignUpdate(long session, byte[] part);

    void C_SignFinal(long session, byte[] signature, MutableLong signatureLen);

    void C_SignRecoverInit(long session, CkMechanism mechanism, long key);

    void C_SignRecover(long session, byte[] data, byte[] signature, MutableLong signatureLen);

    void C_VerifyInit(long session, CkMechanism mechanism, long key);

    boolean C_Verify(long session, byte[] data, byte[] signature);

    void C_VerifyUpdate(long session, byte[] part);

    boolean C_VerifyFinal(long session, byte[] signature);

    void C_VerifyRecoverInit(long session, CkMechanism mechanism, long key);

    boolean C_VerifyRecover(long session, byte[] signature, byte[] data, MutableLong dataLen);

    void C_DigestEncryptUpdate(long session, byte[] part, byte[] encryptedPart, MutableLong encryptedPartLen);

    void C_DecryptDigestUpdate(long session, byte[] encryptedPart, byte[] part, MutableLong partLen);

    void C_SignEncryptUpdate(long session, byte[] part, byte[] encryptedPart, MutableLong encryptedPartLen);

    void C_DecryptVerifyUpdate(long session, byte[] encryptedPart, byte[] part, MutableLong partLen);

    void C_GenerateKey(long session, CkMechanism mechanism, List<CkAttribute> template, MutableLong key);

    void C_GenerateKeyPair(long session, CkMechanism mechanism, List<CkAttribute> publicKeyTemplate,
                           List<CkAttribute> privateKeyTemplate, MutableLong publicKey, MutableLong privateKey);

    void C_WrapKey(long session, CkMechanism mechanism, long wrappingKey, long key, byte[] wrappedKey,
                   MutableLong wrappedKeyLen);

    void C_UnwrapKey(long session, CkMechanism mechanism, long unwrappingKey, byte[] wrappedKey,
                     List<CkAttribute> template, MutableLong key);

    void C_DeriveKey(long session, CkMechanism mechanism, long baseKey, List<CkAttribute> template, MutableLong key);

    void C_SeedRandom(long session, byte[] seed);

    byte[] C_GenerateRandom(long session, int length);

    void C_WaitForSlotEvent(long flags, MutableLong slot, Object reserved);
}
