package ru.rutoken.pkcs11wrapper.manager;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeTrue;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_GOSTR3410;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.DATA;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.DEFAULT_USER_PIN;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.makeGostR3410_2001KeyPairRule;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.isMechanismSupported;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.rule.highlevel.GenerateKeyPairRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.LoginRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SessionRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SlotRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.TokenRule;

public class EncryptionDecryptionTest {
    private static final ModuleRule sModule = new ModuleRule();
    private static final SlotRule sSlot = new SlotRule(sModule);
    private static final TokenRule sToken = new TokenRule(sSlot);
    private static final SessionRule sSession = new SessionRule(sToken);
    private static final LoginRule sLogin = new LoginRule(sSession, Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN);
    private static final GenerateKeyPairRule<?, ?> sKeyPair =
            makeGostR3410_2001KeyPairRule(sModule.getValue().getAttributeFactory(), sSession);

    @ClassRule
    public static final TestRule sRuleChain = RuleChain.outerRule(sModule).around(sSlot).around(sToken)
            .around(sSession).around(sLogin).around(sKeyPair);

    @Ignore("not implemented") // TODO implement
    @Test
    public void encryptDecrypt() {
        assumeTrue(isMechanismSupported(sToken.getValue(), CKM_GOSTR3410));

        final var encrypted = sSession.getValue().getEncryptionManager().encryptAtOnce(DATA,
                Pkcs11Mechanism.make(CKM_GOSTR3410), sKeyPair.getValue().getPrivateKey());

        final var decrypted = sSession.getValue().getDecryptionManager().decryptAtOnce(encrypted,
                Pkcs11Mechanism.make(CKM_GOSTR3410), sKeyPair.getValue().getPrivateKey());

        assertThat(DATA, equalTo(decrypted));
    }
}
