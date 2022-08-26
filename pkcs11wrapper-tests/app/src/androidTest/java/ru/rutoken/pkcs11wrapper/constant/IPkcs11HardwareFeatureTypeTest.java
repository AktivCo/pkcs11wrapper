package ru.rutoken.pkcs11wrapper.constant;

import org.junit.Test;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11HardwareFeatureType;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IPkcs11HardwareFeatureTypeTest {
    @Test
    public void getInstance() {
        assertThat(Pkcs11HardwareFeatureType.CKH_MONOTONIC_COUNTER,
                is(IPkcs11HardwareFeatureType
                        .getInstance(Pkcs11HardwareFeatureType.CKH_MONOTONIC_COUNTER.getAsLong())));
    }

    /**
     * Any value even unexpected (unknown) by wrapper, should be handled without exception
     */
    @Test
    public void getInstanceUnknownValue() {
        final var value = Pkcs11HardwareFeatureType.CKH_VENDOR_DEFINED.getAsLong() + 1;
        assertThat(IPkcs11HardwareFeatureType.getInstance(value).getAsLong(), is(value));
    }
}