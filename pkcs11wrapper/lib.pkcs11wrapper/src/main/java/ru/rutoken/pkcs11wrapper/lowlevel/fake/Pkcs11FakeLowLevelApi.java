/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import ru.rutoken.pkcs11jna.Pkcs11Constants;
import ru.rutoken.pkcs11jna.RtPkcs11Constants;
import ru.rutoken.pkcs11wrapper.attribute.Attributes;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11Flag;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11SessionState;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkCInitializeArgs;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkDate;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkFunctionList;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkInfo;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanism;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanismInfo;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkSessionInfo;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkSlotInfo;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkTokenInfo;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.main.CkNotify;
import ru.rutoken.pkcs11wrapper.object.certificate.Pkcs11X509PublicKeyCertificateObject;
import ru.rutoken.pkcs11wrapper.object.factory.IPkcs11ObjectFactory;
import ru.rutoken.pkcs11wrapper.util.Mutable;
import ru.rutoken.pkcs11wrapper.util.MutableLong;
import ru.rutoken.pkcs11wrapper.util.Pkcs11Utility;

/**
 * Fake pkcs11 implementation. May be used to create virtual/software tokens or for testing.
 */
public class Pkcs11FakeLowLevelApi implements IPkcs11LowLevelApi {
    private IPkcs11ObjectFactory mObjectFactory;

    protected final static List<Long> TOKEN_TYPES = Arrays.asList(
            0L, // unknown token
            RtPkcs11Constants.TOKEN_TYPE_RUTOKEN_ECP,
            RtPkcs11Constants.TOKEN_TYPE_RUTOKEN_ECPDUAL_BT,
            RtPkcs11Constants.TOKEN_TYPE_RUTOKEN_SCDUAL);
    protected final static int CERTIFICATES_ITERATIONS = 2;
    protected final IPkcs11LowLevelFactory mLowLevelFactory;
    protected final Map<Long, FakeSlot> mSlots = new TreeMap<>();

    public Pkcs11FakeLowLevelApi() {
        this(new Pkcs11FakeLowLevelFactory.Builder().build());
    }

    protected Pkcs11FakeLowLevelApi(IPkcs11LowLevelFactory pkcs11FakeLowLevelFactory) {
        mLowLevelFactory = Objects.requireNonNull(pkcs11FakeLowLevelFactory);
    }

    /**
     * Creates fake objects and mechanisms. Must be called once.
     *
     * @param objectFactory factory for objects creation
     */
    public void createFakeTokens(IPkcs11ObjectFactory objectFactory) {
        if (mObjectFactory != null)
            throw new IllegalStateException("createFakeTokens(IPkcs11ObjectFactory) was already called");

        mObjectFactory = Objects.requireNonNull(objectFactory);

        for (int slotId = 0; slotId < TOKEN_TYPES.size(); slotId++) {
            // create objects
            final Map<Long, FakeObject> objects = new HashMap<>();
            long certIndex = 0;
            for (int c = 0; c < CERTIFICATES_ITERATIONS; c++) {
                try {
                    objects.put(certIndex++, makeCertificateObject(
                            new byte[]{0x01, 0x02, 0x03, (byte) certIndex},
                            FakeConstants.PEM_CERTIFICATE_GOSTR_2012_256));
                } catch (CertificateException e) {
                    e.printStackTrace();
                }
                try {
                    objects.put(certIndex++, makeCertificateObject(
                            new byte[]{0x01, 0x02, 0x03, (byte) certIndex},
                            FakeConstants.PEM_CERTIFICATE_RSA));
                } catch (CertificateException e) {
                    e.printStackTrace();
                }
            }

            // create mechanisms
            final Map<IPkcs11MechanismType, CkMechanismInfo> mechanisms = new HashMap<>();
            mechanisms.put(Pkcs11MechanismType.CKM_RSA_PKCS, new FakeCkMechanismInfoImpl());

            mSlots.put((long) slotId, new FakeSlot(new FakeToken(
                    new byte[]{'1', '2', '3', '4', (byte) ('1' + slotId)},
                    TOKEN_TYPES.get(slotId),
                    objects,
                    mechanisms)));
        }
        mSlots.put((long) (TOKEN_TYPES.size() + 1), new FakeSlot(null)); // empty slot
    }

