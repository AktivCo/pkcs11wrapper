package ru.rutoken.pkcs11wrapper.manager;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static ru.rutoken.pkcs11wrapper.main.UtilsKt.getDataObjectAttributes;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import ru.rutoken.pkcs11wrapper.attribute.IPkcs11AttributeFactory;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Exception;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;
import ru.rutoken.pkcs11wrapper.object.data.Pkcs11DataObject;
import ru.rutoken.pkcs11wrapper.rule.CreateObjectRule;
import ru.rutoken.pkcs11wrapper.rule.ModuleRule;
import ru.rutoken.pkcs11wrapper.rule.SessionRule;
import ru.rutoken.pkcs11wrapper.rule.SlotRule;
import ru.rutoken.pkcs11wrapper.rule.TokenRule;

public class Pkcs11ObjectManagerTest {
    private static final ModuleRule sModule = new ModuleRule();
    private static final IPkcs11AttributeFactory sAttributeFactory = sModule.getValue().getAttributeFactory();
    private static final SlotRule sSlot = new SlotRule(sModule);
    private static final TokenRule sToken = new TokenRule(sSlot);
    private static final SessionRule sSession = new SessionRule(sToken);
    private static final CreateObjectRule sObject = new CreateObjectRule(sSession,
            getDataObjectAttributes(sAttributeFactory));
    @ClassRule
    public static final TestRule sRuleChain = RuleChain.outerRule(sModule).around(sSlot).around(sToken)
            .around(sSession).around(sObject);

    private static Pkcs11ObjectManager objectManager() {
        return sSession.getValue().getObjectManager();
    }

    @Test
    public void findSingleObject() {
        final var object = objectManager().findSingleObject(sObject.getTemplate());
        assertNotNull(object);
        assertThat(object.getHandle(), equalTo(sObject.getValue().getHandle()));
    }

    @Test
    public void findObjectsAtOnce() {
        final var objects = objectManager().findObjectsAtOnce(sObject.getTemplate());
        assertThat(objects, hasSize(1));
        assertThat(objects.get(0).getHandle(), equalTo(sObject.getValue().getHandle()));
    }

    @Test
    public void createObject() {
        Pkcs11Object object = null;
        try {
            object = objectManager().createObject(getDataObjectAttributes(sAttributeFactory));
            assertThat(object, instanceOf(Pkcs11DataObject.class));
        } finally {
            if (object != null)
                objectManager().destroyObject(object);
        }
    }

    @Test
    @Ignore("rtpkcs11ecp does not support this")
    public void copyObject() throws Pkcs11Exception {
        final var copy = objectManager().copyObject(objectManager().findSingleObject(sObject.getTemplate()));
        objectManager().destroyObject(copy);
    }

    @Test
    @Ignore("rtpkcs11ecp does not support this")
    public void getObjectSize() throws Pkcs11Exception {
        assertThat(objectManager().getObjectSize(objectManager().findSingleObject(sObject.getTemplate())), equalTo(10));
    }
}
