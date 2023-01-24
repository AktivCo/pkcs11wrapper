/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import com.sun.jna.Callback;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11jna.*;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.*;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.main.CkNotify;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna.RtPkcs11JnaLowLevelApi;
import ru.rutoken.pkcs11wrapper.util.Mutable;
import ru.rutoken.pkcs11wrapper.util.MutableLong;

import java.util.List;
import java.util.Objects;

import static ru.rutoken.pkcs11jna.Pkcs11Constants.CKR_OK;

/**
 * Implements standard pkcs11 low-level API.
 * A user can extend this class to add vendor C_EX_ methods support.
 * See {@link RtPkcs11JnaLowLevelApi} for example.
 */
public class Pkcs11JnaLowLevelApi implements IPkcs11LowLevelApi {
    protected final Pkcs11 mJnaPkcs11;
    private final IPkcs11LowLevelFactory mFactory;

    public Pkcs11JnaLowLevelApi(Pkcs11 jnaPkcs11, IPkcs11LowLevelFactory lowLevelFactory) {
        mJnaPkcs11 = Objects.requireNonNull(jnaPkcs11);
        mFactory = Objects.requireNonNull(lowLevelFactory);
    }

    /**
     * @return null if attributes list is empty, as JNA does not support empty Structure arrays
     */
    @Nullable
    private static CK_ATTRIBUTE[] convertAttributeList(List<CkAttribute> attributes) {
        if (attributes.isEmpty())
            return null;
        final CK_ATTRIBUTE[] ckAttributes = (CK_ATTRIBUTE[]) new CK_ATTRIBUTE().toArray(attributes.size());
        for (int a = 0; a < attributes.size(); ++a) {
            ((CkAttributeImpl) attributes.get(a)).copyToJnaAttribute(ckAttributes[a]);
        }
        return ckAttributes;
    }

    private static void assignAttributeList(List<CkAttribute> attributes, CK_ATTRIBUTE @Nullable [] nativeAttributes) {
        if (null == nativeAttributes) {
            if (0 == attributes.size())
                return;
            else
                throw new IllegalArgumentException("attributes arrays are not in same state");
        } else if (attributes.size() != nativeAttributes.length) {
            throw new IllegalArgumentException("attributes arrays are not in same state");
        }

        for (int a = 0; a < nativeAttributes.length; a++) {
            ((CkAttributeImpl) attributes.get(a)).assignFromJnaAttribute(nativeAttributes[a]);
        }
    }

    @Nullable
    protected static NativeLong[] convertLongArray(long @Nullable [] longs) {
        if (null == longs)
            return null;
        final NativeLong[] nativeLongs = new NativeLong[longs.length];
        for (int l = 0; l < longs.length; l++) {
            nativeLongs[l] = new NativeLong(longs[l]);
        }
        return nativeLongs;
    }

    private static void assignLongArray(long @Nullable [] longs, NativeLong @Nullable [] nativeLongs) {
        if (null == longs && null == nativeLongs)
            return;
        if ((null != longs && null != nativeLongs) && longs.length == nativeLongs.length) {
            for (int l = 0; l < nativeLongs.length; l++) {
                longs[l] = unsigned(nativeLongs[l]);
            }
        } else {
            throw new IllegalArgumentException("arrays are not in same state");
        }
    }

    @Nullable
    private static CK_C_INITIALIZE_ARGS convertInitializeArgs(@Nullable CkCInitializeArgs args) {
        return args != null ? ((CkCInitializeArgsImpl) args).getJnaValue() : null;
    }

    private static CK_MECHANISM convertMechanism(CkMechanism mechanism) {
        return ((CkMechanismImpl) mechanism).getJnaValue();
    }

    protected static NativeLongByReference makeNativeLongRef(MutableLong value) {
        return new NativeLongByReference(new NativeLong(value.value));
    }

