/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.jna;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_C_INITIALIZE_ARGS;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkCInitializeArgs;
import ru.rutoken.pkcs11wrapper.main.IPkcs11MutexHandler;

class CkCInitializeArgsImpl implements CkCInitializeArgs {
    private final CK_C_INITIALIZE_ARGS mData;

    CkCInitializeArgsImpl(CK_C_INITIALIZE_ARGS data) {
        mData = Objects.requireNonNull(data);
    }

    CK_C_INITIALIZE_ARGS getJnaValue() {
        return mData;
    }

    @Override
    public void setMutexHandler(@Nullable IPkcs11MutexHandler mutexHandler) {
        if (mutexHandler != null) {
            throw new RuntimeException("external mutex locking not implemented yet");
            // TODO implement
//            // set mutexHandler callbacks to be called from inside the library
//            // jna callback must not throw any exceptions, as it leads to program crash.
//            mData.CreateMutex = pointerByReference -> {
//                try {
//                    //TODO add mapping from some Memory to Pkcs11Mutex
//                    mutexHandler.createMutex();
//                    return new NativeLong(Pkcs11ReturnValue.CKR_OK.getValue());
//                } catch (Pkcs11Exception e) {
//                    return new NativeLong(e.getCode());
//                } catch (Exception e) {
//                    return new NativeLong(Pkcs11ReturnValue.CKR_GENERAL_ERROR.getValue());
//                }
//            };
//            mData.DestroyMutex = pointer -> {
//                try {
//                    mutexHandler.destroyMutex();
//                    return new NativeLong(Pkcs11ReturnValue.CKR_OK.getValue());
//                } catch (Pkcs11Exception e) {
//                    return new NativeLong(e.getCode());
//                } catch (Exception e) {
//                    return new NativeLong(Pkcs11ReturnValue.CKR_GENERAL_ERROR.getValue());
//                }
//            };
//            mData.LockMutex = pointer -> {
//                try {
//                    mutexHandler.lockMutex();
//                    return new NativeLong(Pkcs11ReturnValue.CKR_OK.getValue());
//                } catch (Pkcs11Exception e) {
//                    return new NativeLong(e.getCode());
//                } catch (Exception e) {
//                    return new NativeLong(Pkcs11ReturnValue.CKR_GENERAL_ERROR.getValue());
//                }
//            };
//            mData.UnlockMutex = pointer -> {
//                try {
//                    mutexHandler.unlockMutex();
//                    return new NativeLong(Pkcs11ReturnValue.CKR_OK.getValue());
//                } catch (Pkcs11Exception e) {
//                    return new NativeLong(e.getCode());
//                } catch (Exception e) {
//                    return new NativeLong(Pkcs11ReturnValue.CKR_GENERAL_ERROR.getValue());
//                }
//            };
        } else {
            mData.CreateMutex = null;
            mData.DestroyMutex = null;
            mData.LockMutex = null;
            mData.UnlockMutex = null;
        }
    }

    @Override
    public void setFlags(long flags) {
        mData.flags.setValue(flags);
    }
}
