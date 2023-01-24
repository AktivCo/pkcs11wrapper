/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkAttribute;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkMechanismInfo;
import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkTokenInfo;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkTokenInfoExtended;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.fake.FakeCkTokenInfoExtendedImpl;

public class FakeToken {
    public final Set<FakeSession> sessions = new HashSet<>();
    public final Map<Long, FakeObject> objects;
    public final Map<IPkcs11MechanismType, CkMechanismInfo> mechanisms;
    private final byte[] mSerialNumber;
    private final long mTokenType;
    @Nullable
    public Search search;

    FakeToken(byte[] serialNumber,
              long tokenType,
              Map<Long, FakeObject> objects,
              Map<IPkcs11MechanismType, CkMechanismInfo> mechanisms) {
        mSerialNumber = Objects.requireNonNull(serialNumber);
        mTokenType = tokenType;
        this.objects = Objects.requireNonNull(objects);
        this.mechanisms = Objects.requireNonNull(mechanisms);
    }

    CkTokenInfo getTokenInfo() {
        return new FakeCkTokenInfoImpl(mSerialNumber);
    }

    public CkTokenInfoExtended getTokenInfoExtended() {
        return new FakeCkTokenInfoExtendedImpl(mTokenType);
    }

    FakeSession openSession(FakeCkSessionInfoImpl sessionInfo) {
        final FakeSession session = new FakeSession(this, sessionInfo);
        sessions.add(session);
        return session;
    }

    void closeAllSessions() {
        final Iterator<FakeSession> iterator = sessions.iterator();
        while (iterator.hasNext()) {
            final FakeSession session = iterator.next();
            session.close();
            iterator.remove();
        }
    }

    public static class Search {
        final List<CkAttribute> template;
        boolean needFindObjects = true;

        Search(List<CkAttribute> template) {
            this.template = Objects.requireNonNull(template);
        }
    }
}
