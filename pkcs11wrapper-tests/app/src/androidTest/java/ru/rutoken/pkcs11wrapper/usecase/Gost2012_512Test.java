package ru.rutoken.pkcs11wrapper.usecase;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_LABEL;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.TEST_2012_512_PRIVATE_KEY_LABEL;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.TEST_2012_512_PUBLIC_KEY_LABEL;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.makePkcs11Attribute;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import java.util.Collections;

import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.pkcs11wrapper.main.UtilsKt;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11Gost512PrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11Gost512PublicKeyObject;
import ru.rutoken.pkcs11wrapper.rule.highlevel.GenerateKeyPairRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.LoginRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SessionRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SlotRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.TokenRule;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyType;

public class Gost2012_512Test {
    private static final ModuleRule sModule = new ModuleRule();
    private static final IPkcs11AttributeFactory sAttributeFactory = sModule.getValue().getAttributeFactory();
    private static final SlotRule sSlot = new SlotRule(sModule);
    private static final TokenRule sToken = new TokenRule(sSlot);
    private static final SessionRule sSession = new SessionRule(sToken);
    private static final LoginRule sLogin = new LoginRule(sSession, Pkcs11UserType.CKU_USER, UtilsKt.DEFAULT_USER_PIN);
    private static final GenerateKeyPairRule<?, ?> sKeyPair =
            UtilsKt.makeGostR3410_2012_512KeyPairRule(sAttributeFactory, sSession);

    @ClassRule
    public static final TestRule sRuleChain = RuleChain.outerRule(sModule).around(sSlot).around(sToken)
            .around(sSession).around(sLogin).around(sKeyPair);

    /**
     * There was a bug on armv7a devices in a comparison of a negative C++ (4 byte) long with a Java long.
     */
    @Test
    public void negativeAttributeValue() {
        assertThat(sKeyPair.getValue().getPublicKey().getKeyTypeAttributeValue(sSession.getValue()).getLongValue(),
                is(RtPkcs11KeyType.CKK_GOSTR3410_512.getAsLong()));
    }

    @Test
    public void findKeyPair() {
        final Pkcs11Object publicKey = sSession.getValue().getObjectManager().findSingleObject(
                Pkcs11Gost512PublicKeyObject.class,
                Collections.singletonList(makePkcs11Attribute(sAttributeFactory, CKA_LABEL,
                        TEST_2012_512_PUBLIC_KEY_LABEL))
        );

        assertThat(publicKey, instanceOf(Pkcs11Gost512PublicKeyObject.class));

        final Pkcs11Object privateKey = sSession.getValue().getObjectManager().findSingleObject(
                Pkcs11Gost512PrivateKeyObject.class,
                Collections.singletonList(makePkcs11Attribute(sAttributeFactory, CKA_LABEL,
                        TEST_2012_512_PRIVATE_KEY_LABEL))
        );

        assertThat(privateKey, instanceOf(Pkcs11Gost512PrivateKeyObject.class));
    }
}