    protected static void assign(MutableLong value, NativeLongByReference nativeValue) {
        value.value = unsigned(nativeValue.getValue());
    }

    protected static NativeLong length(byte @Nullable [] data) {
        return new NativeLong(data != null ? data.length : 0);
    }

    protected static NativeLong length(@Nullable List<String> data) {
        return new NativeLong(data != null ? data.size() : 0);
    }

    protected static NativeLong length(long @Nullable [] data) {
        return new NativeLong(data != null ? data.length : 0);
    }

    /**
     * Helper method to protect from casting & comparison problems with negative 4 byte native longs.
     */
    public static long unsigned(NativeLong value) {
        return new NativeLong(value.longValue(), true).longValue();
    }

    @Override
    public IPkcs11LowLevelFactory getLowLevelFactory() {
        return mFactory;
    }

    @Override
    public long C_Initialize(@Nullable CkCInitializeArgs initArgs) {
        return unsigned(mJnaPkcs11.C_Initialize(convertInitializeArgs(initArgs)));
    }

    @Override
    public long C_Finalize(Object reserved) {
        return unsigned(mJnaPkcs11.C_Finalize((Pointer) reserved));
    }

    @Override
    public long C_GetInfo(Mutable<CkInfo> info) {
        final CK_INFO ckInfo = new CK_INFO();
        final long r = unsigned(mJnaPkcs11.C_GetInfo(ckInfo));
        info.value = new CkInfoImpl(ckInfo);
        return r;
    }

    @Override
    public long C_GetFunctionList(Mutable<CkFunctionList> functionList) {
        final PointerByReference functionListPointerRef = new PointerByReference();
        final long r = unsigned(mJnaPkcs11.C_GetFunctionList(functionListPointerRef));

        if (r == CKR_OK) {
            final Pointer functionListPointer = functionListPointerRef.getValue();
            if (functionListPointer != null) {
                CK_FUNCTION_LIST ckFunctionList = new CK_FUNCTION_LIST(functionListPointer);
                functionList.value = new CkFunctionListImpl(ckFunctionList);
            }
        }

        return r;
    }

    @Override
    public long C_GetSlotList(byte tokenPresent, long[] slotList, MutableLong count) {
        final NativeLongByReference countRef = makeNativeLongRef(count);
        final NativeLong[] nativeSlots = convertLongArray(slotList);
        final long r = unsigned(mJnaPkcs11.C_GetSlotList(tokenPresent, nativeSlots, countRef));
        assignLongArray(slotList, nativeSlots);
        assign(count, countRef);
        return r;
    }

    @Override
    public long C_GetSlotInfo(long slotId, Mutable<CkSlotInfo> info) {
        final CK_SLOT_INFO ckInfo = new CK_SLOT_INFO();
        final long r = unsigned(mJnaPkcs11.C_GetSlotInfo(new NativeLong(slotId), ckInfo));
        info.value = new CkSlotInfoImpl(ckInfo);
        return r;
    }

    @Override
    public long C_GetTokenInfo(long slotId, Mutable<CkTokenInfo> info) {
        final CK_TOKEN_INFO ckInfo = new CK_TOKEN_INFO();
        final long r = unsigned(mJnaPkcs11.C_GetTokenInfo(new NativeLong(slotId), ckInfo));
        info.value = new CkTokenInfoImpl(ckInfo);
        return r;
    }

    @Override
    public long C_GetMechanismList(long slotId, long[] mechanismList, MutableLong count) {
        final NativeLongByReference countRef = makeNativeLongRef(count);
        final NativeLong[] nativeMechanisms = convertLongArray(mechanismList);
        final long r = unsigned(mJnaPkcs11.C_GetMechanismList(new NativeLong(slotId), nativeMechanisms, countRef));
        assignLongArray(mechanismList, nativeMechanisms);
        assign(count, countRef);
        return r;
    }

