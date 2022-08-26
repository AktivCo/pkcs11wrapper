package ru.rutoken.pkcs11wrapper.constant;

import org.junit.Test;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ObjectClass;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IPkcs11ObjectClassTest {
    @Test
    public void getInstance() {
        assertThat(Pkcs11ObjectClass.CKO_DATA,
                is(IPkcs11ObjectClass.getInstance(Pkcs11ObjectClass.CKO_DATA.getAsLong())));
    }

    /**
     * Any value even unexpected (unknown) by wrapper, should be handled without exception
     */
    @Test
    public void getInstanceUnknownValue() {
        final var value = Pkcs11ObjectClass.CKO_VENDOR_DEFINED.getAsLong() + 1;
        assertThat(IPkcs11ObjectClass.getInstance(value).getAsLong(), is(value));
    }
}