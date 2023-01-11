package ru.rutoken.samples.utils;

import ru.rutoken.pkcs11wrapper.constant.IPkcs11MechanismType;
import ru.rutoken.pkcs11wrapper.rutoken.main.RtPkcs11Token;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import static ru.rutoken.samples.utils.Pkcs11Operations.*;

public final class Utils {
    private Utils() {
    }

    public static void println(String text) {
        System.out.println(text);
    }

    public static void println() {
        System.out.println();
    }

    public static void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    public static void printlnf(String format, Object... args) {
        printf(format, args);
        println();
    }

    public static <T> void printSuccessfulExit(Class<T> clazz) {
        println(clazz.getSimpleName() + " has been completed successfully");
    }

    public static <T> void printError(Class<T> clazz, Exception e) {
        System.err.println(clazz.getSimpleName() + " has failed:");
        e.printStackTrace();
    }

    public static void printHex(String label, byte[] data) {
        println(label);
        for (var i = 0; i < data.length; ++i) {
            printf(" %02X", data[i]);
            if ((i + 1) % 16 == 0)
                println();
        }
        println();
    }

    public static void printCsr(byte[] csrDer) {
        println("CSR:");

        final var lineWidth = 64;
        // Encode from der to base64
        final var csrBase64 = Base64.getEncoder().encodeToString(csrDer);

        var k = 0;
        while (k < csrBase64.length() / lineWidth) {
            println(csrBase64.substring(k * lineWidth, (k + 1) * lineWidth));
            k++;
        }

        println(csrBase64.substring(k * lineWidth));
    }

    public static void printString(String label, String data) {
        println(label);
        println(data);
    }

    public static String readCertificate() {
        final var regexHeader = ".*-----BEGIN[^-]*(-[^-]+)*-----";
        final var regexFooter = "-----END[^-]*(-[^-]+)*-----.*";
        final var in = new Scanner(System.in);
        var currentLine = in.nextLine();
        final var certificate = new StringBuilder();

        do {
            certificate.append(currentLine);
            currentLine = in.nextLine();
        } while (!currentLine.isEmpty());

        return certificate.toString().replaceFirst(regexHeader, "").replaceFirst(regexFooter, "");
    }

    public static <T> T TODO(String reason) {
        throw new UnsupportedOperationException(reason);
    }

    public static boolean contains(List<byte[]> arrays, byte[] element) {
        for (final var array : arrays) {
            if (Arrays.equals(array, element))
                return true;
        }
        return false;
    }

    public static byte[] dropPrecedingZeros(byte[] array) {
        if (array.length == 0)
            return array;

        final var numPrecedingZeros = IntStream.range(0, array.length)
                .filter(index -> array[index] != 0)
                .findFirst().orElse(-1);

        return Arrays.copyOfRange(array, numPrecedingZeros, array.length);
    }

    public static <T> boolean hasUnsupportedMechanisms(Class<T> clazz, RtPkcs11Token token,
                                                       IPkcs11MechanismType... mechanisms) {
        final var unsupportedMechanisms = getUnsupportedMechanisms(token, mechanisms);
        if (unsupportedMechanisms.isEmpty())
            return false;

        printf("%s", clazz.getSimpleName() + " cannot be run as ");
        for (var i = 0; i < unsupportedMechanisms.size() - 1; ++i) {
            printf("%s", unsupportedMechanisms.get(i) + " , ");
        }
        printlnf("%s", unsupportedMechanisms.get(unsupportedMechanisms.size() - 1) + " not supported by token");

        return true;
    }

    public static <T> boolean isRsaModulusUnsupported(Class<T> clazz, RtPkcs11Token token, int modulusBits) {
        if (!Pkcs11Operations.isRsaModulusSupported(token, modulusBits)) {
            println(clazz.getSimpleName() + " cannot be run as RSA modulus " + modulusBits +
                    " is not supported by the token");
            return true;
        }
        return false;
    }

    public static void printSampleDelimiter() {
        println("--------------------------------------------------------");
        println("--------------------------------------------------------");
    }
}