    @Override
    public long C_GetMechanismInfo(long slotId, long type, Mutable<CkMechanismInfo> info) {
        final CK_MECHANISM_INFO ckInfo = new CK_MECHANISM_INFO();
        final long r = unsigned(mJnaPkcs11.C_GetMechanismInfo(new NativeLong(slotId), new NativeLong(type), ckInfo));
        info.value = new CkMechanismInfoImpl(ckInfo);
        return r;
    }

    @Override
    public long C_InitToken(long slotId, byte @Nullable [] pin, byte[] label) {
        return unsigned(mJnaPkcs11.C_InitToken(new NativeLong(slotId), pin, length(pin), label));
    }

    @Override
    public long C_InitPIN(long slotId, byte @Nullable [] pin) {
        return unsigned(mJnaPkcs11.C_InitPIN(new NativeLong(slotId), pin, length(pin)));
    }

    @Override
    public long C_SetPIN(long slotId, byte @Nullable [] oldPin, byte @Nullable [] newPin) {
        return unsigned(mJnaPkcs11.C_SetPIN(new NativeLong(slotId), oldPin, length(oldPin), newPin, length(newPin)));
    }

    @Override
    public long C_OpenSession(long slotId, long flags, Object application, CkNotify notify, MutableLong session) {
        final NotifyCallback notifyCallback = null != notify ? new NotifyCallback(notify) : null;
        final NativeLongByReference sessionRef = makeNativeLongRef(session);
        final long r = unsigned(mJnaPkcs11.C_OpenSession(
                new NativeLong(slotId), new NativeLong(flags), (Pointer) application, notifyCallback, sessionRef));
        assign(session, sessionRef);
        return r;
    }

    @Override
    public long C_CloseSession(long session) {
        return unsigned(mJnaPkcs11.C_CloseSession(new NativeLong(session)));
    }

    @Override
    public long C_CloseAllSessions(long slotId) {
        return unsigned(mJnaPkcs11.C_CloseAllSessions(new NativeLong(slotId)));
    }

    @Override
    public long C_GetSessionInfo(long session, Mutable<CkSessionInfo> info) {
        final CK_SESSION_INFO ckInfo = new CK_SESSION_INFO();
        final long r = unsigned(mJnaPkcs11.C_GetSessionInfo(new NativeLong(session), ckInfo));
        info.value = new CkSessionInfoImpl(ckInfo);
        return r;
    }

    @Override
    public long C_GetOperationState(long session, byte[] operationState, MutableLong length) {
        final NativeLongByReference lengthRef = makeNativeLongRef(length);
        final long r = unsigned(mJnaPkcs11.C_GetOperationState(new NativeLong(session), operationState, lengthRef));
        assign(length, lengthRef);
        return r;
    }

    @Override
    public long C_SetOperationState(long session, byte[] operationState, long encryptionKey,
                                    long authenticationKey) {
        return unsigned(mJnaPkcs11.C_SetOperationState(new NativeLong(session), operationState,
                length(operationState), new NativeLong(encryptionKey), new NativeLong(authenticationKey)));
    }

    @Override
    public long C_Login(long session, long userType, byte @Nullable [] pin) {
        return unsigned(mJnaPkcs11.C_Login(
                new NativeLong(session), new NativeLong(userType), pin, length(pin)));
    }

    @Override
    public long C_Logout(long session) {
        return unsigned(mJnaPkcs11.C_Logout(new NativeLong(session)));
    }

    @Override
    public long C_CreateObject(long session, List<CkAttribute> template, MutableLong object) {
        final NativeLongByReference objectRef = makeNativeLongRef(object);
        final long r = unsigned(mJnaPkcs11.C_CreateObject(new NativeLong(session),
                convertAttributeList(template), new NativeLong(template.size()), objectRef));
        assign(object, objectRef);
        return r;
    }

