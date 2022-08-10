package ru.rutoken.pkcs11wrapper.main;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue.CKR_OK;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue.CKR_SIGNATURE_INVALID;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue;
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
import ru.rutoken.pkcs11wrapper.util.Mutable;
import ru.rutoken.pkcs11wrapper.util.MutableLong;

/**
 * Default implementation of standard pkcs11 methods.
 * User can extend this class to add vendor C_EX_ methods support.
 */
public class Pkcs11Api implements IPkcs11Api {
    private final IPkcs11LowLevelApi mApi;

    public Pkcs11Api(IPkcs11LowLevelApi api) {
        mApi = Objects.requireNonNull(api);
    }

    @Override
    public IPkcs11LowLevelApi getLowLevelApi() {
        return mApi;
    }

    @Override
    public void C_Initialize(@Nullable CkCInitializeArgs initArgs) {
        call(mApi.C_Initialize(initArgs));
    }

    @Override
    public void C_Finalize(Object reserved) {
        call(mApi.C_Finalize(reserved));
    }

    @Override
    public CkInfo C_GetInfo() {
        final Mutable<CkInfo> info = new Mutable<>();
        call(mApi.C_GetInfo(info));
        return info.value;
    }

    @Override
    public CkFunctionList C_GetFunctionList() {
        final Mutable<CkFunctionList> functionList = new Mutable<>();
        call(mApi.C_GetFunctionList(functionList));
        return functionList.value;
    }

    @Override
    public void C_GetSlotList(byte tokenPresent, long[] slotList, MutableLong count) {
        call(mApi.C_GetSlotList(tokenPresent, slotList, count));
    }

    @Override
    public CkSlotInfo C_GetSlotInfo(long slotId) {
        final Mutable<CkSlotInfo> info = new Mutable<>();
        call(mApi.C_GetSlotInfo(slotId, info));
        return info.value;
    }

    @Override
    public CkTokenInfo C_GetTokenInfo(long slotId) {
        final Mutable<CkTokenInfo> info = new Mutable<>();
        call(mApi.C_GetTokenInfo(slotId, info));
        return info.value;
    }

    @Override
    public void C_GetMechanismList(long slotId, long[] mechanismList, MutableLong count) {
        call(mApi.C_GetMechanismList(slotId, mechanismList, count));
    }

    @Override
    public CkMechanismInfo C_GetMechanismInfo(long slotId, long type) {
        final Mutable<CkMechanismInfo> info = new Mutable<>();
        call(mApi.C_GetMechanismInfo(slotId, type, info));
        return info.value;
    }

    @Override
    public void C_InitToken(long slotId, byte @Nullable [] pin, byte[] label) {
        call(mApi.C_InitToken(slotId, pin, label));
    }

    @Override
    public void C_InitPIN(long slotId, byte @Nullable [] pin) {
        call(mApi.C_InitPIN(slotId, pin));
    }

    @Override
    public void C_SetPIN(long slotId, byte @Nullable [] oldPin, byte @Nullable [] newPin) {
        call(mApi.C_SetPIN(slotId, oldPin, newPin));
    }

    @Override
    public long C_OpenSession(long slotId, long flags, Object application, CkNotify notify) {
        final MutableLong session = new MutableLong();
        call(mApi.C_OpenSession(slotId, flags, application, notify, session));
        return session.value;
    }

    @Override
    public void C_CloseSession(long session) {
        call(mApi.C_CloseSession(session));
    }

    @Override
    public void C_CloseAllSessions(long slotId) {
        call(mApi.C_CloseAllSessions(slotId));
    }

    @Override
    public CkSessionInfo C_GetSessionInfo(long session) {
        final Mutable<CkSessionInfo> info = new Mutable<>();
        call(mApi.C_GetSessionInfo(session, info));
        return info.value;
    }

    @Override
    public void C_GetOperationState(long session, byte[] operationState, MutableLong length) {
        call(mApi.C_GetOperationState(session, operationState, length));
    }

    @Override
    public void C_SetOperationState(long session, byte[] operationState, long encryptionKey, long authenticationKey) {
        call(mApi.C_SetOperationState(session, operationState, encryptionKey, authenticationKey));
    }

    @Override
    public void C_Login(long session, long userType, byte @Nullable [] pin) {
        call(mApi.C_Login(session, userType, pin));
    }

    @Override
    public void C_Logout(long session) {
        call(mApi.C_Logout(session));
    }

    @Override
    public long C_CreateObject(long session, List<CkAttribute> template) {
        final MutableLong object = new MutableLong();
        call(mApi.C_CreateObject(session, template, object));
        return object.value;
    }

    @Override
    public long C_CopyObject(long session, long object, List<CkAttribute> template) {
        final MutableLong newObject = new MutableLong();
        call(mApi.C_CopyObject(session, object, template, newObject));
        return newObject.value;
    }

    @Override
    public void C_DestroyObject(long session, long object) {
        call(mApi.C_DestroyObject(session, object));
    }

