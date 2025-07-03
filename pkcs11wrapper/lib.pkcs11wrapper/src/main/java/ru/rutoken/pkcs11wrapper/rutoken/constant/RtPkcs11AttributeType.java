/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.constant;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_GOSTR3410_PARAMS;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11BooleanAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11StringAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11LongAttribute;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.standard.EnumFromValueHelper;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11Flag;
import ru.rutoken.pkcs11wrapper.rutoken.attribute.RtPkcs11ByteAttribute;
import ru.rutoken.pkcs11wrapper.rutoken.attribute.RtPkcs11LongArrayAttribute;
import ru.rutoken.pkcs11wrapper.rutoken.constant.internal.RtPkcs11InternalConstants;

public enum RtPkcs11AttributeType implements IPkcs11AttributeType {
    /* Attributes for CKH_VENDOR_TOKEN_INFO hardware feature */
    CKA_VENDOR_SECURE_MESSAGING_AVAILABLE(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3000, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_CURRENT_SECURE_MESSAGING_MODE(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3001, Pkcs11LongAttribute.class
    ),
    CKA_VENDOR_SUPPORTED_SECURE_MESSAGING_MODES(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3002, RtPkcs11LongArrayAttribute.class
    ),

    CKA_VENDOR_CURRENT_TOKEN_INTERFACE(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3003, Pkcs11LongAttribute.class
    ),
    CKA_VENDOR_SUPPORTED_TOKEN_INTERFACE(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3004, Pkcs11LongAttribute.class
    ),

    CKA_VENDOR_EXTERNAL_AUTHENTICATION(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3005, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_BIOMETRIC_AUTHENTICATION(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3006, Pkcs11LongAttribute.class
    ),

    CKA_VENDOR_SUPPORT_CUSTOM_PIN(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3007, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_CUSTOM_ADMIN_PIN(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3008, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_CUSTOM_USER_PIN(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3009, Pkcs11BooleanAttribute.class
    ),

    CKA_VENDOR_SUPPORT_INTERNAL_TRUSTED_CERTS(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x300A, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_SUPPORT_FKC2(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x300B, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_SUPPORT_HW_RESULT_FOR_GOST28147_KEY_UNWRAP(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x300C, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_SUPPORT_HW_KDF_TREE(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x300D, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_SUPPORT_KIMP15(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x300E, Pkcs11BooleanAttribute.class
    ),

    /**
     * An UTF-8 string, that contains symbolic token model name
     */
    CKA_VENDOR_MODEL_NAME(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3010, Pkcs11StringAttribute.class
    ),

    /* KTI attributes */
    /**
     * Array of bytes containing the checksum of the object.
     */
    CKA_VENDOR_CHECKSUM(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3100, Pkcs11ByteArrayAttribute.class
    ),
    /**
     * Byte array containing the HMAC from the transmitted data.
     */
    CKA_VENDOR_HMAC_VALUE(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3101, Pkcs11ByteArrayAttribute.class
    ),
    /**
     * Certificate attribute - a trusted certificate enrolled using an issuer key.
     */
    CKA_VENDOR_INTERNAL_TRUSTED_CERT(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3102, Pkcs11BooleanAttribute.class
    ),
    /**
     * Attribute of the initialization vector used when entering data using the issuer key.
     */
    CKA_VENDOR_IV(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3103, Pkcs11ByteArrayAttribute.class
    ),

    /**
     * PIN state.
     */
    CKA_VENDOR_PIN_POLICY_STATE(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3200, Pkcs11LongAttribute.class
    ),
    /**
     * Pin policies will be removed during format.
     */
    CKA_VENDOR_PIN_POLICIES_DELETABLE(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3201, Pkcs11BooleanAttribute.class
    ),
    /**
     * Get array of CK_ATTRIBUTE_TYPE of supported policies.
     */
    CKA_VENDOR_SUPPORTED_PIN_POLICIES(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | Pkcs11Flag.CKF_ARRAY_ATTRIBUTE.getAsLong() | 0x3202,
            RtPkcs11LongArrayAttribute.class
    ),
    /**
     * Minimal PIN length.
     */
    CKA_VENDOR_PIN_POLICY_MIN_LENGTH(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3203, RtPkcs11ByteAttribute.class
    ),
    /**
     * Number of previous PINs remembered. New PINs cannot be set to those values.
     */
    CKA_VENDOR_PIN_POLICY_HISTORY_DEPTH(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3204, RtPkcs11ByteAttribute.class
    ),
    /**
     * Permits operations with default PIN.
     */
    CKA_VENDOR_PIN_POLICY_ALLOW_DEFAULT_PIN_USAGE(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3205, Pkcs11BooleanAttribute.class
    ),
    /**
     * PIN contains at least one digit.
     */
    CKA_VENDOR_PIN_POLICY_DIGIT_REQUIRED(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3206, Pkcs11BooleanAttribute.class
    ),
    /**
     * PIN contains at least one upper case letter.
     */
    CKA_VENDOR_PIN_POLICY_UPPERCASE_REQUIRED(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3207, Pkcs11BooleanAttribute.class
    ),
    /**
     * PIN contains at least one lower case letter.
     */
    CKA_VENDOR_PIN_POLICY_LOWERCASE_REQUIRED(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3208, Pkcs11BooleanAttribute.class
    ),
    /**
     * PIN contains at least one special character.
     */
    CKA_VENDOR_PIN_POLICY_SPEC_CHAR_REQUIRED(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x3209, Pkcs11BooleanAttribute.class
    ),
    /**
     * PIN doesn't consist of one repeated character.
     */
    CKA_VENDOR_PIN_POLICY_DIFF_CHARS_REQUIRED(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x320a, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_USER_TYPE(
            Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() | 0x320b, Pkcs11LongAttribute.class
    ),

    /**
     * Chaincode value
     */
    CKA_VENDOR_BIP32_CHAINCODE(RtPkcs11InternalConstants.BIP32, Pkcs11ByteArrayAttribute.class),
    /**
     * HASH160 from public key
     */
    CKA_VENDOR_BIP32_ID(RtPkcs11InternalConstants.BIP32 | 0x01, Pkcs11ByteArrayAttribute.class),
    /**
     * First 32 bits of CKA_VENDOR_BIP32_ID
     */
    KA_VENDOR_BIP32_FINGERPRINT(RtPkcs11InternalConstants.BIP32 | 0x02, Pkcs11ByteArrayAttribute.class),
    /**
     * Master key was generated with BIP39 mnemonic code generation
     */
    CKA_VENDOR_BIP39_MNEMONIC_TRACE(RtPkcs11InternalConstants.BIP32 | 0x03, Pkcs11BooleanAttribute.class),
    /**
     * Mnemonic sentence which was used for key generation
     */
    CKA_VENDOR_BIP39_MNEMONIC(RtPkcs11InternalConstants.BIP32 | 0x04, Pkcs11StringAttribute.class),
    /**
     * Can mnemonic sentence be extracted
     */
    CKA_VENDOR_BIP39_MNEMONIC_IS_EXTRACTABLE(RtPkcs11InternalConstants.BIP32 | 0x05, Pkcs11BooleanAttribute.class);

    public static final Pkcs11AttributeType CKA_GOSTR3410_256PARAMS = CKA_GOSTR3410_PARAMS;

    private static final EnumFromValueHelper<RtPkcs11AttributeType> FROM_VALUE_HELPER = new EnumFromValueHelper<>();
    private final long mValue;
    private final Class<? extends Pkcs11Attribute> mAttributeClass;

    RtPkcs11AttributeType(long value, Class<? extends Pkcs11Attribute> attributeClass) {
        mValue = value;
        mAttributeClass = Objects.requireNonNull(attributeClass);
    }

    @Nullable
    public static RtPkcs11AttributeType nullableFromValue(long value) {
        return FROM_VALUE_HELPER.nullableFromValue(value, RtPkcs11AttributeType.class);
    }

    public static RtPkcs11AttributeType fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, RtPkcs11AttributeType.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }

    public Class<? extends Pkcs11Attribute> getAttributeClass() {
        return mAttributeClass;
    }

    public static class Factory implements IPkcs11AttributeType.VendorFactory {
        @Nullable
        @Override
        public RtPkcs11AttributeType nullableFromValue(long value) {
            return RtPkcs11AttributeType.nullableFromValue(value);
        }
    }
}
