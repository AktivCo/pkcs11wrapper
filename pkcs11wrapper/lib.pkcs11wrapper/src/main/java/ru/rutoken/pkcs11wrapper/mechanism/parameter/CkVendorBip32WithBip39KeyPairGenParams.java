/*
 * Copyright (c) 2025, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.mechanism.parameter;

public class CkVendorBip32WithBip39KeyPairGenParams implements Pkcs11MechanismParams {
    private final byte[] mPassphrase;
    private final long mMnemonicLength;

    public CkVendorBip32WithBip39KeyPairGenParams(byte[] passphrase, long mnemonicLength) {
        mPassphrase = passphrase;
        mMnemonicLength = mnemonicLength;
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visit(this);
    }

    public byte[] getPassphrase() {
        return mPassphrase;
    }

    public long getMnemonicLength() {
        return mMnemonicLength;
    }
}
