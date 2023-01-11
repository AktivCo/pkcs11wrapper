package ru.rutoken.samples.utils;

import java.util.Arrays;
import java.util.List;

public final class Constants {
    public static final String DEFAULT_USER_PIN = "12345678";
    public static final String DEFAULT_ADMIN_PIN = "87654321";
    public static final String TOKEN_LABEL = "Rutoken";

    public static final byte[] CRYPTO_PRO_A_GOSTR3410_2012_256_OID =
            {0x06, 0x07, 0x2A, (byte) 0x85, 0x03, 0x02, 0x02, 0x23, 0x01};
    public static final byte[] CRYPTO_PRO_A_GOSTR3410_2012_512_OID =
            {0x06, 0x09, 0x2A, (byte) 0x85, 0x03, 0x07, 0x01, 0x02, 0x01, 0x02, 0x01};

    public static final byte[] GOSTR3411_1994_OID = {0x06, 0x07, 0x2a, (byte) 0x85, 0x03, 0x02, 0x02, 0x1e, 0x01};
    public static final byte[] GOSTR3411_2012_256_OID =
            {0x06, 0x08, 0x2a, (byte) 0x85, 0x03, 0x07, 0x01, 0x01, 0x02, 0x02};
    public static final byte[] GOSTR3411_2012_512_OID =
            {0x06, 0x08, 0x2a, (byte) 0x85, 0x03, 0x07, 0x01, 0x01, 0x02, 0x03};

    public static final String GOST_2012_256_PUBLIC_KEY_LABEL = "Sample GOST R 34.10-2012 (256 bits) Public Key";
    public static final String GOST_2012_256_PRIVATE_KEY_LABEL = "Sample GOST R 34.10-2012 (256 bits) Private Key";
    public static final String GOST_2012_512_PUBLIC_KEY_LABEL = "Sample GOST R 34.10-2012 (512 bits) Public Key";
    public static final String GOST_2012_512_PRIVATE_KEY_LABEL = "Sample GOST R 34.10-2012 (512 bits) Private Key";
    public static final String RSA_PUBLIC_KEY_LABEL = "Sample RSA Public Key";
    public static final String RSA_PRIVATE_KEY_LABEL = "Sample RSA Private Key";
    public static final String ECDSA_PUBLIC_KEY_LABEL = "Sample ECDSA Public Key";
    public static final String ECDSA_PRIVATE_KEY_LABEL = "Sample ECDSA Private Key";
    public static final byte[] GOST_2012_256_KEY_PAIR_ID = "Sample GOST R 34.10-2012 (256 bits) key pair".getBytes();
    public static final byte[] GOST_2012_512_KEY_PAIR_ID = "Sample GOST R 34.10-2012 (512 bits) key pair".getBytes();
    public static final byte[] RSA_KEY_PAIR_ID = "Sample RSA key pair".getBytes();
    public static final byte[] ECDSA_KEY_PAIR_ID = "Sample ECDSA key pair".getBytes();

    public static final List<String> DN = Arrays.asList(
            "CN",
            "Ivanoff",
            "C",
            "RU",
            "2.5.4.5",
            "12312312312",
            "1.2.840.113549.1.9.1",
            "ivanov@mail.ru",
            "ST",
            "Moscow"
    );

    public static final List<String> GOST_EXTENSIONS = Arrays.asList(
            "keyUsage",
            "digitalSignature,nonRepudiation,keyAgreement",
            "extendedKeyUsage",
            "1.3.6.1.5.5.7.3.2,1.3.6.1.5.5.7.3.4"
    );
    public static final List<String> RSA_EXTENSIONS = Arrays.asList(
            "keyUsage",
            "digitalSignature,nonRepudiation,keyEncipherment,dataEncipherment",
            "extendedKeyUsage",
            "1.3.6.1.5.5.7.3.2,1.3.6.1.5.5.7.3.4"
    );

    private Constants() {
    }
}