    private static <T> Long keyAt(Map<Long, T> map, int index) {
        return map.keySet().toArray(new Long[0])[index];
    }

    private FakeObject makeCertificateObject(byte[] idValue, String pemCertificate) throws CertificateException {
        if (mObjectFactory == null)
            throw new IllegalStateException("createFakeTokens(IPkcs11ObjectFactory) was not called");

        final List<CkAttribute> objectAttributes = Attributes.makeCkAttributesList(
                mObjectFactory.makeTemplate(Pkcs11X509PublicKeyCertificateObject.class),
                mLowLevelFactory);

        final CkAttribute id = new FakeCkAttributeImpl();
        id.setType(Pkcs11AttributeType.CKA_ID.getAsLong());
        id.setByteArrayValue(idValue);
        objectAttributes.add(id);

        final CkAttribute value = new FakeCkAttributeImpl();
        value.setType(Pkcs11AttributeType.CKA_VALUE.getAsLong());

        final byte[] encoded = CertificateFactory.getInstance("X.509")
                .generateCertificate(new ByteArrayInputStream(pemCertificate.getBytes())).getEncoded();
        value.setByteArrayValue(encoded);
        objectAttributes.add(value);

        final CkAttribute startDate = new FakeCkAttributeImpl();
        startDate.setType(Pkcs11AttributeType.CKA_START_DATE.getAsLong());
        startDate.setDateValue(makeDate(2019, 5, 17));
        objectAttributes.add(startDate);

        final CkAttribute endDate = new FakeCkAttributeImpl();
        endDate.setType(Pkcs11AttributeType.CKA_END_DATE.getAsLong());
        endDate.setDateValue(makeDate(2051, 2, 18));
        objectAttributes.add(endDate);
        return new FakeObject(objectAttributes);
    }

    private CkDate makeDate(int year, int month, int day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        final CkDate date = new FakeCkDateImpl();
        Pkcs11Utility.assignCkDate(date, calendar.getTime());
        return date;
    }

    protected boolean isRwSession(long flags) {
        return checkFlag(flags, Pkcs11Flag.CKF_RW_SESSION.getAsLong());
    }

    protected boolean checkFlag(long flags, long flag) {
        return (flags & flag) != 0L;
    }

    protected long ok() {
        return Pkcs11ReturnValue.CKR_OK.getAsLong();
    }

    protected long fail() {
        return Pkcs11ReturnValue.CKR_FUNCTION_FAILED.getAsLong();
    }

    @Override
    synchronized public long C_Initialize(@Nullable CkCInitializeArgs initArgs) {
        return ok();
    }

    @Override
    synchronized public long C_Finalize(Object reserved) {
        return ok();
    }

    @Override
    synchronized public long C_GetInfo(Mutable<CkInfo> info) {
        info.value = new FakeCkInfoImpl();
        return ok();
    }

    @Override
    synchronized public long C_GetFunctionList(Mutable<CkFunctionList> functionList) {
        return ok();
    }

    @Override
    synchronized public long C_GetSlotList(byte tokenPresent, long[] slotList, MutableLong count) {
        if (null == slotList) {
            count.value = mSlots.size();
            return ok();
        }

        boolean withToken = tokenPresent == Pkcs11Constants.CK_TRUE; // TODO add tokenPresent support
        for (int m = 0; m < mSlots.size(); m++) {
            slotList[m] = keyAt(mSlots, m);
        }
        return ok();
    }

    @Override
    synchronized public long C_GetSlotInfo(long slotId, Mutable<CkSlotInfo> info) {
        final FakeSlot slot = mSlots.get(slotId);
        if (null == slot) return fail();

        info.value = slot.getSlotInfo();
        return ok();
    }

