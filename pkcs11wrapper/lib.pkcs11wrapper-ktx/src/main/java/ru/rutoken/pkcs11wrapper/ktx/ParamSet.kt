@file:Suppress("EnumEntryName")

package ru.rutoken.pkcs11wrapper.ktx

private val Int.b get() = toByte()

/**
 * @see <a href="https://cpdn.cryptopro.ru/content/csp_trunk/html/group___pro_c_s_p_ex_CP_PARAM_OIDS.html">CryptoPro OIDs</a>
 */
interface ParamSet {
    /**
     * DER encoded format
     */
    val encoded: ByteArray
    val oid: String
    val idName: String
}

/** Marker interface */
interface Gost28147ParamSet : ParamSet

/**
 * RFC7836
 */
enum class Tc26Gost28147ParamSet : Gost28147ParamSet {
    /** Recommended */
    Z {
        override val encoded = byteArrayOf(0x06, 0x09, 0x2A, 0x85.b, 0x03, 0x07, 0x01, 0x02, 0x05, 0x01, 0x01)
        override val oid = "1.2.643.7.1.2.5.1.1"
        override val idName = "id-tc26-gost-28147-param-Z"
    }
}

/**
 * RFC4357
 */
enum class CryptoProGost28147ParamSet : Gost28147ParamSet {
    /** Default */
    A {
        override val encoded = byteArrayOf(0x06, 0x07, 0x2A, 0x85.b, 0x03, 0x02, 0x02, 0x1F, 0x01)
        override val oid = "1.2.643.2.2.31.1"
        override val idName = "id-Gost28147-89-CryptoPro-A-ParamSet"
    },
    B {
        override val encoded = byteArrayOf(0x06, 0x07, 0x2A, 0x85.b, 0x03, 0x02, 0x02, 0x1F, 0x02)
        override val oid = "1.2.643.2.2.31.2"
        override val idName = "id-Gost28147-89-CryptoPro-B-ParamSet"
    },
    C {
        override val encoded = byteArrayOf(0x06, 0x07, 0x2A, 0x85.b, 0x03, 0x02, 0x02, 0x1F, 0x03)
        override val oid = "1.2.643.2.2.31.3"
        override val idName = "id-Gost28147-89-CryptoPro-C-ParamSet"
    },
    D {
        override val encoded = byteArrayOf(0x06, 0x07, 0x2A, 0x85.b, 0x03, 0x02, 0x02, 0x1F, 0x04)
        override val oid = "1.2.643.2.2.31.4"
        override val idName = "id-Gost28147-89-CryptoPro-D-ParamSet"
    }
}

enum class DigestParamSet : ParamSet {
    Gost3411_94 {
        override val encoded = byteArrayOf(0x06, 0x07, 0x2A, 0x85.b, 0x03, 0x02, 0x02, 0x1E, 0x01)
        override val oid = "1.2.643.2.2.30.1"
        override val idName = "id-GostR3411-94-CryptoProParamSet"
    },
    Gost3411_2012_256 {
        override val encoded = byteArrayOf(0x06, 0x08, 0x2A, 0x85.b, 0x03, 0x07, 0x01, 0x01, 0x02, 0x02)
        override val oid = "1.2.643.7.1.1.2.2"
        override val idName = "id-tc26-gost3411-12-256"
    },
    Gost3411_2012_512 {
        override val encoded = byteArrayOf(0x06, 0x08, 0x2A, 0x85.b, 0x03, 0x07, 0x01, 0x01, 0x02, 0x03)
        override val oid = "1.2.643.7.1.1.2.3"
        override val idName = "id-tc26-gost3411-12-512"
    }
}

/**
 * RFC 4357
 */
enum class CryptoProGost2001ParamSet : ParamSet {
    /** Default */
    A {
        override val encoded = byteArrayOf(0x06, 0x07, 0x2A, 0x85.b, 0x03, 0x02, 0x02, 0x23, 0x01)
        override val oid = "1.2.643.2.2.35.1"
        override val idName = "id-GostR3410-2001-CryptoPro-A-ParamSet"
    },
    B {
        override val encoded = byteArrayOf(0x06, 0x07, 0x2A, 0x85.b, 0x03, 0x02, 0x02, 0x23, 0x02)
        override val oid = "1.2.643.2.2.35.2"
        override val idName = "id-GostR3410-2001-CryptoPro-B-ParamSet"
    },
    C {
        override val encoded = byteArrayOf(0x06, 0x07, 0x2A, 0x85.b, 0x03, 0x02, 0x02, 0x23, 0x03)
        override val oid = "1.2.643.2.2.35.3"
        override val idName = "id-GostR3410-2001-CryptoPro-C-ParamSet"
    },

