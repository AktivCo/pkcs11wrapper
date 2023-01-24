/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.constant;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.constant.standard.EnumFromValueHelper;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue;

public enum RtPkcs11ReturnValue implements IPkcs11ReturnValue {
    CKR_CORRUPTED_MAPFILE(1L),
    CKR_WRONG_VERSION_FIELD(2L),
    CKR_WRONG_PKCS1_ENCODING(3L),
    CKR_RTPKCS11_DATA_CORRUPTED(4L),
    CKR_RTPKCS11_RSF_DATA_CORRUPTED(5L),
    CKR_SM_PASSWORD_INVALID(6L),
    CKR_LICENSE_READ_ONLY(7L),
    CKR_VENDOR_EMITENT_KEY_BLOCKED(8L),
    CKR_CERT_CHAIN_NOT_VERIFIED(9L),
    CKR_INAPPROPRIATE_PIN(10L),
    CKR_PIN_IN_HISTORY(11L);

    private static final EnumFromValueHelper<RtPkcs11ReturnValue> FROM_VALUE_HELPER = new EnumFromValueHelper<>();
    private final long mValue;

    RtPkcs11ReturnValue(long value) {
        mValue = Pkcs11ReturnValue.CKR_VENDOR_DEFINED.getAsLong() + value;
    }

    @Nullable
    public static RtPkcs11ReturnValue nullableFromValue(long value) {
        return FROM_VALUE_HELPER.nullableFromValue(value, RtPkcs11ReturnValue.class);
    }

    public static RtPkcs11ReturnValue fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, RtPkcs11ReturnValue.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }

    public static class Factory implements VendorFactory {
        @Nullable
        @Override
        public RtPkcs11ReturnValue nullableFromValue(long value) {
            return RtPkcs11ReturnValue.nullableFromValue(value);
        }
    }
}