    @Override
    public long C_GetObjectSize(long session, long object) {
        final MutableLong size = new MutableLong();
        call(mApi.C_GetObjectSize(session, object, size));
        return size.value;
    }

    @Override
    public void C_GetAttributeValue(long session, long object, List<CkAttribute> template) {
        call(mApi.C_GetAttributeValue(session, object, template));
    }

    @Override
    public void C_SetAttributeValue(long session, long object, List<CkAttribute> template) {
        call(mApi.C_SetAttributeValue(session, object, template));
    }

    @Override
    public void C_FindObjectsInit(long session, List<CkAttribute> template) {
        call(mApi.C_FindObjectsInit(session, template));
    }

    @Override
    public void C_FindObjects(long session, long[] objects, long maxCount, MutableLong count) {
        call(mApi.C_FindObjects(session, objects, maxCount, count));
    }

    @Override
    public void C_FindObjectsFinal(long session) {
        call(mApi.C_FindObjectsFinal(session));
    }

    @Override
    public void C_EncryptInit(long session, CkMechanism mechanism, long key) {
        call(mApi.C_EncryptInit(session, mechanism, key));
    }

    @Override
    public void C_Encrypt(long session, byte[] data, byte[] encryptedData, MutableLong encryptedDataLen) {
        call(mApi.C_Encrypt(session, data, encryptedData, encryptedDataLen));
    }

    @Override
    public void C_EncryptUpdate(long session, byte[] part, byte[] encryptedPart, MutableLong encryptedPartLen) {
        call(mApi.C_EncryptUpdate(session, part, encryptedPart, encryptedPartLen));
    }

    @Override
    public void C_EncryptFinal(long session, byte[] lastEncryptedPart, MutableLong lastEncryptedPartLen) {
        call(mApi.C_EncryptFinal(session, lastEncryptedPart, lastEncryptedPartLen));
    }

    @Override
    public void C_DecryptInit(long session, CkMechanism mechanism, long key) {
        call(mApi.C_DecryptInit(session, mechanism, key));
    }

    @Override
    public void C_Decrypt(long session, byte[] encryptedData, byte[] data, MutableLong dataLen) {
        call(mApi.C_Decrypt(session, encryptedData, data, dataLen));
    }

    @Override
    public void C_DecryptUpdate(long session, byte[] encryptedPart, byte[] part, MutableLong partLen) {
        call(mApi.C_DecryptUpdate(session, encryptedPart, part, partLen));
    }

    @Override
    public void C_DecryptFinal(long session, byte[] lastPart, MutableLong lastPartLen) {
        call(mApi.C_DecryptFinal(session, lastPart, lastPartLen));
    }

    @Override
    public void C_DigestInit(long session, CkMechanism mechanism) {
        call(mApi.C_DigestInit(session, mechanism));
    }

    @Override
    public void C_Digest(long session, byte[] data, byte[] digest, MutableLong digestLen) {
        call(mApi.C_Digest(session, data, digest, digestLen));
    }

    @Override
    public void C_DigestUpdate(long session, byte[] part) {
        call(mApi.C_DigestUpdate(session, part));
    }

    @Override
    public void C_DigestKey(long session, long key) {
        call(mApi.C_DigestKey(session, key));
    }

    @Override
    public void C_DigestFinal(long session, byte[] digest, MutableLong digestLen) {
        call(mApi.C_DigestFinal(session, digest, digestLen));
    }

    @Override
    public void C_SignInit(long session, CkMechanism mechanism, long key) {
        call(mApi.C_SignInit(session, mechanism, key));
    }

    @Override
    public void C_Sign(long session, byte[] data, byte[] signature, MutableLong signatureLen) {
        call(mApi.C_Sign(session, data, signature, signatureLen));
    }

    @Override
    public void C_SignUpdate(long session, byte[] part) {
        call(mApi.C_SignUpdate(session, part));
    }

    @Override
    public void C_SignFinal(long session, byte[] signature, MutableLong signatureLen) {
        call(mApi.C_SignFinal(session, signature, signatureLen));
    }

    @Override
    public void C_SignRecoverInit(long session, CkMechanism mechanism, long key) {
        call(mApi.C_SignRecoverInit(session, mechanism, key));
    }

    @Override
    public void C_SignRecover(long session, byte[] data, byte[] signature, MutableLong signatureLen) {
        call(mApi.C_SignRecover(session, data, signature, signatureLen));
    }

    @Override
    public void C_VerifyInit(long session, CkMechanism mechanism, long key) {
        call(mApi.C_VerifyInit(session, mechanism, key));
    }

    @Override
    public boolean C_Verify(long session, byte[] data, byte[] signature) {
        return callAllowing(mApi.C_Verify(session, data, signature), CKR_SIGNATURE_INVALID) == CKR_OK;
    }

    @Override
    public void C_VerifyUpdate(long session, byte[] part) {
        call(mApi.C_VerifyUpdate(session, part));
    }