    @Override
    synchronized public long C_GetTokenInfo(long slotId, Mutable<CkTokenInfo> info) {
        final FakeSlot slot = mSlots.get(slotId);
        if (slot == null) return fail();

        final FakeToken token = slot.token;
        if (token == null) return fail();

        info.value = token.getTokenInfo();
        return ok();
    }

    @Override
    synchronized public long C_GetMechanismList(long slotId, long[] mechanismList, MutableLong count) {
        final FakeSlot slot = mSlots.get(slotId);
        if (slot == null) return fail();

        final FakeToken token = slot.token;
        if (token == null) return fail();

        if (null == mechanismList) {
            count.value = token.mechanisms.size();
            return ok();
        }
        final List<IPkcs11MechanismType> mechanismTypes = new ArrayList<>(token.mechanisms.keySet());
        for (int m = 0; m < mechanismTypes.size(); m++) {
            mechanismList[m] = mechanismTypes.get(m).getAsLong();
        }
        return ok();
    }

    @Override
    synchronized public long C_GetMechanismInfo(long slotId, long type, Mutable<CkMechanismInfo> info) {
        final FakeSlot slot = mSlots.get(slotId);
        if (slot == null) return fail();

        final FakeToken token = slot.token;
        if (token == null) return fail();

        final CkMechanismInfo mechanismInfo = token.mechanisms.get(IPkcs11MechanismType.getInstance(type));
        if (null == mechanismInfo) return fail();

        info.value = mechanismInfo;
        return ok();
    }

    @Override
    synchronized public long C_InitToken(long slotId, byte[] pin, byte[] label) {
        return ok();
    }

    @Override
    synchronized public long C_InitPIN(long slotId, byte[] pin) {
        return ok();
    }

    @Override
    synchronized public long C_SetPIN(long slotId, byte[] oldPin, byte[] newPin) {
        return ok();
    }

    @Override
    synchronized public long C_OpenSession(long slotId, long flags, Object application, CkNotify notify,
                                           MutableLong session) {
        final FakeSlot slot = mSlots.get(slotId);
        if (slot == null) return fail();

        final FakeToken token = slot.token;
        if (token == null) return fail();

        final Pkcs11SessionState state = isRwSession(flags) ?
                Pkcs11SessionState.CKS_RW_PUBLIC_SESSION : Pkcs11SessionState.CKS_RO_PUBLIC_SESSION;

        final FakeSession fakeSession =
                token.openSession(new FakeCkSessionInfoImpl(slotId, state.getAsLong(), flags, 0));
        session.value = fakeSession.handle;
        return ok();
    }

    @Override
    synchronized public long C_CloseSession(long session) {
        if (FakeSession.sessions.get(session) == null)
            return fail();
        FakeSession.sessions.remove(session);
        return ok();
    }

    @Override
    synchronized public long C_CloseAllSessions(long slotId) {
        final FakeSlot slot = mSlots.get(slotId);
        if (slot == null) return fail();

        final FakeToken token = slot.token;
        if (token == null) return fail();

        token.closeAllSessions();
        return ok();
    }

    @Override
    synchronized public long C_GetSessionInfo(long session, Mutable<CkSessionInfo> info) {
        final FakeSession fakeSession = FakeSession.sessions.get(session);
        if (fakeSession == null)
            return Pkcs11ReturnValue.CKR_SESSION_HANDLE_INVALID.getAsLong();
        info.value = fakeSession.getSessionInfo();
        return ok();
    }

    @Override
    synchronized public long C_GetOperationState(long session, byte[] operationState, MutableLong length) {
        return ok();
    }

    @Override
    synchronized public long C_SetOperationState(long session, byte[] operationState, long encryptionKey,
                                                 long authenticationKey) {
        return ok();
    }

