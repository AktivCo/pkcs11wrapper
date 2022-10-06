package ru.rutoken.pkcs11wrapper.main;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SlotRule;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class Pkcs11SlotTest {
    private static final ModuleRule sModule = new ModuleRule();
    private static final SlotRule sSlot = new SlotRule(sModule);
    @ClassRule
    public static final TestRule sRuleChain = RuleChain.outerRule(sModule).around(sSlot);

    @Test
    public void getSlotInfo() {
        final var info = sSlot.getValue().getSlotInfo();
        assertThat("unexpected slot description", info.getSlotDescription(), containsString("Rutoken"));
    }
}
