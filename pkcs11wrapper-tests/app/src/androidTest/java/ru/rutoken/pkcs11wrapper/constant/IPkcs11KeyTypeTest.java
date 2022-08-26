package ru.rutoken.pkcs11wrapper.constant;

import org.junit.Test;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IPkcs11KeyTypeTest {
    @Test
    public void getInstance() {
        assertThat(Pkcs11KeyType.CKK_RSA,
                is(IPkcs11KeyType.getInstance(Pkcs11KeyType.CKK_RSA.getAsLong())));
    }

    /**
     * Any value even unexpected (unknown) by wrapper, should be handled without exception
     */
    @Test
    public void getInstanceUnknownValue() {
        final var value = Pkcs11KeyType.CKK_VENDOR_DEFINED.getAsLong() + 1;
        assertThat(IPkcs11KeyType.getInstance(value).getAsLong(), is(value));
    }
}