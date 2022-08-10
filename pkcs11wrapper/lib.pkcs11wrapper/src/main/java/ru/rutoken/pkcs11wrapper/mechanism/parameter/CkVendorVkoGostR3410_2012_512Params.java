package ru.rutoken.pkcs11wrapper.mechanism.parameter;

import java.util.Objects;

// FIXME: remove after updating PKCS to 2.3.0. See https://jira.aktivco.ru/browse/PKCSECP-1633 for more information.
public class CkVendorVkoGostR3410_2012_512Params implements Pkcs11MechanismParams {
    private final long mKdf;
    private final byte[] mPublicKeyValue;
    private final byte[] mUkm;

    public CkVendorVkoGostR3410_2012_512Params(long kdf, byte[] publicKeyValue, byte[] ukm) {
        mKdf = kdf;
        mPublicKeyValue = Objects.requireNonNull(publicKeyValue);
        mUkm = Objects.requireNonNull(ukm);
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }

    public long getKdf() {
        return mKdf;
    }

    public byte[] getPublicKeyValue() {
        return mPublicKeyValue;
    }

    public byte[] getUkm() {
        return mUkm;
    }
}
