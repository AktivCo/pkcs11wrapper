/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.constant;

import org.junit.Test;

import ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IPkcs11MechanismTypeTest {
    @Test
    public void getInstance() {
        assertThat(Pkcs11MechanismType.CKM_RSA_PKCS_KEY_PAIR_GEN,
                is(IPkcs11MechanismType.getInstance(Pkcs11MechanismType.CKM_RSA_PKCS_KEY_PAIR_GEN.getAsLong())));
    }

    /**
     * Any value even unexpected (unknown) by wrapper, should be handled without exception
     */
    @Test
    public void getInstanceUnknownValue() {
        final var value = Pkcs11MechanismType.CKM_VENDOR_DEFINED.getAsLong() + 1;
        assertThat(IPkcs11MechanismType.getInstance(value).getAsLong(), is(value));
    }
}