/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant.standard;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11BooleanAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11DateAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11MechanismTypeArrayAttribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11StringAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11CertificateTypeAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11HardwareFeatureTypeAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11KeyTypeAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11LongAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11MechanismTypeAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11ObjectClassAttribute;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;

@SuppressWarnings("SpellCheckingInspection")
public enum Pkcs11AttributeType implements IPkcs11AttributeType {
    CKA_CLASS(0x00000000L, Pkcs11ObjectClassAttribute.class),
    CKA_TOKEN(0x00000001L, Pkcs11BooleanAttribute.class),
    CKA_PRIVATE(0x00000002L, Pkcs11BooleanAttribute.class),
    CKA_LABEL(0x00000003L, Pkcs11StringAttribute.class),
    CKA_APPLICATION(0x00000010L, Pkcs11StringAttribute.class),
    CKA_VALUE(0x00000011L, Pkcs11ByteArrayAttribute.class),
    CKA_OBJECT_ID(0x00000012L, Pkcs11ByteArrayAttribute.class),
    CKA_CERTIFICATE_TYPE(0x00000080L, Pkcs11CertificateTypeAttribute.class),
    CKA_ISSUER(0x00000081L, Pkcs11ByteArrayAttribute.class),
    CKA_SERIAL_NUMBER(0x00000082L, Pkcs11ByteArrayAttribute.class),
    CKA_AC_ISSUER(0x00000083L, Pkcs11ByteArrayAttribute.class),
    CKA_OWNER(0x00000084L, Pkcs11ByteArrayAttribute.class),
    CKA_ATTR_TYPES(0x00000085L, Pkcs11ByteArrayAttribute.class),
    CKA_TRUSTED(0x00000086L, Pkcs11BooleanAttribute.class),
    CKA_CERTIFICATE_CATEGORY(0x00000087L, Pkcs11LongAttribute.class),
    CKA_JAVA_MIDP_SECURITY_DOMAIN(0x00000088L, Pkcs11LongAttribute.class),
    CKA_URL(0x00000089L, Pkcs11StringAttribute.class),
    CKA_HASH_OF_SUBJECT_PUBLIC_KEY(0x0000008AL, Pkcs11ByteArrayAttribute.class),
    CKA_HASH_OF_ISSUER_PUBLIC_KEY(0x0000008BL, Pkcs11ByteArrayAttribute.class),
    CKA_NAME_HASH_ALGORITHM(0x0000008CL, Pkcs11MechanismTypeAttribute.class),
    CKA_CHECK_VALUE(0x00000090L, Pkcs11ByteArrayAttribute.class),

    CKA_KEY_TYPE(0x00000100L, Pkcs11KeyTypeAttribute.class),
    CKA_SUBJECT(0x00000101L, Pkcs11ByteArrayAttribute.class),
    CKA_ID(0x00000102L, Pkcs11ByteArrayAttribute.class),
    CKA_SENSITIVE(0x00000103L, Pkcs11BooleanAttribute.class),
    CKA_ENCRYPT(0x00000104L, Pkcs11BooleanAttribute.class),
    CKA_DECRYPT(0x00000105L, Pkcs11BooleanAttribute.class),
    CKA_WRAP(0x00000106L, Pkcs11BooleanAttribute.class),
    CKA_UNWRAP(0x00000107L, Pkcs11BooleanAttribute.class),
    CKA_SIGN(0x00000108L, Pkcs11BooleanAttribute.class),
    CKA_SIGN_RECOVER(0x00000109L, Pkcs11BooleanAttribute.class),
    CKA_VERIFY(0x0000010AL, Pkcs11BooleanAttribute.class),
    CKA_VERIFY_RECOVER(0x0000010BL, Pkcs11BooleanAttribute.class),
    CKA_DERIVE(0x0000010CL, Pkcs11BooleanAttribute.class),
    CKA_START_DATE(0x00000110L, Pkcs11DateAttribute.class),
    CKA_END_DATE(0x00000111L, Pkcs11DateAttribute.class),
    CKA_MODULUS(0x00000120L, Pkcs11ByteArrayAttribute.class),
    CKA_MODULUS_BITS(0x00000121L, Pkcs11LongAttribute.class),
    CKA_PUBLIC_EXPONENT(0x00000122L, Pkcs11ByteArrayAttribute.class),
    CKA_PRIVATE_EXPONENT(0x00000123L, Pkcs11ByteArrayAttribute.class),
    CKA_PRIME_1(0x00000124L, Pkcs11ByteArrayAttribute.class),
    CKA_PRIME_2(0x00000125L, Pkcs11ByteArrayAttribute.class),
    CKA_EXPONENT_1(0x00000126L, Pkcs11ByteArrayAttribute.class),
    CKA_EXPONENT_2(0x00000127L, Pkcs11ByteArrayAttribute.class),
    CKA_COEFFICIENT(0x00000128L, Pkcs11ByteArrayAttribute.class),
    CKA_PUBLIC_KEY_INFO(0x00000129L, Pkcs11ByteArrayAttribute.class),
    CKA_PRIME(0x00000130L, Pkcs11ByteArrayAttribute.class),
    CKA_SUBPRIME(0x00000131L, Pkcs11ByteArrayAttribute.class),
    CKA_BASE(0x00000132L, Pkcs11ByteArrayAttribute.class),