    /** Exchange default */
    XchA {
        override val encoded = byteArrayOf(0x06, 0x07, 0x2A, 0x85.b, 0x03, 0x02, 0x02, 0x24, 0x00)
        override val oid = "1.2.643.2.2.36.0"
        override val idName = "id-GostR3410-2001-CryptoPro-XchA-ParamSet"
    },
    XchB {
        override val encoded = byteArrayOf(0x06, 0x07, 0x2A, 0x85.b, 0x03, 0x02, 0x02, 0x24, 0x01)
        override val oid = "1.2.643.2.2.36.1"
        override val idName = "id-GostR3410-2001-CryptoPro-XchB-ParamSet"
    }
}

/**
 * Sign & verify algorithms parameters
 */
enum class Tc26Gost2012ShortParamSet : ParamSet {
    A {
        override val encoded = byteArrayOf(0x06, 0x09, 0x2A, 0x85.b, 0x03, 0x07, 0x01, 0x02, 0x01, 0x01, 0x01)
        override val oid = "1.2.643.7.1.2.1.1.1"
        override val idName = "id-tc26-gost-3410-12-256-paramSetA"
    },
    B {
        override val encoded = byteArrayOf(0x06, 0x09, 0x2A, 0x85.b, 0x03, 0x07, 0x01, 0x02, 0x01, 0x01, 0x02)
        override val oid = "1.2.643.7.1.2.1.1.2"
        override val idName = "id-tc26-gost-3410-12-256-paramSetB"
    },
    C {
        override val encoded = byteArrayOf(0x06, 0x09, 0x2A, 0x85.b, 0x03, 0x07, 0x01, 0x02, 0x01, 0x01, 0x03)
        override val oid = "1.2.643.7.1.2.1.1.3"
        override val idName = "id-tc26-gost-3410-12-256-paramSetC"
    },
    D {
        override val encoded = byteArrayOf(0x06, 0x09, 0x2A, 0x85.b, 0x03, 0x07, 0x01, 0x02, 0x01, 0x01, 0x04)
        override val oid = "1.2.643.7.1.2.1.1.4"
        override val idName = "id-tc26-gost-3410-12-256-paramSetD"
    }
}

/**
 * Sign & verify algorithms parameters
 */
enum class Tc26Gost2012LongParamSet : ParamSet {
    A {
        override val encoded = byteArrayOf(0x06, 0x09, 0x2A, 0x85.b, 0x03, 0x07, 0x01, 0x02, 0x01, 0x02, 0x01)
        override val oid = "1.2.643.7.1.2.1.2.1"
        override val idName = "id-tc26-gost-3410-12-512-paramSetA"
    },
    B {
        override val encoded = byteArrayOf(0x06, 0x09, 0x2A, 0x85.b, 0x03, 0x07, 0x01, 0x02, 0x01, 0x02, 0x02)
        override val oid = "1.2.643.7.1.2.1.2.2"
        override val idName = "id-tc26-gost-3410-12-512-paramSetB"
    },
    C {
        override val encoded = byteArrayOf(0x06, 0x09, 0x2A, 0x85.b, 0x03, 0x07, 0x01, 0x02, 0x01, 0x02, 0x03)
        override val oid = "1.2.643.7.1.2.1.2.3"
        override val idName = "id-tc26-gost-3410-12-512-paramSetC"
    }
}

enum class EcdsaCurveParamSet : ParamSet {
    Secp256k1 {
        override val encoded = byteArrayOf(0x06, 0x05, 0x2B, 0x81.b, 0x04, 0x00, 0x0A)
        override val oid = "1.3.132.0.10"
        override val idName = "secp256k1"
    },
    Secp256r1 {
        override val encoded = byteArrayOf(0x06, 0x08, 0x2A, 0x86.b, 0x48, 0xCE.b, 0x3D, 0x03, 0x01, 0x07)
        override val oid = "1.2.840.10045.3.1.7"
        override val idName = "secp256r1"
    }
}
