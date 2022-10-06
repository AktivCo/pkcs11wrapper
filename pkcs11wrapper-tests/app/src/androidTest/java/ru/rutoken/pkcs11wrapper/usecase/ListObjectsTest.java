package ru.rutoken.pkcs11wrapper.usecase;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import java.util.Calendar;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11Date;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;
import ru.rutoken.pkcs11wrapper.object.certificate.Pkcs11CertificateObject;
import ru.rutoken.pkcs11wrapper.object.certificate.Pkcs11X509PublicKeyCertificateObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11KeyObject;
import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SessionRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.SlotRule;
import ru.rutoken.pkcs11wrapper.rule.highlevel.TokenRule;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

/**
 * Reads some objects from token.
 * These tests require token that contains keys and certificate (ra.rutoken.ru)
 */
public class ListObjectsTest {
    private static final ModuleRule sModule = new ModuleRule();
    private static final SlotRule sSlot = new SlotRule(sModule);
    private static final TokenRule sToken = new TokenRule(sSlot);
    private static final SessionRule sSession = new SessionRule(sToken);
    @ClassRule
    public static final TestRule sRuleChain = RuleChain.outerRule(sModule).around(sSlot).around(sToken)
            .around(sSession);

    @Ignore("needs token with certificate")
    @Test
    public void listCertificates() {
        final var certificates = sSession.getValue().getObjectManager()
                .findObjectsAtOnce(Pkcs11CertificateObject.class);
        assertThat("no certificates found", certificates.size(), greaterThan(0));
        for (var certificate : certificates) {
            final var startDateAttribute = certificate.getStartDateAttributeValue(sSession.getValue());
            checkDate(startDateAttribute.getDateValue());
            final var endDateAttribute = certificate.getEndDateAttributeValue(sSession.getValue());
            checkDate(endDateAttribute.getDateValue());

            // get same attribute with more generic api
            final var startDateAttribute1 = certificate
                    .getAttributeValue(sSession.getValue(), Pkcs11AttributeType.CKA_START_DATE);
            assertSame("different instances for same attribute", startDateAttribute1, startDateAttribute);
        }
    }

    /**
     * findObjectsAtOnce uses template deduced from object class, its not possible for all classes.
     * Alternative is to return template for nearest super class, but this may be very misleading.
     */
    @Test(expected = RuntimeException.class)
    public void listKeys_negative() {
        final var keys = sSession.getValue().getObjectManager()
                .findObjectsAtOnce(Pkcs11KeyObject.class);
    }

    @Ignore("needs non-empty token")
    @Test
    public void listObjects() {
        final var objects = sSession.getValue().getObjectManager().findObjectsAtOnce(Pkcs11Object.class);
        assertThat("no objects found", objects.size(), greaterThan(0));
    }

    @Ignore("needs token with certificate")
    @Test
    public void listX509Certificates() {
        final var x509certificates =
                sSession.getValue().getObjectManager().findObjectsAtOnce(Pkcs11X509PublicKeyCertificateObject.class);
        assertThat("no X509 certificates found", x509certificates.size(), greaterThan(0));

        for (var certificate : x509certificates) {
            final var value = certificate.getValueAttributeValue(sSession.getValue()).getByteArrayValue();
            assertThat("X509 certificate value is empty", value.length, greaterThan(0));
        }
    }

    private void checkDate(Pkcs11Date date) {
        final var calendar = Calendar.getInstance();
        calendar.setTime(date.getDate());
        assertThat("year is out of range", calendar.get(Calendar.YEAR),
                allOf(greaterThanOrEqualTo(1900), lessThanOrEqualTo(9999)));
        assertThat("month is out of range", calendar.get(Calendar.MONTH),
                allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(12)));
        assertThat("day is out of range", calendar.get(Calendar.DAY_OF_MONTH),
                allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(31)));
    }
}