    @Override
    public long C_CopyObject(long session, long object, List<CkAttribute> template, MutableLong newObject) {
        final NativeLongByReference newObjectRef = makeNativeLongRef(newObject);
        final long r = unsigned(mJnaPkcs11.C_CopyObject(new NativeLong(session), new NativeLong(object),
                convertAttributeList(template), new NativeLong(template.size()), newObjectRef));
        assign(newObject, newObjectRef);
        return r;
    }

    @Override
    public long C_DestroyObject(long session, long object) {
        return unsigned(mJnaPkcs11.C_DestroyObject(new NativeLong(session), new NativeLong(object)));
    }

    @Override
    public long C_GetObjectSize(long session, long object, MutableLong size) {
        final NativeLongByReference sizeRef = makeNativeLongRef(size);
        final long r = unsigned(mJnaPkcs11.C_GetObjectSize(new NativeLong(session), new NativeLong(object), sizeRef));
        assign(size, sizeRef);
        return r;
    }

    @Override
    public long C_GetAttributeValue(long session, long object, List<CkAttribute> template) {
        final CK_ATTRIBUTE[] nativeTemplate = convertAttributeList(template);
        final long r = unsigned(mJnaPkcs11.C_GetAttributeValue(new NativeLong(session), new NativeLong(object),
                nativeTemplate, new NativeLong(template.size())));
        assignAttributeList(template, nativeTemplate);
        return r;
    }

    @Override
    public long C_SetAttributeValue(long session, long object, List<CkAttribute> template) {
        return unsigned(mJnaPkcs11.C_SetAttributeValue(new NativeLong(session), new NativeLong(object),
                convertAttributeList(template), new NativeLong(template.size())));
    }

    @Override
    public long C_FindObjectsInit(long session, List<CkAttribute> template) {
        return unsigned(mJnaPkcs11.C_FindObjectsInit(
                new NativeLong(session), convertAttributeList(template), new NativeLong(template.size())));
    }

    @Override
    public long C_FindObjects(long session, long[] objects, long maxCount, MutableLong count) {
        final NativeLongByReference countRef = makeNativeLongRef(count);
        final NativeLong[] nativeObjects = convertLongArray(objects);
        final long r = unsigned(mJnaPkcs11.C_FindObjects(
                new NativeLong(session), nativeObjects, new NativeLong(maxCount), countRef));
        assignLongArray(objects, nativeObjects);
        assign(count, countRef);
        return r;
    }

    @Override
    public long C_FindObjectsFinal(long session) {
        return unsigned(mJnaPkcs11.C_FindObjectsFinal(new NativeLong(session)));
    }

    @Override
    public long C_EncryptInit(long session, CkMechanism mechanism, long key) {
        return unsigned(mJnaPkcs11.C_EncryptInit(
                new NativeLong(session), convertMechanism(mechanism), new NativeLong(key)));
    }

    @Override
    public long C_Encrypt(long session, byte[] data, byte[] encryptedData, MutableLong encryptedDataLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(encryptedDataLen);
        final long r = unsigned(mJnaPkcs11.C_Encrypt(
                new NativeLong(session), data, length(data), encryptedData, lengthRef));
        assign(encryptedDataLen, lengthRef);
        return r;
    }

    @Override
    public long C_EncryptUpdate(long session, byte[] part, byte[] encryptedPart,
                                MutableLong encryptedPartLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(encryptedPartLen);
        final long r = unsigned(mJnaPkcs11.C_EncryptUpdate(
                new NativeLong(session), part, length(part), encryptedPart, lengthRef));
        assign(encryptedPartLen, lengthRef);
        return r;
    }

    @Override
    public long C_EncryptFinal(long session, byte[] lastEncryptedPart, MutableLong lastEncryptedPartLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(lastEncryptedPartLen);
        final long r = unsigned(mJnaPkcs11.C_EncryptFinal(new NativeLong(session), lastEncryptedPart, lengthRef));
        assign(lastEncryptedPartLen, lengthRef);
        return r;
    }

