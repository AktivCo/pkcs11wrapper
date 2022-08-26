package ru.rutoken.pkcs11wrapper.constant;

import org.junit.Test;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IPkcs11AttributeTypeTest {
    @Test
    public void getInstance() {
        assertThat(Pkcs11AttributeType.CKA_CLASS,
                is(IPkcs11AttributeType.getInstance(Pkcs11AttributeType.CKA_CLASS.getAsLong())));
    }

    /**
     * Any value even unexpected (unknown) by wrapper, should be handled without exception
     */
    @Test
    public void getInstanceUnknownValue() {
        final var value = Pkcs11AttributeType.CKA_VENDOR_DEFINED.getAsLong() + 1;
        assertThat(IPkcs11AttributeType.getInstance(value).getAsLong(), is(value));
    }
}