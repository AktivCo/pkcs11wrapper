package ru.rutoken.samples.utils;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public final class Utils {
    private Utils() {
    }

    public static void println(String text) {
        System.out.println(text);
    }

    public static <T> void printSuccessfulExit(Class<T> clazz) {
        println(clazz.getSimpleName() + " has been completed successfully");
        println("--------------------------------------------------------");
        println("--------------------------------------------------------");
    }

    public static <T> void printError(Class<T> clazz, Exception e) {
        System.err.println(clazz.getSimpleName() + " has failed:");
        e.printStackTrace();
    }

    public static void printHex(String label, byte[] data) {
        println(label);
        for (var i = 0; i < data.length; ++i) {
            System.out.printf(" %02X", data[i]);
            if ((i + 1) % 16 == 0)
                System.out.println();
        }
        System.out.println();
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
}
