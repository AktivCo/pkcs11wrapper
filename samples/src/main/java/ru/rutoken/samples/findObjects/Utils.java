package ru.rutoken.samples.findObjects;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509CertificateHolder;

import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PublicKeyObject;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Session;
import ru.rutoken.samples.findObjects.Algorithm.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_GOSTR3411_PARAMS;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.CKA_MODULUS_BITS;
import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11KeyType.*;
import static ru.rutoken.pkcs11wrapper.rutoken.constant.RtPkcs11KeyType.CKK_GOSTR3410_512;
import static ru.rutoken.samples.utils.Constants.GOSTR3411_1994_OID;
import static ru.rutoken.samples.utils.Constants.GOSTR3411_2012_256_OID;
import static ru.rutoken.samples.utils.Utils.printHex;
import static ru.rutoken.samples.utils.Utils.println;

final class Utils {
    private Utils() {
    }

    @Nullable
    static Algorithm getAlgorithm(Pkcs11PublicKeyObject publicKey, RtPkcs11Session session) {
        final var keyType = publicKey.getKeyTypeAttributeValue(session).getEnumValue(session);

        if (keyType == CKK_GOSTR3410) {
            final var params = publicKey.getByteArrayAttributeValue(session, CKA_GOSTR3411_PARAMS).getByteArrayValue();
            return getGost256Algorithm(params);
        } else if (keyType == CKK_EC) {
            return new EcdsaAlgorithm();
        } else if (keyType == CKK_GOSTR3410_512) {
            return new GostAlgorithm2012_512();
        } else if (keyType == CKK_RSA) {
            final var modulusBits = publicKey.getLongAttributeValue(session, CKA_MODULUS_BITS).getLongValue();
            return new RsaAlgorithm(modulusBits);
        }

        return null;
    }

    static void printInfo(String type, byte[] id, Algorithm algorithm, X509CertificateHolder certificate) {
        println("Container type: " + type);
        if (id != null) printHex("Identifier:", id);
        if (algorithm != null) println("Algorithm: " + algorithm);
        if (certificate != null) {
            println("Owner: " + getOwner(certificate));
            println("Organization: " + getSubjectRdnValue(certificate, BCStyle.O));
            println("Expires: " + toTimeString(certificate.getNotAfter()));
        }
    }

    @Nullable
    private static Algorithm getGost256Algorithm(byte[] gostR3411ParamsValue) {
        if (Arrays.equals(gostR3411ParamsValue, GOSTR3411_1994_OID))
            return new GostAlgorithm2001();
        else if (Arrays.equals(gostR3411ParamsValue, GOSTR3411_2012_256_OID))
            return new GostAlgorithm2012_256();

        return null;
    }

    private static String getOwner(X509CertificateHolder certificateHolder) {
        final var cn = getSubjectRdnValue(certificateHolder, BCStyle.CN);
        final var surname = getSubjectRdnValue(certificateHolder, BCStyle.SURNAME);
        final var givenName = getSubjectRdnValue(certificateHolder, BCStyle.GIVENNAME);

        final var hasFullName = surname != null && givenName != null;

        if (!hasFullName && cn == null)
            throw new IllegalStateException("Suitable RDNs are not found");

        return hasFullName ? surname + " " + givenName : cn;
    }

    @Nullable
    private static String getSubjectRdnValue(X509CertificateHolder certificateHolder, ASN1ObjectIdentifier type) {
        final var rdnOptional = Arrays.stream(certificateHolder.getSubject().getRDNs())
                .filter(it -> it.getFirst().getType().equals(type))
                .findFirst();

        return rdnOptional.map(rdn -> rdn.getFirst().getValue().toString()).orElse(null);
    }

    private static String toTimeString(Date date) {
        return new SimpleDateFormat("d MMMM yyyy, HH:mm:ss", Locale.getDefault()).format(date);
    }
}
