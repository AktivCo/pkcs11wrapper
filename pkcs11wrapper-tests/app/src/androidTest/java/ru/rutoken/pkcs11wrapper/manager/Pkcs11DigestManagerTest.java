package ru.rutoken.pkcs11wrapper.manager;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeTrue;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_GOSTR3411;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.DATA;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.isMechanismSupported;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SessionRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SlotRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.TokenRule;

public class Pkcs11DigestManagerTest {
    private static final ModuleRule sModule = new ModuleRule();
    private static final SlotRule sSlot = new SlotRule(sModule);
    private static final TokenRule sToken = new TokenRule(sSlot);
    private static final SessionRule sSession = new SessionRule(sToken);

    @ClassRule
    public static final TestRule sRuleChain = RuleChain.outerRule(sModule).around(sSlot).around(sToken)
            .around(sSession);

    @Test
    public void digestAtOnce() {
        assumeTrue(isMechanismSupported(sToken.getValue(), CKM_GOSTR3411));

        final var digest = sSession.getValue().getDigestManager().digestAtOnce(
                DATA, Pkcs11Mechanism.make(CKM_GOSTR3411));
        assertThat(digest.length, is(32));
    }
}