    @Override
    synchronized public long C_Login(long session, long userType, byte[] pin) {
        final FakeSession fakeSession = FakeSession.sessions.get(session);
        if (fakeSession == null)
            return Pkcs11ReturnValue.CKR_SESSION_HANDLE_INVALID.getAsLong();

        final FakeCkSessionInfoImpl sessionInfo = fakeSession.getSessionInfo();
        switch (Pkcs11UserType.fromValue(userType)) {
            case CKU_USER: {
                final Pkcs11SessionState state = isRwSession(sessionInfo.getFlags()) ?
                        Pkcs11SessionState.CKS_RW_USER_FUNCTIONS : Pkcs11SessionState.CKS_RO_USER_FUNCTIONS;
                fakeSession.setSessionInfo(sessionInfo.copyWithState(state.getAsLong()));
                break;
            }
            case CKU_SO: {
                fakeSession.setSessionInfo(
                        sessionInfo.copyWithState(Pkcs11SessionState.CKS_RW_SO_FUNCTIONS.getAsLong()));
                break;
            }
            default:
                return fail();
        }
        return ok();
    }

    @Override
    synchronized public long C_Logout(long session) {
        final FakeSession fakeSession = FakeSession.sessions.get(session);
        if (fakeSession == null)
            return Pkcs11ReturnValue.CKR_SESSION_HANDLE_INVALID.getAsLong();

        final FakeCkSessionInfoImpl sessionInfo = fakeSession.getSessionInfo();
        switch (Pkcs11SessionState.fromValue(fakeSession.getSessionInfo().getState())) {
            case CKS_RW_USER_FUNCTIONS:
            case CKS_RW_SO_FUNCTIONS:
                fakeSession.setSessionInfo(sessionInfo.copyWithState(
                        Pkcs11SessionState.CKS_RW_PUBLIC_SESSION.getAsLong()));
                break;
            case CKS_RO_USER_FUNCTIONS:
                fakeSession.setSessionInfo(sessionInfo.copyWithState(
                        Pkcs11SessionState.CKS_RO_PUBLIC_SESSION.getAsLong()));
                break;
            default:
                return fail();
        }
        return ok();
    }

    @Override
    synchronized public long C_CreateObject(long session, List<CkAttribute> template, MutableLong object) {
        return ok();
    }

    @Override
    synchronized public long C_CopyObject(long session, long object, List<CkAttribute> template,
                                          MutableLong newObject) {
        return ok();
    }

    @Override
    synchronized public long C_DestroyObject(long session, long object) {
        return ok();
    }

    @Override
    synchronized public long C_GetObjectSize(long session, long object, MutableLong size) {
        return ok();
    }

    @Override
    synchronized public long C_GetAttributeValue(long session, long object, List<CkAttribute> template) {
        final FakeSession fakeSession = FakeSession.sessions.get(session);
        if (fakeSession == null)
            return Pkcs11ReturnValue.CKR_SESSION_HANDLE_INVALID.getAsLong();

        final FakeObject fakeObject = fakeSession.token.objects.get(object);
        if (null != fakeObject) {
            for (CkAttribute attr : template) {
                final FakeCkAttributeImpl requestedAttr = (FakeCkAttributeImpl) attr;
                // find requested attribute in object attributes
                final CkAttribute objectAttr = fakeObject.getAttribute(requestedAttr.getPkcs11Type());
                if (null != objectAttr) {
                    if (requestedAttr.getValueLen() == objectAttr.getValueLen())
                        requestedAttr.setValue(objectAttr.getValue());
                    else
                        requestedAttr.setValueLen(objectAttr.getValueLen());
                } else {
                    requestedAttr.setValueLen(Pkcs11Constants.CK_UNAVAILABLE_INFORMATION);
                }
            }
        }
        return ok();
    }

    @Override
    synchronized public long C_SetAttributeValue(long session, long object, List<CkAttribute> template) {
        return ok();
    }

