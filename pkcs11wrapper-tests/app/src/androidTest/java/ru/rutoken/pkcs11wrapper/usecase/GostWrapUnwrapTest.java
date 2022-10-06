package ru.rutoken.pkcs11wrapper.usecase;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeTrue;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_GOST28147_KEY_GEN;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_GOST28147_KEY_WRAP;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.makePkcs11Attribute;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.isMechanismSupported;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import java.util.Arrays;
import java.util.List;

import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ObjectClass;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.pkcs11wrapper.main.UtilsKt;
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.mechanism.parameter.Pkcs11ByteArrayMechanismParams;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11KeyObject;
import ru.rutoken.pkcs11wrapper.rule.highlevel.GenerateSecretKeyRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.LoginRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SessionRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SlotRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.TokenRule;

public class GostWrapUnwrapTest {
    private static final ModuleRule sModule = new ModuleRule();
    private static final IPkcs11AttributeFactory sAttributeFactory = sModule.getValue().getAttributeFactory();

    private static final List<Pkcs11Attribute> SECRET_KEY_TEMPLATE = Arrays.asList(
            makePkcs11Attribute(sAttributeFactory, Pkcs11AttributeType.CKA_LABEL, "SessionKey"),
            makePkcs11Attribute(sAttributeFactory, Pkcs11AttributeType.CKA_CLASS, Pkcs11ObjectClass.CKO_SECRET_KEY),
            makePkcs11Attribute(sAttributeFactory, Pkcs11AttributeType.CKA_KEY_TYPE, Pkcs11KeyType.CKK_GOST28147),
            makePkcs11Attribute(sAttributeFactory, Pkcs11AttributeType.CKA_WRAP, true),
            makePkcs11Attribute(sAttributeFactory, Pkcs11AttributeType.CKA_UNWRAP, true),
            makePkcs11Attribute(sAttributeFactory, Pkcs11AttributeType.CKA_TOKEN, false),
            makePkcs11Attribute(sAttributeFactory, Pkcs11AttributeType.CKA_EXTRACTABLE, true),
            makePkcs11Attribute(sAttributeFactory, Pkcs11AttributeType.CKA_SENSITIVE, false)
    );

    private static final SlotRule sSlot = new SlotRule(sModule);
    private static final TokenRule sToken = new TokenRule(sSlot);
    private static final SessionRule sSession = new SessionRule(sToken);
    private static final LoginRule sLogin = new LoginRule(sSession, Pkcs11UserType.CKU_USER, UtilsKt.DEFAULT_USER_PIN);
    private static final GenerateSecretKeyRule sSessionKey = new GenerateSecretKeyRule(
            sSession, Pkcs11Mechanism.make(CKM_GOST28147_KEY_GEN), SECRET_KEY_TEMPLATE);
    private static final GenerateSecretKeyRule sWrappingKey = new GenerateSecretKeyRule(
            sSession, Pkcs11Mechanism.make(CKM_GOST28147_KEY_GEN), SECRET_KEY_TEMPLATE);

    @ClassRule
    public static final TestRule sRuleChain = RuleChain.outerRule(sModule).around(sSlot).around(sToken).
            around(sSession).around(sLogin).around(sSessionKey).around(sWrappingKey);

    @Test
    public void wrapUnwrapKey() {
        assumeTrue(isMechanismSupported(sToken.getValue(), CKM_GOST28147_KEY_WRAP));

        Pkcs11KeyObject unwrappedKey = null;
        try {
            final var ukm = sSession.getValue().getRandomNumberManager().generateRandom(8);
            final var wrapMechanism = Pkcs11Mechanism.make(
                    CKM_GOST28147_KEY_WRAP, new Pkcs11ByteArrayMechanismParams(ukm));

            final var wrappedKey = sSession.getValue().getKeyManager().wrapKey(
                    wrapMechanism, sWrappingKey.getValue(), sSessionKey.getValue());
            unwrappedKey = sSession.getValue().getKeyManager().unwrapKey(
                    wrapMechanism, sWrappingKey.getValue(), wrappedKey, SECRET_KEY_TEMPLATE);

            assertThat(
                    unwrappedKey.getAttributeValue(Pkcs11ByteArrayAttribute.class, sSession.getValue(),
                            Pkcs11AttributeType.CKA_VALUE).getByteArrayValue(),
                    equalTo(sSessionKey.getValue().getAttributeValue(Pkcs11ByteArrayAttribute.class,
                            sSession.getValue(), Pkcs11AttributeType.CKA_VALUE).getByteArrayValue())
            );
        } finally {
            if (unwrappedKey != null)
                sSession.getValue().getObjectManager().destroyObject(unwrappedKey);
        }
    }
}