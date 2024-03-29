/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.object.factory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ObjectClass;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11EcPrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11EcPublicKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11GostPrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11GostPublicKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11KeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11RsaPrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11RsaPublicKeyObject;
import ru.rutoken.pkcs11wrapper.rule.highlevel.ModuleRule;

@RunWith(AndroidJUnit4.class)
public class IPkcs11ObjectFactoryTest {
    private static final ModuleRule sModule = new ModuleRule();

    @Test
    public void makeObject() {
        final var object = getFactory().makeObject(0);
        assertNotNull(object);
    }

    @Test
    public void makeObjectOfSpecifiedClass() {
        final var rsaPublicKey = getFactory().makeObject(Pkcs11RsaPublicKeyObject.class, 0);
        assertNotNull(rsaPublicKey);
        final var rsaPrivateKey = getFactory().makeObject(Pkcs11RsaPrivateKeyObject.class, 0);
        assertNotNull(rsaPrivateKey);

        final var ecPublicKey = getFactory().makeObject(Pkcs11EcPublicKeyObject.class, 0);
        assertNotNull(ecPublicKey);
        final var ecPrivateKey = getFactory().makeObject(Pkcs11EcPrivateKeyObject.class, 0);
        assertNotNull(ecPrivateKey);

        final var gostPublicKey = getFactory().makeObject(Pkcs11GostPublicKeyObject.class, 0);
        assertNotNull(gostPublicKey);
        final var gostPrivateKey = getFactory().makeObject(Pkcs11GostPrivateKeyObject.class, 0);
        assertNotNull(gostPrivateKey);
    }

    @Test
    public void makeTemplate() {
        final var attributeFactory = sModule.getValue().getAttributeFactory();
        final var template = getFactory().makeTemplate(Pkcs11RsaPrivateKeyObject.class);

        final List<Pkcs11Attribute> expectedTemplate = new ArrayList<>();
        expectedTemplate.add(attributeFactory.makeAttribute(Pkcs11AttributeType.CKA_KEY_TYPE, Pkcs11KeyType.CKK_RSA));
        expectedTemplate.add(attributeFactory.makeAttribute(Pkcs11AttributeType.CKA_CLASS,
                Pkcs11ObjectClass.CKO_PRIVATE_KEY));

        for (var expectedAttribute : expectedTemplate) {
            var found = false;
            for (var attribute : template) {
                if (attribute.getType().equals(expectedAttribute.getType()) &&
                        attribute.getValue().equals(expectedAttribute.getValue())) {
                    found = true;
                    break;
                }
            }
            assertTrue("template not found", found);
        }
    }

    @Test
    public void checkRegisteredObjectClasses() {
        for (var objectClass : getFactory().getRegisteredObjectClasses()) {
            checkObjectClass(objectClass);
        }
    }

    @Test(expected = RuntimeException.class)
    public void makeTemplate_negative() {
        getFactory().makeTemplate(Pkcs11KeyObject.class);
    }

    private void checkObjectClass(Class<? extends Pkcs11Object> objectClass) {
        assertTrue(objectClass.toString(), getFactory().isObjectClassRegistered(objectClass));
        getFactory().makeTemplate(objectClass);
        getFactory().makeObject(objectClass, 0);
    }

    private IPkcs11ObjectFactory getFactory() {
        return sModule.getValue().getObjectFactory();
    }
}