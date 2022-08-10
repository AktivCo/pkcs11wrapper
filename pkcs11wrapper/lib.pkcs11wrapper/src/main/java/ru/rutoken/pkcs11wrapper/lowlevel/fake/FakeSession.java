package ru.rutoken.pkcs11wrapper.lowlevel.fake;

import java.io.Closeable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class FakeSession implements Closeable {
    static final Map<Long, FakeSession> sessions = new HashMap<>();
    public final long handle;
    public final FakeToken token;
    private FakeCkSessionInfoImpl mSessionInfo;

    FakeSession(FakeToken token, FakeCkSessionInfoImpl sessionInfo) {
        this.token = Objects.requireNonNull(token);
        setSessionInfo(sessionInfo);
        handle = sessions.size();

        sessions.put(handle, this);
    }

    public FakeCkSessionInfoImpl getSessionInfo() {
        return mSessionInfo;
    }

    public void setSessionInfo(FakeCkSessionInfoImpl sessionInfo) {
        mSessionInfo = Objects.requireNonNull(sessionInfo);
    }

    @Override
    public void close() {
        sessions.remove(handle);
    }
}
