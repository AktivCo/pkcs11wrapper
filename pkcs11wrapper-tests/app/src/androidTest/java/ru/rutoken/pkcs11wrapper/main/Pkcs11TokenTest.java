package ru.rutoken.pkcs11wrapper.main;

import android.util.Log;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SlotRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.TokenRule;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class Pkcs11TokenTest {
    private static final ModuleRule sModule = new ModuleRule();
    private static final SlotRule sSlot = new SlotRule(sModule);
    private static final TokenRule sToken = new TokenRule(sSlot);
    @ClassRule
    public static final TestRule sRuleChain = RuleChain.outerRule(sModule).around(sSlot).around(sToken);

    @Test
    public void getTokenInfo() {
        final var info = sToken.getValue().getTokenInfo();
        assertEquals("Aktiv Co.                       ", info.getManufacturerId());
    }

    @Test
    public void getMechanismList() {
        final var mechanisms = sToken.getValue().getMechanismList();
        assertThat("no supported mechanisms", mechanisms.size(), greaterThan(0));
    }

    @Test
    public void getMechanismInfo() {
        for (var mechanism : sToken.getValue().getMechanismList()) {
            Log.d(getClass().getName(), "mechanism: " + mechanism);

            final var info = sToken.getValue().getMechanismInfo(mechanism);
            assertThat("min key size less than zero", info.getMinKeySize(), greaterThanOrEqualTo(0L));
            assertThat("max key size is less than min key size", info.getMaxKeySize(),
                    greaterThanOrEqualTo(info.getMinKeySize()));
        }
    }

    @Test
    public void openSession() {
        final Pkcs11Session session;
        try (var ignore = session = sToken.getValue().openSession(true)) {
        }
        checkSessionClosed(session);
    }

    @Test
    public void closeAllSessions() {
        final Pkcs11Session session1;
        final Pkcs11Session session2;
        try {
            session1 = sToken.getValue().openSession(true);
            session2 = sToken.getValue().openSession(true);
        } finally {
            sToken.getValue().closeAllSessions();
        }
        checkSessionClosed(session1);
        checkSessionClosed(session2);
    }

    private void checkSessionClosed(Pkcs11Session session) {
        try {
            session.getSessionInfo();
            fail("calling getSessionInfo() on closed session should throw");
        } catch (Pkcs11Exception e) {
            if (Pkcs11ReturnValue.CKR_SESSION_CLOSED != e.getCode() &&
                    Pkcs11ReturnValue.CKR_SESSION_HANDLE_INVALID != e.getCode())
                throw e;
        }
    }
}