    @Override
    public long C_DecryptInit(long session, CkMechanism mechanism, long key) {
        return unsigned(mJnaPkcs11.C_DecryptInit(
                new NativeLong(session), convertMechanism(mechanism), new NativeLong(key)));
    }

    @Override
    public long C_Decrypt(long session, byte[] encryptedData, byte[] data, MutableLong dataLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(dataLen);
        final long r = unsigned(mJnaPkcs11.C_Decrypt(
                new NativeLong(session), encryptedData, length(encryptedData), data, lengthRef));
        assign(dataLen, lengthRef);
        return r;
    }

    @Override
    public long C_DecryptUpdate(long session, byte[] encryptedPart, byte[] part,
                                MutableLong partLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(partLen);
        final long r = unsigned(mJnaPkcs11.C_DecryptUpdate(
                new NativeLong(session), encryptedPart, length(encryptedPart), part, lengthRef));
        assign(partLen, lengthRef);
        return r;
    }

    @Override
    public long C_DecryptFinal(long session, byte[] lastPart, MutableLong lastPartLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(lastPartLen);
        final long r = unsigned(mJnaPkcs11.C_DecryptFinal(new NativeLong(session), lastPart, lengthRef));
        assign(lastPartLen, lengthRef);
        return r;
    }

    @Override
    public long C_DigestInit(long session, CkMechanism mechanism) {
        return unsigned(mJnaPkcs11.C_DigestInit(new NativeLong(session), convertMechanism(mechanism)));
    }

    @Override
    public long C_Digest(long session, byte[] data, byte[] digest, MutableLong digestLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(digestLen);
        final long r = unsigned(mJnaPkcs11.C_Digest(new NativeLong(session), data, length(data), digest, lengthRef));
        assign(digestLen, lengthRef);
        return r;
    }

    @Override
    public long C_DigestUpdate(long session, byte[] part) {
        return unsigned(mJnaPkcs11.C_DigestUpdate(new NativeLong(session), part, length(part)));
    }

    @Override
    public long C_DigestKey(long session, long key) {
        return unsigned(mJnaPkcs11.C_DigestKey(new NativeLong(session), new NativeLong(key)));
    }

    @Override
    public long C_DigestFinal(long session, byte[] digest, MutableLong digestLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(digestLen);
        final long r = unsigned(mJnaPkcs11.C_DigestFinal(new NativeLong(session), digest, lengthRef));
        assign(digestLen, lengthRef);
        return r;
    }

    @Override
    public long C_SignInit(long session, CkMechanism mechanism, long key) {
        return unsigned(mJnaPkcs11.C_SignInit(
                new NativeLong(session), convertMechanism(mechanism), new NativeLong(key)));
    }

    @Override
    public long C_Sign(long session, byte[] data, byte[] signature, MutableLong signatureLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(signatureLen);
        final long r = unsigned(mJnaPkcs11.C_Sign(
                new NativeLong(session), data, length(data), signature, lengthRef));
        assign(signatureLen, lengthRef);
        return r;
    }

    @Override
    public long C_SignUpdate(long session, byte[] part) {
        return unsigned(mJnaPkcs11.C_SignUpdate(new NativeLong(session), part, length(part)));
    }

    @Override
    public long C_SignFinal(long session, byte[] signature, MutableLong signatureLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(signatureLen);
        final long r = unsigned(mJnaPkcs11.C_SignFinal(new NativeLong(session), signature, lengthRef));
        assign(signatureLen, lengthRef);
        return r;
    }

    @Override
    public long C_SignRecoverInit(long session, CkMechanism mechanism, long key) {
        return unsigned(mJnaPkcs11.C_SignRecoverInit(
                new NativeLong(session), convertMechanism(mechanism), new NativeLong(key)));
    }

    @Override
    public long C_SignRecover(long session, byte[] data, byte[] signature, MutableLong signatureLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(signatureLen);
        final long r = unsigned(mJnaPkcs11.C_SignRecover(
                new NativeLong(session), data, length(data), signature, lengthRef));
        assign(signatureLen, lengthRef);
        return r;
    }

