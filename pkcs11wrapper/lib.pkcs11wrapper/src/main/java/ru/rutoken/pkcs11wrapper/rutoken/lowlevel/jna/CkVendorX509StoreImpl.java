package ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna;

import com.sun.jna.NativeLong;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.rutoken.pkcs11jna.CK_VENDOR_BUFFER;
import ru.rutoken.pkcs11jna.CK_VENDOR_X509_STORE;
import ru.rutoken.pkcs11wrapper.rutoken.lowlevel.datatype.CkVendorX509Store;

import static ru.rutoken.pkcs11wrapper.rutoken.lowlevel.jna.RtPkcs11JnaLowLevelApi.makePointerFromBytes;

class CkVendorX509StoreImpl implements CkVendorX509Store {
    private final CK_VENDOR_X509_STORE mStore;

    CkVendorX509StoreImpl(CK_VENDOR_X509_STORE store) {
        mStore = Objects.requireNonNull(store);
    }

    @Nullable
    private static List<byte[]> ckVendorBuffersAsList(@Nullable CK_VENDOR_BUFFER buffersPointer,
                                                      NativeLong buffersCount) {
        if (buffersPointer == null)
            return null;

        final List<byte[]> result = new ArrayList<>();

        CK_VENDOR_BUFFER firstBuffer = new CK_VENDOR_BUFFER(buffersPointer.pData);
        CK_VENDOR_BUFFER[] buffers = (CK_VENDOR_BUFFER[]) firstBuffer.toArray(buffersCount.intValue());

        for (CK_VENDOR_BUFFER buffer : buffers)
            result.add(buffer.pData.getByteArray(0, buffer.ulSize.intValue()));

        return result;
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
    @Nullable
    public List<byte[]> getTrustedCertificates() {
        return ckVendorBuffersAsList(mStore.pTrustedCertificates, mStore.ulTrustedCertificatesCount);
    }

    @Override
    public void setTrustedCertificates(@Nullable List<byte[]> trustedCertificates) {
        mStore.pTrustedCertificates = toCkVendorBufferArray(trustedCertificates);
        mStore.ulTrustedCertificatesCount = new NativeLong(length(trustedCertificates));
    }

    @Override
    @Nullable
    public List<byte[]> getCertificates() {
        return ckVendorBuffersAsList(mStore.pCertificates, mStore.ulCertificatesCount);
    }

    @Override
    public void setCertificates(@Nullable List<byte[]> certificates) {
        mStore.pCertificates = toCkVendorBufferArray(certificates);
        mStore.ulCertificatesCount = new NativeLong(length(certificates));
    }

    @Override
    @Nullable
    public List<byte[]> getCrls() {
        return ckVendorBuffersAsList(mStore.pCrls, mStore.ulCrlsCount);
    }

    @Override
    public void setCrls(@Nullable List<byte[]> crls) {
        mStore.pCrls = toCkVendorBufferArray(crls);
        mStore.ulCrlsCount = new NativeLong(length(crls));
    }
}
