package ru.rutoken.pkcs11wrapper.main;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11ReturnValue;
import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11Flag;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11MechanismInfo;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11TokenInfo;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Token;
import ru.rutoken.pkcs11wrapper.util.MutableLong;
import ru.rutoken.pkcs11wrapper.util.callconvention.LongArrayCallConvention;

/**
 * Default token implementation.
 * This class may be extended to add vendor defined extended methods.
 * See {@link RtPkcs11Token} for example.
 */
public class Pkcs11TokenImpl implements Pkcs11Token {
    private final Pkcs11Slot mSlot;

    public Pkcs11TokenImpl(Pkcs11Slot slot) {
        mSlot = Objects.requireNonNull(slot);
    }

    @Override
    public Pkcs11Slot getSlot() {
        return mSlot;
    }

    @Override
    public void initToken(@Nullable String pin, String label) {
        final byte[] pinBytes = null != pin ? pin.getBytes() : null;

        final String cutLabel = label.substring(0, Math.min(label.length(), LABEL_LENGTH));
        final StringBuilder builder = new StringBuilder(cutLabel);
        while (builder.length() < LABEL_LENGTH)
            builder.append(' ');

        final byte[] labelData = builder.toString().getBytes();
        getApi().C_InitToken(getSlot().getId(), pinBytes, labelData);
    }

    @Override
    public Pkcs11TokenInfo getTokenInfo() {
        return new Pkcs11TokenInfo(getApi().C_GetTokenInfo(mSlot.getId()));
    }

    @Override
    public List<IPkcs11MechanismType> getMechanismList() {
        final long[] mechanisms = new LongArrayCallConvention("C_GetMechanismList") {
            @Override
            protected int getLength() {
                final MutableLong count = new MutableLong();
                getApi().C_GetMechanismList(mSlot.getId(), null, count);
                return (int) count.value;
            }

            @Override
            protected IPkcs11ReturnValue fillValues(long[] values, MutableLong length) {
                return IPkcs11ReturnValue.getInstance(
                        getLowLevelApi().C_GetMechanismList(mSlot.getId(), values, length));
            }
        }.call();

        final List<IPkcs11MechanismType> mechanismTypes = new ArrayList<>();
        for (long mechanism : mechanisms)
            mechanismTypes.add(IPkcs11MechanismType.getInstance(mechanism, getLowLevelFactory()));
        return mechanismTypes;
    }

    @Override
    public Pkcs11MechanismInfo getMechanismInfo(IPkcs11MechanismType type) {
        return new Pkcs11MechanismInfo(getApi().C_GetMechanismInfo(mSlot.getId(), type.getAsLong()));
    }

    @Override
    public Pkcs11Session openSession(boolean rwSession) {
        // CKF_SERIAL_SESSION should always be set for backward compatibility by the standard
        long flags = Pkcs11Flag.CKF_SERIAL_SESSION.getAsLong();
        flags |= rwSession ? Pkcs11Flag.CKF_RW_SESSION.getAsLong() : 0L;

        final long session = getApi().C_OpenSession(mSlot.getId(), flags, null, null);
        return getHighLevelFactory().makeSession(this, session);
    }

    @Override
    public void closeAllSessions() {
        getApi().C_CloseAllSessions(mSlot.getId());
    }

    @Override
    public boolean equals(Object otherObject) {
        if (otherObject instanceof Pkcs11TokenImpl) {
            Pkcs11TokenImpl other = (Pkcs11TokenImpl) otherObject;
            return (this == other) || Objects.equals(mSlot, other.mSlot);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mSlot);
    }

    @Override
    public String toString() {
        return "Pkcs11TokenImpl{" +
                "mSlot=" + mSlot +
                '}';
    }
}
