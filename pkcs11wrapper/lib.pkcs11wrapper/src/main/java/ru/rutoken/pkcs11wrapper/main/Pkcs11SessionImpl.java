package ru.rutoken.pkcs11wrapper.main;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11SessionInfo;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11DecryptionManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11DigestManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11DualFunctionManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11EncryptionManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11KeyManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11ObjectManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11RandomNumberManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11SignManager;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11VerifyManager;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11KeyObject;
import ru.rutoken.pkcs11wrapper.util.MutableLong;
import ru.rutoken.pkcs11wrapper.util.callconvention.ByteArrayCallConvention;

/**
 * Default session implementation.
 * This class may be extended to add vendor defined extended methods.
 */
public class Pkcs11SessionImpl implements Pkcs11Session {
    private final Pkcs11Token mToken;
    private final long mSessionHandle;
    private final Pkcs11ObjectManager mObjectManager;
    private final Pkcs11EncryptionManager mEncryptionManager;
    private final Pkcs11DecryptionManager mDecryptionManager;
    private final Pkcs11DigestManager mDigestManager;
    private final Pkcs11SignManager mSignManager;
    private final Pkcs11VerifyManager mVerifyManager;
    private final Pkcs11DualFunctionManager mDualFunctionManager;
    private final Pkcs11KeyManager mKeyManager;
    private final Pkcs11RandomNumberManager mRandomNumberManager;

    public Pkcs11SessionImpl(Pkcs11Token token, long sessionHandle) {
        mToken = Objects.requireNonNull(token);
        mSessionHandle = sessionHandle;
        final IPkcs11HighLevelFactory factory = getHighLevelFactory();
        mObjectManager = factory.makeObjectManager(this);
        mEncryptionManager = factory.makeEncryptionManager(this);
        mDecryptionManager = factory.makeDecryptionManager(this);
        mDigestManager = factory.makeDigestManager(this);
        mSignManager = factory.makeSignManager(this);
        mVerifyManager = factory.makeVerifyManager(this);
        mDualFunctionManager = factory.makeDualFunctionManager(this);
        mKeyManager = factory.makeKeyManager(this);
        mRandomNumberManager = factory.makeRandomNumberManager(this);
    }

    @Override
    public Pkcs11Token getToken() {
        return mToken;
    }

    @Override
    public void close() {
        closeSession();
    }

    @Override
    public long getSessionHandle() {
        return mSessionHandle;
    }

    @Override
    public Pkcs11ObjectManager getObjectManager() {
        return mObjectManager;
    }

    @Override
    public Pkcs11EncryptionManager getEncryptionManager() {
        return mEncryptionManager;
    }

    @Override
    public Pkcs11DecryptionManager getDecryptionManager() {
        return mDecryptionManager;
    }

    @Override
    public Pkcs11DigestManager getDigestManager() {
        return mDigestManager;
    }

    @Override
    public Pkcs11SignManager getSignManager() {
        return mSignManager;
    }

    @Override
    public Pkcs11VerifyManager getVerifyManager() {
        return mVerifyManager;
    }

    @Override
    public Pkcs11DualFunctionManager getDualFunctionManager() {
        return mDualFunctionManager;
    }

    @Override
    public Pkcs11KeyManager getKeyManager() {
        return mKeyManager;
    }

    @Override
    public Pkcs11RandomNumberManager getRandomNumberManager() {
        return mRandomNumberManager;
    }

    @Override
    public Pkcs11SessionInfo getSessionInfo() {
        return new Pkcs11SessionInfo(getApi().C_GetSessionInfo(mSessionHandle));
    }

    @Override
    public void initPin(@Nullable String pin) {
        final byte[] pinBytes = null != pin ? pin.getBytes() : null;
        getApi().C_InitPIN(mSessionHandle, pinBytes);
    }

    @Override
    public void setPin(@Nullable String oldPin, @Nullable String newPin) {
        final byte[] oldPinBytes = null != oldPin ? oldPin.getBytes() : null;
        final byte[] newPinBytes = null != newPin ? newPin.getBytes() : null;
        getApi().C_SetPIN(mSessionHandle, oldPinBytes, newPinBytes);
    }

    @Override
    public byte[] getOperationState() {
        return new ByteArrayCallConvention("C_GetOperationState") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_GetOperationState(mSessionHandle, null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_GetOperationState(
                        mSessionHandle, values, length));
            }
        }.call().values;
    }

    @Override
    public void setOperationState(byte[] operationState, Pkcs11KeyObject encryptionKey,
                                  Pkcs11Object authenticationKey) {
        getApi().C_SetOperationState(mSessionHandle, operationState,
                encryptionKey.getHandle(), authenticationKey.getHandle());
    }

    @Override
    public LoginGuard login(Pkcs11UserType userType, @Nullable String pin) {
        final byte[] pinBytes = null != pin ? pin.getBytes() : null;
        getApi().C_Login(mSessionHandle, userType.getAsLong(), pinBytes);
        return new LoginGuardImpl(this);
    }

    private void logout() {
        getApi().C_Logout(mSessionHandle);
    }

    private void closeSession() {
        getApi().C_CloseSession(mSessionHandle);
    }

    private static class LoginGuardImpl implements LoginGuard {
        private final Pkcs11SessionImpl mSession;

        private LoginGuardImpl(Pkcs11SessionImpl session) {
            mSession = Objects.requireNonNull(session);
        }

        @Override
        public void close() {
            mSession.logout();
        }
    }
}