    @Override
    public long C_VerifyInit(long session, CkMechanism mechanism, long key) {
        return unsigned(mJnaPkcs11.C_VerifyInit(
                new NativeLong(session), convertMechanism(mechanism), new NativeLong(key)));
    }

    @Override
    public long C_Verify(long session, byte[] data, byte[] signature) {
        return unsigned(mJnaPkcs11.C_Verify(new NativeLong(session), data, length(data), signature, length(signature)));
    }

    @Override
    public long C_VerifyUpdate(long session, byte[] part) {
        return unsigned(mJnaPkcs11.C_VerifyUpdate(new NativeLong(session), part, length(part)));
    }

    @Override
    public long C_VerifyFinal(long session, byte[] signature) {
        return unsigned(mJnaPkcs11.C_VerifyFinal(new NativeLong(session), signature, length(signature)));
    }

    @Override
    public long C_VerifyRecoverInit(long session, CkMechanism mechanism, long key) {
        return unsigned(mJnaPkcs11.C_VerifyRecoverInit(
                new NativeLong(session), convertMechanism(mechanism), new NativeLong(key)));
    }

    @Override
    public long C_VerifyRecover(long session, byte[] signature, byte[] data, MutableLong dataLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(dataLen);
        final long r = unsigned(mJnaPkcs11.C_VerifyRecover(
                new NativeLong(session), signature, length(signature), data, lengthRef));
        assign(dataLen, lengthRef);
        return r;
    }

    @Override
    public long C_DigestEncryptUpdate(long session, byte[] part, byte[] encryptedPart, MutableLong encryptedPartLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(encryptedPartLen);
        final long r = unsigned(mJnaPkcs11.C_DigestEncryptUpdate(new NativeLong(session), part,
                length(part), encryptedPart, lengthRef));
        assign(encryptedPartLen, lengthRef);
        return r;
    }

    @Override
    public long C_DecryptDigestUpdate(long session, byte[] encryptedPart, byte[] part, MutableLong partLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(partLen);
        final long r = unsigned(mJnaPkcs11.C_DecryptDigestUpdate(new NativeLong(session), encryptedPart,
                length(encryptedPart), part, lengthRef));
        assign(partLen, lengthRef);
        return r;
    }

    @Override
    public long C_SignEncryptUpdate(long session, byte[] part, byte[] encryptedPart, MutableLong encryptedPartLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(encryptedPartLen);
        final long r = unsigned(mJnaPkcs11.C_SignEncryptUpdate(
                new NativeLong(session), part, length(part), encryptedPart, lengthRef));
        assign(encryptedPartLen, lengthRef);
        return r;
    }

    @Override
    public long C_DecryptVerifyUpdate(long session, byte[] encryptedPart, byte[] part, MutableLong partLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(partLen);
        final long r = unsigned(mJnaPkcs11.C_DecryptVerifyUpdate(new NativeLong(session), encryptedPart,
                length(encryptedPart), part, lengthRef));
        assign(partLen, lengthRef);
        return r;
    }

    @Override
    public long C_GenerateKey(long session, CkMechanism mechanism, List<CkAttribute> template, MutableLong key) {
        final NativeLongByReference keyRef = makeNativeLongRef(key);
        final long r = unsigned(mJnaPkcs11.C_GenerateKey(new NativeLong(session), convertMechanism(mechanism),
                convertAttributeList(template), new NativeLong(template.size()), keyRef));
        assign(key, keyRef);
        return r;
    }