    @Override
    synchronized public long C_FindObjectsInit(long session, List<CkAttribute> template) {
        final FakeSession fakeSession = FakeSession.sessions.get(session);
        if (fakeSession == null)
            return Pkcs11ReturnValue.CKR_SESSION_HANDLE_INVALID.getAsLong();

        if (fakeSession.token.search != null)
            return fail();
        fakeSession.token.search = new FakeToken.Search(template);
        return ok();
    }

    @Override
    synchronized public long C_FindObjects(long session, long[] objects, long maxCount, MutableLong count) {
        final FakeSession fakeSession = FakeSession.sessions.get(session);
        if (fakeSession == null)
            return Pkcs11ReturnValue.CKR_SESSION_HANDLE_INVALID.getAsLong();

        final FakeToken token = fakeSession.token;
        if (token.search == null)
            return fail();
        else if (!token.search.needFindObjects) {
            count.value = 0;
            return ok();
        }

        count.value = token.objects.size();
        for (int m = 0; m < Math.min(token.objects.size(), maxCount); m++) {
            objects[m] = keyAt(token.objects, m);
        }

        token.search.needFindObjects = false;
        return ok();
    }

    @Override
    synchronized public long C_FindObjectsFinal(long session) {
        final FakeSession fakeSession = FakeSession.sessions.get(session);
        if (fakeSession == null)
            return Pkcs11ReturnValue.CKR_SESSION_HANDLE_INVALID.getAsLong();

        if (fakeSession.token.search == null)
            return fail();
        fakeSession.token.search = null;
        return ok();
    }

    @Override
    synchronized public long C_EncryptInit(long session, CkMechanism mechanism, long key) {
        return ok();
    }

    @Override
    synchronized public long C_Encrypt(long session, byte[] data, byte[] encryptedData, MutableLong encryptedDataLen) {
        return ok();
    }

    @Override
    synchronized public long C_EncryptUpdate(long session, byte[] part, byte[] encryptedPart,
                                             MutableLong encryptedPartLen) {
        return ok();
    }

    @Override
    synchronized public long C_EncryptFinal(long session, byte[] lastEncryptedPart, MutableLong lastEncryptedPartLen) {
        return ok();
    }

    @Override
    synchronized public long C_DecryptInit(long session, CkMechanism mechanism, long key) {
        return ok();
    }

    @Override
    synchronized public long C_Decrypt(long session, byte[] encryptedData, byte[] data, MutableLong dataLen) {
        return ok();
    }

    @Override
    synchronized public long C_DecryptUpdate(long session, byte[] encryptedPart, byte[] part, MutableLong partLen) {
        return ok();
    }

    @Override
    synchronized public long C_DecryptFinal(long session, byte[] lastPart, MutableLong lastPartLen) {
        return ok();
    }

    @Override
    synchronized public long C_DigestInit(long session, CkMechanism mechanism) {
        return ok();
    }

    @Override
    synchronized public long C_Digest(long session, byte[] data, byte[] digest, MutableLong digestLen) {
        if (digest == null) {
            digestLen.value = 32;
            return ok();
        }
        Arrays.fill(digest, (byte) 1);
        return ok();
    }

    @Override
    synchronized public long C_DigestUpdate(long session, byte[] part) {
        return ok();
    }

    @Override
    synchronized public long C_DigestKey(long session, long key) {
        return ok();
    }

    @Override
    synchronized public long C_DigestFinal(long session, byte[] digest, MutableLong digestLen) {
        return ok();
    }

    @Override
    synchronized public long C_SignInit(long session, CkMechanism mechanism, long key) {
        return ok();
    }

    @Override
    synchronized public long C_Sign(long session, byte[] data, byte[] signature, MutableLong signatureLen) {
        if (signature == null) {
            signatureLen.value = 64;
            return ok();
        }
        Arrays.fill(signature, (byte) 1);
        return ok();
    }

    @Override
    synchronized public long C_SignUpdate(long session, byte[] part) {
        return ok();
    }

