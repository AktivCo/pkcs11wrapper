package ru.rutoken.pkcs11wrapper.manager;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SessionRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SlotRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.TokenRule;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class Pkcs11RandomNumberManagerTest {
    private static final ModuleRule sModule = new ModuleRule();
    private static final SlotRule sSlot = new SlotRule(sModule);
    private static final TokenRule sToken = new TokenRule(sSlot);
    private static final SessionRule sSession = new SessionRule(sToken);

    @ClassRule
    public static final TestRule sRuleChain = RuleChain.outerRule(sModule).around(sSlot).around(sToken)
            .around(sSession);

    @Test
    public void generateRandom() {
        final var length = 10;
        final var random = sSession.getValue().getRandomNumberManager().generateRandom(length);
        assertThat(random.length, is(length));
    }
}
