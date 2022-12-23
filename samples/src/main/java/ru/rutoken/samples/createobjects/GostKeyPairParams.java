package ru.rutoken.samples.createobjects;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11KeyType;
import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType.CKK_GOSTR3410;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11MechanismType.CKM_GOSTR3410_KEY_PAIR_GEN;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyType.CKK_GOSTR3410_512;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11MechanismType.CKM_GOSTR3410_512_KEY_PAIR_GEN;
import static ru.rutoken.samples.utils.Constants.*;

public enum GostKeyPairParams {
    GOST_2012_256(
            CKM_GOSTR3410_KEY_PAIR_GEN,
            CKK_GOSTR3410,
            CRYPTO_PRO_A_GOSTR3410_2012_256_OID,
            GOSTR3411_2012_256_OID,
            GOST_2012_256_PUBLIC_KEY_LABEL,
            GOST_2012_256_PRIVATE_KEY_LABEL,
            GOST_2012_256_KEY_PAIR_ID
    ),
    GOST_2012_512(
            CKM_GOSTR3410_512_KEY_PAIR_GEN,
            CKK_GOSTR3410_512,
            CRYPTO_PRO_A_GOSTR3410_2012_512_OID,
            GOSTR3411_2012_512_OID,
            GOST_2012_512_PUBLIC_KEY_LABEL,
            GOST_2012_512_PRIVATE_KEY_LABEL,
            GOST_2012_512_KEY_PAIR_ID
    );

    private final IPkcs11MechanismType mMechanismType;
    private final IPkcs11KeyType mKeyType;
    private final byte[] mParamset3410;
    private final byte[] mParamset3411;
    private final String mPublicKeyLabel;
    private final String mPrivateKeyLabel;
    private final byte[] mId;

    GostKeyPairParams(IPkcs11MechanismType mechanismType, IPkcs11KeyType keyType, byte[] paramset3410,
                      byte[] paramset3411, String publicKeyLabel, String privateKeyLabel, byte[] id) {
        mMechanismType = mechanismType;
        mKeyType = keyType;
        mParamset3410 = paramset3410;
        mParamset3411 = paramset3411;
        mPublicKeyLabel = publicKeyLabel;
        mPrivateKeyLabel = privateKeyLabel;
        mId = id;
    }

    public IPkcs11MechanismType getMechanismType() {
        return mMechanismType;
    }

    public IPkcs11KeyType getKeyType() {
        return mKeyType;
    }

    public byte[] getParamset3410() {
        return mParamset3410;
    }

    public byte[] getParamset3411() {
        return mParamset3411;
    }

    public String getPublicKeyLabel() {
        return mPublicKeyLabel;
    }

    public String getPrivateKeyLabel() {
        return mPrivateKeyLabel;
    }

    public byte[] getId() {
        return mId;
    }
}
