package ru.rutoken.pkcs11wrapper.rutoken.constant;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_GOSTR3410_PARAMS;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import ru.rutoken.pkcs11jna.RtPkcs11Constants;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11BooleanAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11LongAttribute;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.standard.EnumFromValueHelper;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.rutoken.attribute.RtPkcs11ByteAttribute;
import ru.rutoken.pkcs11wrapper.rutoken.attribute.RtPkcs11LongArrayAttribute;

public enum RtPkcs11AttributeType implements IPkcs11AttributeType {
    CKA_VENDOR_SECURE_MESSAGING_AVAILABLE(
            RtPkcs11Constants.CKA_VENDOR_SECURE_MESSAGING_AVAILABLE, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_CURRENT_SECURE_MESSAGING_MODE(
            RtPkcs11Constants.CKA_VENDOR_CURRENT_SECURE_MESSAGING_MODE, Pkcs11LongAttribute.class
    ),
    CKA_VENDOR_SUPPORTED_SECURE_MESSAGING_MODES(
            RtPkcs11Constants.CKA_VENDOR_SUPPORTED_SECURE_MESSAGING_MODES, RtPkcs11LongArrayAttribute.class
    ),

    CKA_VENDOR_CURRENT_TOKEN_INTERFACE(RtPkcs11Constants.CKA_VENDOR_CURRENT_TOKEN_INTERFACE, Pkcs11LongAttribute.class),
    CKA_VENDOR_SUPPORTED_TOKEN_INTERFACE(
            RtPkcs11Constants.CKA_VENDOR_SUPPORTED_TOKEN_INTERFACE, Pkcs11LongAttribute.class
    ),

    CKA_VENDOR_EXTERNAL_AUTHENTICATION(
            RtPkcs11Constants.CKA_VENDOR_EXTERNAL_AUTHENTICATION, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_BIOMETRIC_AUTHENTICATION(
            RtPkcs11Constants.CKA_VENDOR_BIOMETRIC_AUTHENTICATION, Pkcs11LongAttribute.class
    ),

    CKA_VENDOR_SUPPORT_CUSTOM_PIN(RtPkcs11Constants.CKA_VENDOR_SUPPORT_CUSTOM_PIN, Pkcs11BooleanAttribute.class),
    CKA_VENDOR_CUSTOM_ADMIN_PIN(RtPkcs11Constants.CKA_VENDOR_CUSTOM_ADMIN_PIN, Pkcs11BooleanAttribute.class),
    CKA_VENDOR_CUSTOM_USER_PIN(RtPkcs11Constants.CKA_VENDOR_CUSTOM_USER_PIN, Pkcs11BooleanAttribute.class),

    CKA_VENDOR_SUPPORT_INTERNAL_TRUSTED_CERTS(
            RtPkcs11Constants.CKA_VENDOR_SUPPORT_INTERNAL_TRUSTED_CERTS, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_SUPPORT_FKC2(RtPkcs11Constants.CKA_VENDOR_SUPPORT_FKC2, Pkcs11BooleanAttribute.class),
    CKA_VENDOR_SUPPORT_HW_RESULT_FOR_GOST28147_KEY_UNWRAP(
            RtPkcs11Constants.CKA_VENDOR_SUPPORT_HW_RESULT_FOR_GOST28147_KEY_UNWRAP, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_SUPPORT_HW_KDF_TREE(RtPkcs11Constants.CKA_VENDOR_SUPPORT_HW_KDF_TREE, Pkcs11BooleanAttribute.class),
    CKA_VENDOR_SUPPORT_KIMP15(RtPkcs11Constants.CKA_VENDOR_SUPPORT_KIMP15, Pkcs11BooleanAttribute.class),

    CKA_VENDOR_CHECKSUM(RtPkcs11Constants.CKA_VENDOR_CHECKSUM, Pkcs11ByteArrayAttribute.class),
    CKA_VENDOR_HMAC_VALUE(RtPkcs11Constants.CKA_VENDOR_HMAC_VALUE, Pkcs11ByteArrayAttribute.class),
    CKA_VENDOR_INTERNAL_TRUSTED_CERT(RtPkcs11Constants.CKA_VENDOR_INTERNAL_TRUSTED_CERT, Pkcs11BooleanAttribute.class),
    CKA_VENDOR_IV(RtPkcs11Constants.CKA_VENDOR_IV, Pkcs11ByteArrayAttribute.class),

    CKA_VENDOR_PIN_POLICY_STATE(RtPkcs11Constants.CKA_VENDOR_PIN_POLICY_STATE, Pkcs11LongAttribute.class),
    CKA_VENDOR_PIN_POLICIES_DELETABLE(
            RtPkcs11Constants.CKA_VENDOR_PIN_POLICIES_DELETABLE, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_SUPPORTED_PIN_POLICIES(
            RtPkcs11Constants.CKA_VENDOR_SUPPORTED_PIN_POLICIES, RtPkcs11LongArrayAttribute.class
    ),
    CKA_VENDOR_PIN_POLICY_MIN_LENGTH(
            RtPkcs11Constants.CKA_VENDOR_PIN_POLICY_MIN_LENGTH, RtPkcs11ByteAttribute.class
    ),
    CKA_VENDOR_PIN_POLICY_HISTORY_DEPTH(
            RtPkcs11Constants.CKA_VENDOR_PIN_POLICY_HISTORY_DEPTH, RtPkcs11ByteAttribute.class
    ),
    CKA_VENDOR_PIN_POLICY_ALLOW_DEFAULT_PIN_USAGE(
            RtPkcs11Constants.CKA_VENDOR_PIN_POLICY_ALLOW_DEFAULT_PIN_USAGE, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_PIN_POLICY_DIGIT_REQUIRED(
            RtPkcs11Constants.CKA_VENDOR_PIN_POLICY_DIGIT_REQUIRED, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_PIN_POLICY_UPPERCASE_REQUIRED(
            RtPkcs11Constants.CKA_VENDOR_PIN_POLICY_UPPERCASE_REQUIRED, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_PIN_POLICY_LOWERCASE_REQUIRED(
            RtPkcs11Constants.CKA_VENDOR_PIN_POLICY_LOWERCASE_REQUIRED, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_PIN_POLICY_SPEC_CHAR_REQUIRED(
            RtPkcs11Constants.CKA_VENDOR_PIN_POLICY_SPEC_CHAR_REQUIRED, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_PIN_POLICY_DIFF_CHARS_REQUIRED(
            RtPkcs11Constants.CKA_VENDOR_PIN_POLICY_DIFF_CHARS_REQUIRED, Pkcs11BooleanAttribute.class
    ),
    CKA_VENDOR_USER_TYPE(RtPkcs11Constants.CKA_VENDOR_USER_TYPE, Pkcs11LongAttribute.class);

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
