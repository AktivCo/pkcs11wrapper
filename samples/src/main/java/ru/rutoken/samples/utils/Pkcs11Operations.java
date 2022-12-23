package ru.rutoken.samples.utils;

import org.bouncycastle.cert.X509CertificateHolder;
import org.jetbrains.annotations.Nullable;

import ru.rutoken.pkcs11wrapper.attribute.Pkcs11Attribute;
import ru.rutoken.pkcs11wrapper.attribute.Pkcs11ByteArrayAttribute;
import ru.rutoken.pkcs11wrapper.datatype.Pkcs11InitializeArgs;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11PublicKeyObject;
import ru.rutoken.pkcs11wrapper.object.key.Pkcs11RsaPublicKeyObject;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Session;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Token;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.rutoken.pkcs11wrapper.constant.standard.Pkcs11AttributeType.*;
import static ru.rutoken.samples.utils.PkiUtils.generateX509Certificate;
import static ru.rutoken.samples.utils.Utils.dropPrecedingZeros;

public final class Pkcs11Operations {
    private Pkcs11Operations() {
    }

    public static RtPkcs11Token initializePkcs11AndGetFirstToken(RtPkcs11Module module) {
        module.initializeModule(new Pkcs11InitializeArgs.Builder().setOsLockingOk(true).build());

        final var slots = module.getSlotList(true);
        if (slots.size() == 0)
            throw new IllegalStateException("Rutoken is not found");

        return (RtPkcs11Token) slots.get(0).getToken();
    }

    @Nullable
    public static Pkcs11PublicKeyObject findPublicKeyByCertificate(RtPkcs11Session session,
                                                                   X509CertificateHolder certificateHolder)
            throws CertificateException, IOException {
        final var certificate = generateX509Certificate(certificateHolder);
        return certificate.getPublicKey() instanceof RSAPublicKey ? findRsaPublicKeyByCertificate(session, certificate)
                : findGostPublicKeyByCertificate(session, certificateHolder);
    }

    @Nullable
    private static Pkcs11RsaPublicKeyObject findRsaPublicKeyByCertificate(RtPkcs11Session session,
                                                                          X509Certificate certificate) {
        final var objects = session.getObjectManager()
                .findObjectsAtOnce(Pkcs11RsaPublicKeyObject.class, createRsaPublicKeyTemplate(certificate));
        return (objects.size() == 1) ? objects.get(0) : null;
    }

    @Nullable
    private static Pkcs11PublicKeyObject findGostPublicKeyByCertificate(RtPkcs11Session session,
                                                                        X509CertificateHolder certificateHolder)
            throws IOException {
        final var template = new ArrayList<Pkcs11Attribute>();
        template.add(new Pkcs11ByteArrayAttribute(CKA_VALUE, getGostPublicKeyValue(certificateHolder)));

        final var objects = session.getObjectManager().findObjectsAtOnce(Pkcs11PublicKeyObject.class, template);
        return (objects.size() == 1) ? objects.get(0) : null;
    }

    private static byte[] getGostPublicKeyValue(X509CertificateHolder certificateHolder) throws IOException {
        final var keyValue = certificateHolder.getSubjectPublicKeyInfo().parsePublicKey().getEncoded();

        // Remove the key header (see ASN.1 Basic Encoding Rules)
        var pos = 2;
        if ((keyValue[1] & (1 << 7)) != 0) {
            pos += keyValue[1] & (0xFF >> 1);
        }

        return Arrays.copyOfRange(keyValue, pos, keyValue.length);
    }

    private static List<Pkcs11Attribute> createRsaPublicKeyTemplate(X509Certificate certificate) {
        final var publicKey = certificate.getPublicKey();
        if (!(publicKey instanceof RSAPublicKey))
            throw new IllegalStateException("Public key extracted from certificate is not an RSA key");

        final var rsaPublicKey = (RSAPublicKey) publicKey;
        final var modulus = rsaPublicKey.getModulus();
        final var publicExp = rsaPublicKey.getPublicExponent();

        if (modulus.signum() < 0 && publicExp.signum() < 0)
            throw new IllegalStateException("Modulus or public exponent is less than zero");

        final var result = new ArrayList<Pkcs11Attribute>();
        result.add(new Pkcs11ByteArrayAttribute(CKA_MODULUS, dropPrecedingZeros(modulus.toByteArray())));
        result.add(new Pkcs11ByteArrayAttribute(CKA_PUBLIC_EXPONENT, publicExp.toByteArray()));

        return result;
    }
}
