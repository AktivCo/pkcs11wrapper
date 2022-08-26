package ru.rutoken.pkcs11wrapper.attribute;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11LongAttribute;
import ru.rutoken.pkcs11wrapper.attribute.longvalue.Pkcs11ObjectClassAttribute;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ObjectClass;
import ru.rutoken.pkcs11wrapper.rule.ModuleRule;

/**
 * We have to use instrumented tests because of unit tests problems with native libraries.
 */
@RunWith(AndroidJUnit4.class)
public class IPkcs11AttributeFactoryTest {
    private static final ModuleRule sModule = new ModuleRule();

    private static IPkcs11AttributeFactory getFactory() {
        return sModule.getValue().getAttributeFactory();
    }

    @Test
    public void makeAttribute() {
        var attribute = getFactory().makeAttribute(Pkcs11AttributeType.CKA_CLASS);
        assertTrue("wrong type", attribute instanceof Pkcs11ObjectClassAttribute);

        var longAttribute = getFactory()
                .makeAttribute(Pkcs11LongAttribute.class, Pkcs11AttributeType.CKA_CLASS);
        assertTrue("wrong type", longAttribute instanceof Pkcs11ObjectClassAttribute);

        var classAttribute = getFactory()
                .makeAttribute(Pkcs11ObjectClassAttribute.class, Pkcs11AttributeType.CKA_CLASS);
    }

    @Test
    public void makeAttributeSetBooleanValue() {
        final var value = false;
        var attribute = getFactory().makeAttribute(Pkcs11AttributeType.CKA_TOKEN, value);
        assertTrue("wrong type", attribute instanceof Pkcs11BooleanAttribute);
        assertEquals("value not equal", attribute.getValue(), value);
    }

    @Test
    public void makeAttributeSetStringValue() {
        var value = "test";
        var attribute = getFactory().makeAttribute(Pkcs11AttributeType.CKA_LABEL, value);
        assertTrue("wrong type", attribute instanceof Pkcs11StringAttribute);
        assertEquals("value not equal", attribute.getValue(), value);
    }

    @Test
    public void makeAttributeSetIntValue() {
        //int is not long
        var value = 0;
        var attribute = getFactory().makeAttribute(Pkcs11AttributeType.CKA_CERTIFICATE_CATEGORY, value);
        assertTrue("wrong type", attribute instanceof Pkcs11LongAttribute);
        assertEquals("value not equal", attribute.getValue(), (long) value);
    }

    @Test
    public void makeAttributeSetLongValue() {
        var value = 0L;
        var attribute = getFactory().makeAttribute(Pkcs11AttributeType.CKA_CERTIFICATE_CATEGORY, value);
        assertTrue("wrong type", attribute instanceof Pkcs11LongAttribute);
        assertEquals("value not equal", attribute.getValue(), value);
    }

    @Test
    public void makeAttributeSetEnumValue() {
        var value = Pkcs11ObjectClass.CKO_PRIVATE_KEY;
        var attribute = getFactory().makeAttribute(Pkcs11AttributeType.CKA_CLASS, value);
        assertTrue("wrong type", attribute instanceof Pkcs11ObjectClassAttribute);
        assertEquals("value not equal", attribute.getValue(), value.getAsLong());
    }

    @Test(expected = ClassCastException.class)
    public void makeAttributeWrongClass_negative() {
        var classAttribute = getFactory()
                .makeAttribute(Pkcs11ObjectClassAttribute.class, Pkcs11AttributeType.CKA_KEY_TYPE);
    }

    @Test
    public void makeAllAttributes() {
        for (IPkcs11AttributeType type : Pkcs11AttributeType.values()) {
            getFactory().makeAttribute(type);
        }
    }
}