    @Override
    synchronized public long C_SignFinal(long session, byte[] signature, MutableLong signatureLen) {
        return ok();
    }

    @Override
    synchronized public long C_SignRecoverInit(long session, CkMechanism mechanism, long key) {
        return ok();
    }

    @Override
    synchronized public long C_SignRecover(long session, byte[] data, byte[] signature, MutableLong signatureLen) {
        return ok();
    }

    @Override
    synchronized public long C_VerifyInit(long session, CkMechanism mechanism, long key) {
        return ok();
    }

    @Override
    synchronized public long C_Verify(long session, byte[] data, byte[] signature) {
        return ok();
    }

    @Override
    synchronized public long C_VerifyUpdate(long session, byte[] part) {
        return ok();
    }

    @Override
    synchronized public long C_VerifyFinal(long session, byte[] signature) {
        return ok();
    }

    @Override
    synchronized public long C_VerifyRecoverInit(long session, CkMechanism mechanism, long key) {
        return ok();
    }

    @Override
    synchronized public long C_VerifyRecover(long session, byte[] signature, byte[] data, MutableLong dataLen) {
        return ok();
    }

    @Override
    synchronized public long C_DigestEncryptUpdate(long session, byte[] part, byte[] encryptedPart,
                                                   MutableLong encryptedPartLen) {
        return ok();
    }

    @Override
    synchronized public long C_DecryptDigestUpdate(long session, byte[] encryptedPart, byte[] part,
                                                   MutableLong partLen) {
        return ok();
    }

    @Override
    synchronized public long C_SignEncryptUpdate(long session, byte[] part, byte[] encryptedPart,
                                                 MutableLong encryptedPartLen) {
        return ok();
    }

    @Override
    synchronized public long C_DecryptVerifyUpdate(long session, byte[] encryptedPart, byte[] part,
                                                   MutableLong partLen) {
        return ok();
    }

    @Override
    synchronized public long C_GenerateKey(long session, CkMechanism mechanism, List<CkAttribute> template,
                                           MutableLong key) {
        return ok();
    }

    @Override
    synchronized public long C_GenerateKeyPair(long session, CkMechanism mechanism, List<CkAttribute> publicKeyTemplate,
                                               List<CkAttribute> privateKeyTemplate, MutableLong publicKey,
                                               MutableLong privateKey) {
        return ok();
    }

    @Override
    synchronized public long C_WrapKey(long session, CkMechanism mechanism, long wrappingKey, long key,
                                       byte[] wrappedKey,
                                       MutableLong wrappedKeyLen) {
        return ok();
    }

    @Override
    synchronized public long C_UnwrapKey(long session, CkMechanism mechanism, long unwrappingKey, byte[] wrappedKey,
                                         List<CkAttribute> template, MutableLong key) {
        return ok();
    }

    @Override
    synchronized public long C_DeriveKey(long session, CkMechanism mechanism, long baseKey,
                                         List<CkAttribute> template, MutableLong key) {
        return ok();
    }

    @Override
    synchronized public long C_SeedRandom(long session, byte[] seed) {
        return ok();
    }

    @Override
    synchronized public long C_GenerateRandom(long session, byte[] randomData) {
        return ok();
    }

    @Deprecated
    @Override
    synchronized public long C_GetFunctionStatus(long session) {
        return ok();
    }

    @Deprecated
    @Override
    synchronized public long C_CancelFunction(long session) {
        return ok();
    }

    /**
     * Should not be synchronized
     */
    @Override
    public long C_WaitForSlotEvent(long flags, MutableLong slot, Object reserved) {
        if (checkFlag(flags, Pkcs11Flag.CKF_DONT_BLOCK.getAsLong())) {
            return Pkcs11ReturnValue.CKR_NO_EVENT.getAsLong();
        } else {
            try {
                Thread.sleep(1000 * 60);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return fail();
        }
    }

    @Override
    public IPkcs11LowLevelFactory getLowLevelFactory() {
        return mLowLevelFactory;
    }
}
