/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.usecase;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_LABEL;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.TEST_2012_256_PRIVATE_KEY_LABEL;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.TEST_2012_256_PUBLIC_KEY_LABEL;
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
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11Gost256PrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11Gost256PublicKeyObject;
import ru.rutoken.pkcs11wrapper.rule.highlevel.GenerateKeyPairRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.LoginRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SessionRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SlotRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.TokenRule;

public class Gost2012_256Test {
    private static final ModuleRule sModule = new ModuleRule();
    private static final IPkcs11AttributeFactory sAttributeFactory = sModule.getValue().getAttributeFactory();
    private static final SlotRule sSlot = new SlotRule(sModule);
    private static final TokenRule sToken = new TokenRule(sSlot);
    private static final SessionRule sSession = new SessionRule(sToken);
    private static final LoginRule sLogin = new LoginRule(sSession, Pkcs11UserType.CKU_USER, UtilsKt.DEFAULT_USER_PIN);
    private static final GenerateKeyPairRule<?, ?> sKeyPair =
            UtilsKt.makeGostR3410_2012_256KeyPairRule(sAttributeFactory, sSession);

    @ClassRule
    public static final TestRule sRuleChain = RuleChain.outerRule(sModule).around(sSlot).around(sToken)
            .around(sSession).around(sLogin).around(sKeyPair);

    @Test
    public void findKeyPair() {
        final Pkcs11Object publicKey = sSession.getValue().getObjectManager().findSingleObject(
                Pkcs11Gost256PublicKeyObject.class,
                Collections.singletonList(makePkcs11Attribute(sAttributeFactory, CKA_LABEL,
                        TEST_2012_256_PUBLIC_KEY_LABEL))
        );

        assertThat(publicKey, instanceOf(Pkcs11Gost256PublicKeyObject.class));

        final Pkcs11Object privateKey = sSession.getValue().getObjectManager().findSingleObject(
                Pkcs11Gost256PrivateKeyObject.class,
                Collections.singletonList(makePkcs11Attribute(sAttributeFactory, CKA_LABEL,
                        TEST_2012_256_PRIVATE_KEY_LABEL))
        );

        assertThat(privateKey, instanceOf(Pkcs11Gost256PrivateKeyObject.class));
    }
}
