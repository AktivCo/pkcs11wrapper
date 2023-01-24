/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.manager.impl;

import java.util.List;

import ru.rutoken.pkcs11wrapper.attribute.Attributes;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11KeyPair;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanism;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11KeyManager;
import ru.rutoken.pkcs11wrapper.mechanism.Pkcs11Mechanism;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11KeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PrivateKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PublicKeyObject;
import ru.rutoken.pkcs11wrapper.util.MutableLong;
import ru.rutoken.pkcs11wrapper.util.callconvention.ByteArrayCallConvention;

public class Pkcs11KeyManagerImpl extends BaseManager implements Pkcs11KeyManager {
    public Pkcs11KeyManagerImpl(Pkcs11Session session) {
        super(session);
    }

    @Override
    public <Key extends Pkcs11KeyObject> Key generateKey(Class<Key> keyClass, Pkcs11Mechanism mechanism,
                                                         List<Pkcs11Attribute> template) {
        final List<CkAttribute> ckAttributes = Attributes.makeCkAttributesList(template, getLowLevelFactory());
        final MutableLong key = new MutableLong();
        getApi().C_GenerateKey(
                mSession.getSessionHandle(), mechanism.toCkMechanism(getLowLevelFactory()), ckAttributes, key);
        return getObjectFactory().makeObject(keyClass, key.value);
    }

    @Override
    public <PublicKey extends Pkcs11PublicKeyObject, PrivateKey extends Pkcs11PrivateKeyObject>
    Pkcs11KeyPair<PublicKey, PrivateKey> generateKeyPair(Class<PublicKey> publicKeyClass,
                                                         Class<PrivateKey> privateKeyClass,
                                                         Pkcs11Mechanism mechanism,
                                                         List<Pkcs11Attribute> publicKeyTemplate,
                                                         List<Pkcs11Attribute> privateKeyTemplate) {
        final List<CkAttribute> publicKeyAttributes = Attributes.makeCkAttributesList(
                publicKeyTemplate, getLowLevelFactory());
        final List<CkAttribute> privateKeyAttributes = Attributes.makeCkAttributesList(
                privateKeyTemplate, getLowLevelFactory());
        final MutableLong publicKey = new MutableLong();
        final MutableLong privateKey = new MutableLong();
        getApi().C_GenerateKeyPair(
                mSession.getSessionHandle(), mechanism.toCkMechanism(getLowLevelFactory()),
                publicKeyAttributes, privateKeyAttributes, publicKey, privateKey);
        return new Pkcs11KeyPair<>(
                getObjectFactory().makeObject(publicKeyClass, publicKey.value),
                getObjectFactory().makeObject(privateKeyClass, privateKey.value));
    }

    @Override
    public byte[] wrapKey(Pkcs11Mechanism mechanism, Pkcs11KeyObject wrappingKey, Pkcs11KeyObject key) {
        final CkMechanism ckMechanism = mechanism.toCkMechanism(getLowLevelFactory());
        return new ByteArrayCallConvention("C_WrapKey") {
            @Override
            protected int getLength() {
                final MutableLong length = new MutableLong();
                getApi().C_WrapKey(mSession.getSessionHandle(), ckMechanism, wrappingKey.getHandle(),
                        key.getHandle(), null, length);
                return (int) length.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(byte[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(getLowLevelApi().C_WrapKey(
                        mSession.getSessionHandle(), ckMechanism, wrappingKey.getHandle(),
                        key.getHandle(), values, length));
            }
        }.call().values;
    }

    @Override
    public <Key extends Pkcs11KeyObject> Key unwrapKey(Class<Key> keyClass, Pkcs11Mechanism mechanism,
                                                       Pkcs11KeyObject unwrappingKey,
                                                       byte[] wrappedKey, List<Pkcs11Attribute> template) {
        final List<CkAttribute> ckAttributes = Attributes.makeCkAttributesList(template, getLowLevelFactory());
        final MutableLong key = new MutableLong();
        getApi().C_UnwrapKey(getSession().getSessionHandle(), mechanism.toCkMechanism(getLowLevelFactory()),
                unwrappingKey.getHandle(), wrappedKey, ckAttributes, key);
        return getObjectFactory().makeObject(keyClass, key.value);
    }

    @Override
    public <Key extends Pkcs11KeyObject> Key deriveKey(Class<Key> keyClass, Pkcs11Mechanism mechanism,
                                                       Pkcs11KeyObject baseKey,
                                                       List<Pkcs11Attribute> template) {
        final List<CkAttribute> ckAttributes = Attributes.makeCkAttributesList(template, getLowLevelFactory());
        final MutableLong key = new MutableLong();
        getApi().C_DeriveKey(getSession().getSessionHandle(), mechanism.toCkMechanism(getLowLevelFactory()),
                baseKey.getHandle(), ckAttributes, key);
        return getObjectFactory().makeObject(keyClass, key.value);
    }
}
