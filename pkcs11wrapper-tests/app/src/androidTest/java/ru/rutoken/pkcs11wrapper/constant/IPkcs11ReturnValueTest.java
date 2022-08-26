package ru.rutoken.pkcs11wrapper.constant;

import org.junit.Test;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11ReturnValue;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IPkcs11ReturnValueTest {
    @Test
    public void getInstance() {
        assertThat(Pkcs11ReturnValue.CKR_OK,
                is(IPkcs11ReturnValue.getInstance(Pkcs11ReturnValue.CKR_OK.getAsLong())));
    }

    /**
     * Any value even unexpected (unknown) by wrapper, should be handled without exception
     */
    @Test
    public void getInstanceUnknownValue() {
        final var value = Pkcs11ReturnValue.CKR_VENDOR_DEFINED.getAsLong() + 1;
        assertThat(IPkcs11ReturnValue.getInstance(value).getAsLong(), is(value));
    }
}