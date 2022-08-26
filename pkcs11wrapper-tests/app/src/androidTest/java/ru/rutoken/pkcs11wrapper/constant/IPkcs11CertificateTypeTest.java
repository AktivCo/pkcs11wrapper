package ru.rutoken.pkcs11wrapper.constant;

import org.junit.Test;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11CertificateType;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IPkcs11CertificateTypeTest {
    @Test
    public void getInstance() {
        assertThat(Pkcs11CertificateType.CKC_X_509,
                is(IPkcs11CertificateType.getInstance(Pkcs11CertificateType.CKC_X_509.getAsLong())));
    }

    /**
     * Any value even unexpected (unknown) by wrapper, should be handled without exception
     */
    @Test
    public void getInstanceUnknownValue() {
        final var value = Pkcs11CertificateType.CKC_VENDOR_DEFINED.getAsLong() + 1;
        assertThat(IPkcs11CertificateType.getInstance(value).getAsLong(), is(value));
    }
}