    CKA_PRIME_BITS(0x00000133L, Pkcs11LongAttribute.class),
    CKA_SUBPRIME_BITS(0x00000134L, Pkcs11LongAttribute.class),

    CKA_VALUE_BITS(0x00000160L, Pkcs11LongAttribute.class),
    CKA_VALUE_LEN(0x00000161L, Pkcs11LongAttribute.class),
    CKA_EXTRACTABLE(0x00000162L, Pkcs11BooleanAttribute.class),
    CKA_LOCAL(0x00000163L, Pkcs11BooleanAttribute.class),
    CKA_NEVER_EXTRACTABLE(0x00000164L, Pkcs11BooleanAttribute.class),
    CKA_ALWAYS_SENSITIVE(0x00000165L, Pkcs11BooleanAttribute.class),
    CKA_KEY_GEN_MECHANISM(0x00000166L, Pkcs11MechanismTypeAttribute.class),

    CKA_MODIFIABLE(0x00000170L, Pkcs11BooleanAttribute.class),
    CKA_COPYABLE(0x00000171L, Pkcs11BooleanAttribute.class),
    CKA_DESTROYABLE(0x00000172L, Pkcs11BooleanAttribute.class),

    CKA_EC_PARAMS(0x00000180L, Pkcs11ByteArrayAttribute.class),

    CKA_EC_POINT(0x00000181L, Pkcs11ByteArrayAttribute.class),

    @Deprecated CKA_SECONDARY_AUTH(0x00000200L, Pkcs11BooleanAttribute.class),
    @Deprecated CKA_AUTH_PIN_FLAGS(0x00000201L, Pkcs11LongAttribute.class),

    CKA_ALWAYS_AUTHENTICATE(0x00000202L, Pkcs11BooleanAttribute.class),

    CKA_WRAP_WITH_TRUSTED(0x00000210L, Pkcs11BooleanAttribute.class),
    CKA_WRAP_TEMPLATE(Pkcs11Flag.CKF_ARRAY_ATTRIBUTE.getAsLong() | 0x00000211L, Pkcs11ArrayAttribute.class),
    CKA_UNWRAP_TEMPLATE(Pkcs11Flag.CKF_ARRAY_ATTRIBUTE.getAsLong() | 0x00000212L, Pkcs11ArrayAttribute.class),
    CKA_DERIVE_TEMPLATE(Pkcs11Flag.CKF_ARRAY_ATTRIBUTE.getAsLong() | 0x00000213L, Pkcs11ArrayAttribute.class),

    CKA_OTP_FORMAT(0x00000220L, Pkcs11LongAttribute.class),
    CKA_OTP_LENGTH(0x00000221L, Pkcs11LongAttribute.class),
    CKA_OTP_TIME_INTERVAL(0x00000222L, Pkcs11LongAttribute.class),
    CKA_OTP_USER_FRIENDLY_MODE(0x00000223L, Pkcs11BooleanAttribute.class),
    CKA_OTP_CHALLENGE_REQUIREMENT(0x00000224L, Pkcs11LongAttribute.class),
    CKA_OTP_TIME_REQUIREMENT(0x00000225L, Pkcs11LongAttribute.class),
    CKA_OTP_COUNTER_REQUIREMENT(0x00000226L, Pkcs11LongAttribute.class),
    CKA_OTP_PIN_REQUIREMENT(0x00000227L, Pkcs11LongAttribute.class),
    CKA_OTP_COUNTER(0x0000022EL, Pkcs11ByteArrayAttribute.class),
    CKA_OTP_TIME(0x0000022FL, Pkcs11StringAttribute.class),
    CKA_OTP_USER_IDENTIFIER(0x0000022AL, Pkcs11StringAttribute.class),
    CKA_OTP_SERVICE_IDENTIFIER(0x0000022BL, Pkcs11StringAttribute.class),
    CKA_OTP_SERVICE_LOGO(0x0000022CL, Pkcs11StringAttribute.class),
    CKA_OTP_SERVICE_LOGO_TYPE(0x0000022DL, Pkcs11StringAttribute.class),