    @Override
    public boolean C_VerifyFinal(long session, byte[] signature) {
        return callAllowing(mApi.C_VerifyFinal(session, signature), CKR_SIGNATURE_INVALID) == CKR_OK;
    }

    @Override
    public void C_VerifyRecoverInit(long session, CkMechanism mechanism, long key) {
        call(mApi.C_VerifyRecoverInit(session, mechanism, key));
    }

    @Override
    public boolean C_VerifyRecover(long session, byte[] signature, byte[] data, MutableLong dataLen) {
        return callAllowing(mApi.C_VerifyRecover(session, signature, data, dataLen), CKR_SIGNATURE_INVALID) == CKR_OK;
    }

    @Override
    public void C_DigestEncryptUpdate(long session, byte[] part, byte[] encryptedPart, MutableLong encryptedPartLen) {
        call(mApi.C_DigestEncryptUpdate(session, part, encryptedPart, encryptedPartLen));
    }

    @Override
    public void C_DecryptDigestUpdate(long session, byte[] encryptedPart, byte[] part, MutableLong partLen) {
        call(mApi.C_DecryptDigestUpdate(session, encryptedPart, part, partLen));
    }

    @Override
    public void C_SignEncryptUpdate(long session, byte[] part, byte[] encryptedPart, MutableLong encryptedPartLen) {
        call(mApi.C_SignEncryptUpdate(session, part, encryptedPart, encryptedPartLen));
    }

    @Override
    public void C_DecryptVerifyUpdate(long session, byte[] encryptedPart, byte[] part, MutableLong partLen) {
        call(mApi.C_DecryptVerifyUpdate(session, encryptedPart, part, partLen));
    }

    @Override
    public void C_GenerateKey(long session, CkMechanism mechanism, List<CkAttribute> template, MutableLong key) {
        call(mApi.C_GenerateKey(session, mechanism, template, key));
    }

    @Override
    public void C_GenerateKeyPair(long session, CkMechanism mechanism, List<CkAttribute> publicKeyTemplate,
                                  List<CkAttribute> privateKeyTemplate, MutableLong publicKey, MutableLong privateKey) {
        call(mApi.C_GenerateKeyPair(session, mechanism, publicKeyTemplate, privateKeyTemplate, publicKey, privateKey));
    }

    @Override
    public void C_WrapKey(long session, CkMechanism mechanism, long wrappingKey, long key, byte[] wrappedKey,
                          MutableLong wrappedKeyLen) {
        call(mApi.C_WrapKey(session, mechanism, wrappingKey, key, wrappedKey, wrappedKeyLen));
    }

    @Override
    public void C_UnwrapKey(long session, CkMechanism mechanism, long unwrappingKey, byte[] wrappedKey,
                            List<CkAttribute> template, MutableLong key) {
        call(mApi.C_UnwrapKey(session, mechanism, unwrappingKey, wrappedKey, template, key));
    }

    @Override
    public void C_DeriveKey(long session, CkMechanism mechanism, long baseKey, List<CkAttribute> template,
                            MutableLong key) {
        call(mApi.C_DeriveKey(session, mechanism, baseKey, template, key));
    }

    @Override
    public void C_SeedRandom(long session, byte[] seed) {
        call(mApi.C_SeedRandom(session, seed));
    }

    @Override
    public byte[] C_GenerateRandom(long session, int length) {
        final byte[] data = new byte[length];
        call(mApi.C_GenerateRandom(session, data));
        return data;
    }

    @Override
    public void C_WaitForSlotEvent(long flags, MutableLong slot, Object reserved) {
        call(mApi.C_WaitForSlotEvent(flags, slot, reserved));
    }

    /**
     * @param depth in stack, 0 means current method, 1 means call method, ...
     */
    private String getMethodName(int depth) {
        try {
            final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            return stack[stack.length - 1 - depth].getMethodName();
        } catch (Exception e) {
            return "<UnknownMethod>";
        }
    }

    protected void call(long r) {
        if (r != Pkcs11ReturnValue.CKR_OK.getAsLong()) // make throwIfNotOk args lazy
            Pkcs11Exception.throwIfNotOk(r, getMethodName(2) + " failed", this);
    }

    /**
     * CKR_OK is always allowed
     */
    protected IPkcs11ReturnValue callAllowing(long r, IPkcs11ReturnValue... allowedReturnCodes) {
        for (IPkcs11ReturnValue allowed : allowedReturnCodes)
            if (allowed.getAsLong() == r)
                return returnValue(r);
        if (r != Pkcs11ReturnValue.CKR_OK.getAsLong()) // make throwIfNotOk args lazy
            Pkcs11Exception.throwIfNotOk(r, getMethodName(2) + " failed", this);
        return returnValue(r);
    }

    protected IPkcs11ReturnValue returnValue(long r) {
        return IPkcs11ReturnValue.getInstance(r, getVendorExtensions());
    }
}