    @Override
    public long C_GenerateKeyPair(long session, CkMechanism mechanism, List<CkAttribute> publicKeyTemplate,
                                  List<CkAttribute> privateKeyTemplate, MutableLong publicKey, MutableLong privateKey) {
        final NativeLongByReference publicKeyRef = makeNativeLongRef(publicKey);
        final NativeLongByReference privateKeyRef = makeNativeLongRef(privateKey);
        final long r = unsigned(mJnaPkcs11.C_GenerateKeyPair(
                new NativeLong(session), convertMechanism(mechanism), convertAttributeList(publicKeyTemplate),
                new NativeLong(publicKeyTemplate.size()), convertAttributeList(privateKeyTemplate),
                new NativeLong(privateKeyTemplate.size()), publicKeyRef, privateKeyRef));
        assign(publicKey, publicKeyRef);
        assign(privateKey, privateKeyRef);
        return r;
    }

    @Override
    public long C_WrapKey(long session, CkMechanism mechanism, long wrappingKey, long key, byte[] wrappedKey,
                          MutableLong wrappedKeyLen) {
        final NativeLongByReference lengthRef = makeNativeLongRef(wrappedKeyLen);
        final long r = unsigned(mJnaPkcs11.C_WrapKey(new NativeLong(session), convertMechanism(mechanism),
                new NativeLong(wrappingKey), new NativeLong(key), wrappedKey, lengthRef));
        assign(wrappedKeyLen, lengthRef);
        return r;
    }

    @Override
    public long C_UnwrapKey(long session, CkMechanism mechanism, long unwrappingKey, byte[] wrappedKey,
                            List<CkAttribute> template, MutableLong key) {
        final NativeLongByReference keyRef = makeNativeLongRef(key);
        final long r = unsigned(mJnaPkcs11.C_UnwrapKey(
                new NativeLong(session), convertMechanism(mechanism), new NativeLong(unwrappingKey),
                wrappedKey, length(wrappedKey), convertAttributeList(template),
                new NativeLong(template.size()), keyRef));
        assign(key, keyRef);
        return r;
    }

    @Override
    public long C_DeriveKey(long session, CkMechanism mechanism, long baseKey,
                            List<CkAttribute> template, MutableLong key) {
        final NativeLongByReference keyRef = makeNativeLongRef(key);
        final long r = unsigned(mJnaPkcs11.C_DeriveKey(new NativeLong(session), convertMechanism(mechanism),
                new NativeLong(baseKey), convertAttributeList(template), new NativeLong(template.size()), keyRef));
        assign(key, keyRef);
        return r;
    }

    @Override
    public long C_SeedRandom(long session, byte[] seed) {
        return unsigned(mJnaPkcs11.C_SeedRandom(new NativeLong(session), seed, length(seed)));
    }

    @Override
    public long C_GenerateRandom(long session, byte[] randomData) {
        return unsigned(mJnaPkcs11.C_GenerateRandom(new NativeLong(session), randomData, length(randomData)));
    }

    @Override
    @Deprecated
    public long C_GetFunctionStatus(long session) {
        //noinspection deprecation
        return unsigned(mJnaPkcs11.C_GetFunctionStatus(new NativeLong(session)));
    }

    @Override
    @Deprecated
    public long C_CancelFunction(long session) {
        //noinspection deprecation
        return unsigned(mJnaPkcs11.C_CancelFunction(new NativeLong(session)));
    }

    @Override
    public long C_WaitForSlotEvent(long flags, MutableLong slot, Object reserved) {
        final NativeLongByReference slotRef = makeNativeLongRef(slot);
        final long r = unsigned(mJnaPkcs11.C_WaitForSlotEvent(new NativeLong(flags), slotRef, (Pointer) reserved));
        assign(slot, slotRef);
        return r;
    }

    private static final class NotifyCallback implements Callback {
        private final CkNotify mNotify;

        NotifyCallback(CkNotify notify) {
            mNotify = Objects.requireNonNull(notify);
        }

        /**
         * Called by JNA
         */
        public void callback(NativeLong session, NativeLong event, Pointer application) {
            mNotify.onEvent(session.longValue(), event.longValue(), application);
        }
    }
}