    CKA_GOSTR3410_PARAMS(0x00000250L, Pkcs11ByteArrayAttribute.class),
    CKA_GOSTR3411_PARAMS(0x00000251L, Pkcs11ByteArrayAttribute.class),
    CKA_GOST28147_PARAMS(0x00000252L, Pkcs11ByteArrayAttribute.class),

    CKA_HW_FEATURE_TYPE(0x00000300L, Pkcs11HardwareFeatureTypeAttribute.class),
    CKA_RESET_ON_INIT(0x00000301L, Pkcs11BooleanAttribute.class),
    CKA_HAS_RESET(0x00000302L, Pkcs11BooleanAttribute.class),

    CKA_PIXEL_X(0x00000400L, Pkcs11LongAttribute.class),
    CKA_PIXEL_Y(0x00000401L, Pkcs11LongAttribute.class),
    CKA_RESOLUTION(0x00000402L, Pkcs11LongAttribute.class),
    CKA_CHAR_ROWS(0x00000403L, Pkcs11LongAttribute.class),
    CKA_CHAR_COLUMNS(0x00000404L, Pkcs11LongAttribute.class),
    CKA_COLOR(0x00000405L, Pkcs11BooleanAttribute.class),
    CKA_BITS_PER_PIXEL(0x00000406L, Pkcs11LongAttribute.class),
    CKA_CHAR_SETS(0x00000480L, Pkcs11StringAttribute.class),
    CKA_ENCODING_METHODS(0x00000481L, Pkcs11StringAttribute.class),
    CKA_MIME_TYPES(0x00000482L, Pkcs11StringAttribute.class),
    CKA_MECHANISM_TYPE(0x00000500L, Pkcs11MechanismTypeAttribute.class),
    CKA_REQUIRED_CMS_ATTRIBUTES(0x00000501L, Pkcs11ByteArrayAttribute.class),
    CKA_DEFAULT_CMS_ATTRIBUTES(0x00000502L, Pkcs11ByteArrayAttribute.class),
    CKA_SUPPORTED_CMS_ATTRIBUTES(0x00000503L, Pkcs11ByteArrayAttribute.class),
    CKA_ALLOWED_MECHANISMS(Pkcs11Flag.CKF_ARRAY_ATTRIBUTE.getAsLong() | 0x00000600L,
            Pkcs11MechanismTypeArrayAttribute.class),

    CKA_VENDOR_DEFINED(0x80000000L, Pkcs11ByteArrayAttribute.class);

    public static final Pkcs11AttributeType CKA_SUB_PRIME_BITS = CKA_SUBPRIME_BITS;
    /**
     * @deprecated CKA_EC_PARAMS is preferred.
     */
    @Deprecated
    public static final Pkcs11AttributeType CKA_ECDSA_PARAMS = CKA_EC_PARAMS;

    private static final EnumFromValueHelper<Pkcs11AttributeType> FROM_VALUE_HELPER = new EnumFromValueHelper<>();
    private final long mValue;
    private final Class<? extends Pkcs11Attribute> mAttributeClass;

    Pkcs11AttributeType(long value, Class<? extends Pkcs11Attribute> attributeClass) {
        mValue = value;
        mAttributeClass = Objects.requireNonNull(attributeClass);
    }

    @Nullable
    public static Pkcs11AttributeType nullableFromValue(long value) {
        return FROM_VALUE_HELPER.nullableFromValue(value, Pkcs11AttributeType.class);
    }

    public static Pkcs11AttributeType fromValue(long value) {
        return FROM_VALUE_HELPER.fromValue(value, Pkcs11AttributeType.class);
    }

    @Override
    public long getAsLong() {
        return mValue;
    }

    public Class<? extends Pkcs11Attribute> getAttributeClass() {
        return mAttributeClass;
    }
}
