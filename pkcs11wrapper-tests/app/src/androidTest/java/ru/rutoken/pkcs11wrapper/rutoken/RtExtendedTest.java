package ru.rutoken.pkcs11wrapper.rutoken;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.DEFAULT_USER_PIN;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.DN;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.EXTENSIONS;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.makeGostR3410_2012_256KeyPairRule;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.pkcs11wrapper.rule.highlevel.GenerateKeyPairRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.LoginRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.RtModuleRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SessionRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SlotRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.TokenRule;
import ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Session;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Token;

public class RtExtendedTest {
    private static final RtModuleRule sModule = new RtModuleRule();
    private static final SlotRule sSlot = new SlotRule(sModule);
    private static final TokenRule sToken = new TokenRule(sSlot);
    private static final SessionRule sSession = new SessionRule(sToken);
    private static final LoginRule sLogin = new LoginRule(sSession, Pkcs11UserType.CKU_USER, DEFAULT_USER_PIN);
    private static final GenerateKeyPairRule<?, ?> sKeyPair =
            makeGostR3410_2012_256KeyPairRule(sModule.getValue().getAttributeFactory(), sSession);
    @ClassRule
    public static final TestRule sRuleChain = RuleChain.outerRule(sModule).around(sSlot).around(sToken)
            .around(sSession).around(sLogin).around(sKeyPair);

    @Test
    public void getTokenInfoExtended() {
        final var info = ((RtPkcs11Token) sToken.getValue()).getTokenInfoExtended();
    }

    @Test
    public void createCsr() {
        final var csr = ((RtPkcs11Session) sSession.getValue()).createCsr(sKeyPair.getValue().getPublicKey(), DN,
                sKeyPair.getValue().getPrivateKey(), null, EXTENSIONS);
        final var encodedCertificate = GostDemoCA.INSTANCE.issueCertificate(csr);
    }

    @Test
    public void vendorReturnValue() {
        final var value = IPkcs11ReturnValue.getInstance(
                RtPkcs11ReturnValue.CKR_CORRUPTED_MAPFILE.getAsLong(), sModule.getValue().getVendorExtensions());
        assertThat(value, is(RtPkcs11ReturnValue.CKR_CORRUPTED_MAPFILE));
    }
}
