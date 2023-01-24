/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.manager.impl;

import java.util.ArrayList;
import java.util.List;

import ru.rutoken.pkcs11wrapper.attribute.Attributes;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.main.Pkcs11Session;
import ru.rutoken.pkcs11wrapper.manager.Pkcs11ObjectManager;
import ru.rutoken.pkcs11wrapper.object.Pkcs11Object;
import ru.rutoken.pkcs11wrapper.util.MutableLong;

public class Pkcs11ObjectManagerImpl extends BaseManager implements Pkcs11ObjectManager {
    /**
     * Maximum number of objects searched by iteration in findObjectsAtOnce functions
     */
    private int mMaxObjectCount = 50;

    public Pkcs11ObjectManagerImpl(Pkcs11Session session) {
        super(session);
    }

    private static <Obj extends Pkcs11Object> Obj requireSingleObject(List<Obj> objects) {
        if (objects.size() != 1)
            throw new IllegalStateException(objects.size() + " objects found");
        return objects.get(0);
    }

    @Override
    public int getMaxObjectCount() {
        return mMaxObjectCount;
    }

    @Override
    public void setMaxObjectCount(int maxObjectCount) {
        mMaxObjectCount = maxObjectCount;
    }

    @Override
    public Pkcs11Object findSingleObject(List<Pkcs11Attribute> template) {
        return requireSingleObject(findObjectsAtOnce(template));
    }

    @Override
    public <Obj extends Pkcs11Object> Obj findSingleObject(Class<Obj> objectClass) {
        return requireSingleObject(findObjectsAtOnce(objectClass));
    }

    @Override
    public <Obj extends Pkcs11Object> Obj findSingleObject(Class<Obj> objectClass,
                                                           List<Pkcs11Attribute> template) {
        return requireSingleObject(findObjectsAtOnce(objectClass, template));
    }

    @Override
    public void findObjectsInit(List<Pkcs11Attribute> template) {
        final List<CkAttribute> ckAttributes = Attributes.makeCkAttributesList(template, getLowLevelFactory());
        getApi().C_FindObjectsInit(mSession.getSessionHandle(), ckAttributes);
    }

    @Override
    public List<Pkcs11Object> findObjects(int maxObjectCount) {
        final long[] objectHandles = new long[maxObjectCount];
        final MutableLong actualCount = new MutableLong();
        getApi().C_FindObjects(mSession.getSessionHandle(), objectHandles, objectHandles.length, actualCount);

        final List<Pkcs11Object> objects = new ArrayList<>((int) actualCount.value);
        for (int h = 0; h < actualCount.value; ++h) {
            //type is dynamic
            try {
                objects.add(getObjectFactory().makeObject(mSession, objectHandles[h]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return objects;
    }

    @Override
    public void findObjectsFinal() {
        getApi().C_FindObjectsFinal(mSession.getSessionHandle());
    }

    @Override
    public Pkcs11Object createObject(List<Pkcs11Attribute> template) {
        return getObjectFactory().makeObject(mSession, createObjectHandle(template));
    }

    @Override
    public <Obj extends Pkcs11Object> Obj createObject(Class<Obj> objectClass, List<Pkcs11Attribute> template) {
        return getObjectFactory().makeObject(objectClass, createObjectHandle(template));
    }

    @Override
    public <Obj extends Pkcs11Object> Obj copyObject(Obj object, List<Pkcs11Attribute> template) {
        final List<CkAttribute> ckAttributes = Attributes.makeCkAttributesList(template, getLowLevelFactory());
        final long newObject = getApi().C_CopyObject(mSession.getSessionHandle(), object.getHandle(), ckAttributes);
        @SuppressWarnings("unchecked") final Obj copy = (Obj) getObjectFactory().makeObject(
                object.getClass(), newObject);
        return copy;
    }

    @Override
    public void destroyObject(Pkcs11Object object) {
        getApi().C_DestroyObject(mSession.getSessionHandle(), object.getHandle());
    }

    @Override
    public long getObjectSize(Pkcs11Object object) {
        return getApi().C_GetObjectSize(mSession.getSessionHandle(), object.getHandle());
    }

    private long createObjectHandle(List<Pkcs11Attribute> template) {
        final List<CkAttribute> ckAttributes = Attributes.makeCkAttributesList(template, getLowLevelFactory());
        return getApi().C_CreateObject(mSession.getSessionHandle(), ckAttributes);
    }
}
