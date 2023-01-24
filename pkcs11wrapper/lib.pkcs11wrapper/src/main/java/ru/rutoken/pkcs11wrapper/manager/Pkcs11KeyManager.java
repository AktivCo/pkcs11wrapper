/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.manager;

import java.util.List;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11KeyPair;
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11KeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PublicKeyObject;
import ru.rutoken.pkcs11wrapper.reference.SessionReference;

public interface Pkcs11KeyManager extends SessionReference {
    default Pkcs11KeyObject generateKey(Pkcs11Mechanism mechanism, List<Pkcs11Attribute> template) {
        return generateKey(Pkcs11KeyObject.class, mechanism, template);
    }

    <Key extends Pkcs11KeyObject> Key generateKey(Class<Key> keyClass, Pkcs11Mechanism mechanism,
                                                  List<Pkcs11Attribute> template);

    default Pkcs11KeyPair<?, ?> generateKeyPair(Pkcs11Mechanism mechanism,
                                                List<Pkcs11Attribute> publicKeyTemplate,
                                                List<Pkcs11Attribute> privateKeyTemplate) {
        return generateKeyPair(Pkcs11PublicKeyObject.class, Pkcs11PrivateKeyObject.class, mechanism,
                publicKeyTemplate, privateKeyTemplate);
    }

    <PublicKey extends Pkcs11PublicKeyObject, PrivateKey extends Pkcs11PrivateKeyObject>
    Pkcs11KeyPair<PublicKey, PrivateKey> generateKeyPair(Class<PublicKey> publicKeyClass,
                                                         Class<PrivateKey> privateKeyClass,
                                                         Pkcs11Mechanism mechanism,
                                                         List<Pkcs11Attribute> publicKeyTemplate,
                                                         List<Pkcs11Attribute> privateKeyTemplate);

    byte[] wrapKey(Pkcs11Mechanism mechanism, Pkcs11KeyObject wrappingKey, Pkcs11KeyObject key);

    default Pkcs11KeyObject unwrapKey(Pkcs11Mechanism mechanism, Pkcs11KeyObject unwrappingKey,
                                      byte[] wrappedKey, List<Pkcs11Attribute> template) {
        return unwrapKey(Pkcs11KeyObject.class, mechanism, unwrappingKey, wrappedKey, template);
    }

    <Key extends Pkcs11KeyObject> Key unwrapKey(Class<Key> keyClass, Pkcs11Mechanism mechanism,
                                                Pkcs11KeyObject unwrappingKey,
                                                byte[] wrappedKey, List<Pkcs11Attribute> template);

    default Pkcs11KeyObject deriveKey(Pkcs11Mechanism mechanism, Pkcs11KeyObject baseKey,
                                      List<Pkcs11Attribute> template) {
        return deriveKey(Pkcs11KeyObject.class, mechanism, baseKey, template);
    }

    <Key extends Pkcs11KeyObject> Key deriveKey(Class<Key> keyClass, Pkcs11Mechanism mechanism,
                                                Pkcs11KeyObject baseKey, List<Pkcs11Attribute> template);
}
