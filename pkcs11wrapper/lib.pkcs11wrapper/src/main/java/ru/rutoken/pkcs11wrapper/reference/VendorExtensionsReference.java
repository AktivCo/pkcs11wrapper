package ru.rutoken.pkcs11wrapper.reference;

import ru.rutoken.pkcs11wrapper.lowlevel.main.IPkcs11VendorExtensions;

public interface VendorExtensionsReference {
    IPkcs11VendorExtensions getVendorExtensions();
}
