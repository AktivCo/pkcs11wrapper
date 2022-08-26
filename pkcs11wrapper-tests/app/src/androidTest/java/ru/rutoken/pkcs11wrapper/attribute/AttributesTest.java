package ru.rutoken.pkcs11wrapper.attribute;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_APPLICATION;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_CLASS;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ObjectClass.CKO_DATA;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.getDataObjectAttributes;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11ObjectClassAttribute;
import ru.rutoken.pkcs11wrapper.object.data.Pkcs11DataObject;
import ru.rutoken.pkcs11wrapper.rule.CreateObjectRule;
import ru.rutoken.pkcs11wrapper.rule.ModuleRule;
import ru.rutoken.pkcs11wrapper.rule.SessionRule;
import ru.rutoken.pkcs11wrapper.rule.SlotRule;
import ru.rutoken.pkcs11wrapper.rule.TokenRule;

public class AttributesTest {
    private static final ModuleRule sModule = new ModuleRule();
    private static final SlotRule sSlot = new SlotRule(sModule);
    private static final TokenRule sToken = new TokenRule(sSlot);
    private static final SessionRule sSession = new SessionRule(sToken);
    private static final CreateObjectRule sObject = new CreateObjectRule(sSession,
            getDataObjectAttributes(sModule.getValue().getAttributeFactory()));
    @ClassRule
    public static final TestRule sRuleChain = RuleChain.outerRule(sModule).around(sSlot).around(sToken)
            .around(sSession).around(sObject);

    @Test
    public void getAttributeValue() {
        final var attribute = new Pkcs11ObjectClassAttribute(CKA_CLASS);
        Attributes.getAttributeValue(sSession.getValue(), sObject.getValue().getHandle(), attribute);
        assertThat(attribute.getLongValue(), is(CKO_DATA.getAsLong()));
    }

    @Test
    public void getAttributeValueByGetter() {
        final var attribute = sObject.getValue().getClassAttributeValue(sSession.getValue());
        assertThat(attribute.getLongValue(), is(CKO_DATA.getAsLong()));
    }

    @Test
    public void getAttributeValueByGenericShortcutGetter() {
        final var attribute = sObject.getValue().getLongAttributeValue(sSession.getValue(), CKA_CLASS);
        assertThat(attribute.getLongValue(), is(CKO_DATA.getAsLong()));
    }

    @Test
    public void getAttributeValueByGenericGetter() {
        final var attribute = sObject.getValue().getAttributeValue(sSession.getValue(), CKA_CLASS);
        assertThat(attribute.getValue(), is(CKO_DATA.getAsLong()));
    }

    @Test
    public void getAttributeValueObjectGenericApi() {
        final var dataObject = (Pkcs11DataObject) sObject.getValue();
        // make Pkcs11BaseObject for same handle
        final var object = sModule.getValue().getObjectFactory().makeObject(dataObject.getHandle());

        final var dataObjectApplicationValue =
                dataObject.getApplicationAttributeValue(sSession.getValue()).getStringValue();

        assertThat(object.getAttributeValue(sSession.getValue(), CKA_APPLICATION).getValue(),
                is(dataObjectApplicationValue));

        assertThat(object.getStringAttributeValue(sSession.getValue(), CKA_APPLICATION).getStringValue(),
                is(dataObjectApplicationValue));

        assertThat(object.getAttributeValue(Pkcs11StringAttribute.class, sSession.getValue(), CKA_APPLICATION)
                .getStringValue(), is(dataObjectApplicationValue));
    }
}