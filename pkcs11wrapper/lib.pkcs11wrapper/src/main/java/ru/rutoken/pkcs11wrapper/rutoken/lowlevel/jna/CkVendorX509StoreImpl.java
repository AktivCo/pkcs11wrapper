/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna;

import com.sun.jna.NativeLong;
import org.jetbrains.annotations.Nullable;
import ru.rutoken.pkcs11jna.CK_VENDOR_BUFFER;
import ru.rutoken.pkcs11jna.CK_VENDOR_X509_STORE;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorX509Store;

import java.util.List;
import java.util.Objects;

import static ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna.RtPkcs11JnaLowLevelApi.makePointerFromBytes;

class CkVendorX509StoreImpl implements CkVendorX509Store {
    private final CK_VENDOR_X509_STORE mStore;

    CkVendorX509StoreImpl(CK_VENDOR_X509_STORE store) {
        mStore = Objects.requireNonNull(store);
    }

    @Nullable
    private static CK_VENDOR_BUFFER.ByReference toCkVendorBufferArray(@Nullable List<byte[]> list) {
        if (list == null || list.isEmpty())
            return null;

        final CK_VENDOR_BUFFER.ByReference[] buffers =
                (CK_VENDOR_BUFFER.ByReference[]) new CK_VENDOR_BUFFER.ByReference(list.get(0)).toArray(list.size());
        for (int i = 1; i < list.size(); i++) {
            buffers[i].pData = makePointerFromBytes(list.get(i));
            buffers[i].ulSize = new NativeLong(list.get(i).length);
        }

        return buffers[0];
    }

    private static int length(@Nullable List<byte[]> data) {
        return data != null ? data.size() : 0;
    }

    CK_VENDOR_X509_STORE getJnaValue() {
        return mStore;
    }

    @Override
    public void setTrustedCertificates(@Nullable List<byte[]> trustedCertificates) {
        mStore.pTrustedCertificates = toCkVendorBufferArray(trustedCertificates);
        mStore.ulTrustedCertificatesCount = new NativeLong(length(trustedCertificates));
    }

    @Override
    public void setCertificates(@Nullable List<byte[]> certificates) {
        mStore.pCertificates = toCkVendorBufferArray(certificates);
        mStore.ulCertificatesCount = new NativeLong(length(certificates));
    }

    @Override
    public void setCrls(@Nullable List<byte[]> crls) {
        mStore.pCrls = toCkVendorBufferArray(crls);
        mStore.ulCrlsCount = new NativeLong(length(crls));
    }
}
