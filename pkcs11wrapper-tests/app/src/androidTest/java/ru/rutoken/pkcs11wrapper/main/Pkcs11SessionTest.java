package ru.rutoken.pkcs11wrapper.main;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11SessionState;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11UserType;
import ru.rutoken.pkcs11wrapper.rule.ModuleRule;
import ru.rutoken.pkcs11wrapper.rule.SessionRule;
import ru.rutoken.pkcs11wrapper.rule.SlotRule;
import ru.rutoken.pkcs11wrapper.rule.TokenRule;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class Pkcs11SessionTest {
    private static final ModuleRule sModule = new ModuleRule();
    private static final SlotRule sSlot = new SlotRule(sModule);
    private static final TokenRule sToken = new TokenRule(sSlot);
    private static final SessionRule sSession = new SessionRule(sToken);
    @ClassRule
    public static final TestRule sRuleChain = RuleChain.outerRule(sModule).around(sSlot).around(sToken)
            .around(sSession);

    @Test
    public void getSessionInfo() {
        final var info = sSession.getValue().getSessionInfo();
        assertTrue("not RW session", info.isRwSession());
        assertTrue("not serial session", info.isSerialSession());
        assertSame("not public session", Pkcs11SessionState.CKS_RW_PUBLIC_SESSION, info.getState());
    }

    @Test
    public void loginUser() {
        try (final var ignore = loginAsUser()) {
            final var info = sSession.getValue().getSessionInfo();
            assertSame("not user session", Pkcs11SessionState.CKS_RW_USER_FUNCTIONS, info.getState());
        }
        final var info = sSession.getValue().getSessionInfo();
        assertSame("not public session", Pkcs11SessionState.CKS_RW_PUBLIC_SESSION, info.getState());
    }

    @Test
    public void loginAdmin() {
        try (final var ignore = sSession.getValue()
                .login(Pkcs11UserType.CKU_SO, UtilsKt.DEFAULT_ADMIN_PIN)) {
            final var info = sSession.getValue().getSessionInfo();
            assertSame("not admin session", Pkcs11SessionState.CKS_RW_SO_FUNCTIONS, info.getState());
        }
        final var info = sSession.getValue().getSessionInfo();
        assertSame("not public session", Pkcs11SessionState.CKS_RW_PUBLIC_SESSION, info.getState());
    }

    @Test
    public void setPin() {
        try (final var ignore = loginAsUser()) {
            sSession.getValue().setPin(UtilsKt.DEFAULT_USER_PIN, UtilsKt.DEFAULT_USER_PIN);
        }
    }

    @Test
    public void getOperationState() {
        try {
            sSession.getValue().getOperationState();
        } catch (Pkcs11Exception e) {
            if (Pkcs11ReturnValue.CKR_FUNCTION_NOT_SUPPORTED != e.getCode())
                throw e;
        }
    }

    private Pkcs11Session.LoginGuard loginAsUser() {
        return sSession.getValue().login(Pkcs11UserType.CKU_USER, UtilsKt.DEFAULT_USER_PIN);
    }
}
