package ru.rutoken.samples.utils;

import java.util.Arrays;
import java.util.List;

public final class Utils {
    private Utils() {
    }

    public static void println(String text) {
        System.out.println(text);
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
            System.out.printf(" %02X", data[i]);
            if ((i + 1) % 16 == 0)
                System.out.println();
        }
        System.out.println();
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
}
