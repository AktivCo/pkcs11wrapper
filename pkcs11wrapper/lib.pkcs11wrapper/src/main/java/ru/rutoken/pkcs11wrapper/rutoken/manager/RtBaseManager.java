/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.manager;

import ru.rutoken.pkcs11wrapper.manager.impl.BaseManager;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelApi;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.IRtPkcs11LowLevelFactory;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Api;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Session;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Token;

class RtBaseManager extends BaseManager {
    protected RtBaseManager(RtPkcs11Session session) {
        super(session);
    }

    @Override
    public RtPkcs11Session getSession() {
        return (RtPkcs11Session) super.getSession();
    }

    @Override
    public IRtPkcs11LowLevelApi getLowLevelApi() {
        return getSession().getLowLevelApi();
    }

    @Override
    public IRtPkcs11LowLevelFactory getLowLevelFactory() {
        return getSession().getLowLevelFactory();
    }

    @Override
    public RtPkcs11Api getApi() {
        return getSession().getApi();
    }

    @Override
    public RtPkcs11Token getToken() {
        return getSession().getToken();
    }
}
