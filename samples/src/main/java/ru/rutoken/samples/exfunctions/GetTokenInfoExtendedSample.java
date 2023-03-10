/*
 * Copyright (c) 2022, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.samples.exfunctions;

import ru.rutoken.pkcs11wrapper.rutoken.datatype.TokenInfoExtended;
import ru.rutoken.samples.utils.RtPkcs11Module;

import static ru.rutoken.pkcs11jna.RtPkcs11Constants.*;
import static ru.rutoken.samples.utils.Pkcs11Operations.initializePkcs11AndGetFirstToken;
import static ru.rutoken.samples.utils.Utils.*;

public class GetTokenInfoExtendedSample {

    public static void runSample(RtPkcs11Module module) {
        printSampleLaunchMessage(GetTokenInfoExtendedSample.class);
        try {
            printInfo(initializePkcs11AndGetFirstToken(module).getTokenInfoExtended());
            printSuccessfulExit(GetTokenInfoExtendedSample.class);
        } catch (Exception e) {
            printError(GetTokenInfoExtendedSample.class, e);
        } finally {
            module.finalizeModule();
            printSampleDelimiter();
        }
    }

    public static void main(String[] args) {
        runSample(RtPkcs11Module.getInstance(args));
    }

    private static void printInfo(TokenInfoExtended tokenInfoExtended) {
        println("Extended token info:");
        printSerialNumber(tokenInfoExtended.getSerialNumber());
        printTokenClass(tokenInfoExtended.getTokenClass());
        printFlags(tokenInfoExtended.getFlags());
        printAtr(tokenInfoExtended.getAtr());
        printColor(tokenInfoExtended.getBodyColor());
        printHex("Protocol number", tokenInfoExtended.getProtocolNumber());
        printHex("Microcode number", tokenInfoExtended.getMicrocodeNumber());
        printLong("Min admin PIN length", tokenInfoExtended.getMinAdminPinLen());
        printLong("Max admin PIN length", tokenInfoExtended.getMaxAdminPinLen());
        printLong("Min user PIN length", tokenInfoExtended.getMinUserPinLen());
        printLong("Max user PIN length", tokenInfoExtended.getMaxUserPinLen());
        printLong("Max admin login attempts", tokenInfoExtended.getMaxAdminRetryCount());
        printLong("Admin login attempts left", tokenInfoExtended.getAdminRetryCountLeft());
        printLong("Max user login attempts", tokenInfoExtended.getMaxUserRetryCount());
        printLong("User login attempts left", tokenInfoExtended.getUserRetryCountLeft());
        printLong("Total memory (bytes)", tokenInfoExtended.getTotalMemory());
        printLong("Free memory (bytes)", tokenInfoExtended.getFreeMemory());
        printLong("Order number", tokenInfoExtended.getOrderNumber());
        printHex("Firmware checksum", tokenInfoExtended.getFirmwareChecksum());
        printLong("Battery voltage (micro V)", tokenInfoExtended.getBatteryVoltage());
        printLong("Battery percentage", tokenInfoExtended.getBatteryPercentage());
        printLong("Battery flags", tokenInfoExtended.getBatteryFlags());
    }

    private static void printTokenClass(TokenInfoExtended.TokenClass tokenClass) {
        var tokenClassName = "Unknown";
        switch (tokenClass) {
            case TOKEN_CLASS_ECP:
                tokenClassName = "Rutoken ECP";
                break;
            case TOKEN_CLASS_LITE:
                tokenClassName = "Rutoken Lite";
                break;
            case TOKEN_CLASS_ECP_BT:
                tokenClassName = "Rutoken ECP BT";
                break;
            case TOKEN_CLASS_S:
                tokenClassName = "Rutoken S";
        }
        printlnf("\t%-30s %s", "Token class", tokenClassName);
    }

    private static void printColor(TokenInfoExtended.TokenBodyColor color) {
        var colorText = "Unknown";
        switch (color) {
            case TOKEN_BODY_COLOR_WHITE:
                colorText = "White";
                break;
            case TOKEN_BODY_COLOR_BLACK:
                colorText = "Black";
        }
        printlnf("\t%-30s %s", "Body color", colorText);
    }

    /**
     * This method parses and prints some token flags referred to pin.
     * To check the rest of the flags see {@link ru.rutoken.pkcs11jna.RtPkcs11Constants}
     *
     * @param flags token flags
     */
    private static void printFlags(long flags) {
        printlnf("\t%-30s", "Flags");
        printlnf("\t\t%-30s %b", "Admin can change PIN",
                (flags & TOKEN_FLAGS_ADMIN_CHANGE_USER_PIN) == TOKEN_FLAGS_ADMIN_CHANGE_USER_PIN);
        printlnf("\t\t%-30s %b", "User can change PIN",
                (flags & TOKEN_FLAGS_USER_CHANGE_USER_PIN) == TOKEN_FLAGS_USER_CHANGE_USER_PIN);
        printlnf("\t\t%-30s %b", "Admin PIN not default",
                (flags & TOKEN_FLAGS_ADMIN_PIN_NOT_DEFAULT) == TOKEN_FLAGS_ADMIN_PIN_NOT_DEFAULT);
        printlnf("\t\t%-30s %b", "User PIN not default",
                (flags & TOKEN_FLAGS_USER_PIN_NOT_DEFAULT) == TOKEN_FLAGS_USER_PIN_NOT_DEFAULT);
    }

    private static void printAtr(byte[] atr) {
        printf("\t%-30s ", "ATR");
        for (var b : atr) {
            printf("%02X", b);
        }
        println();
    }

    private static void printSerialNumber(byte[] serialNumber) {
        printf("\t%-30s 0x", "Token serial number");
        for (var i = 4; i < serialNumber.length; ++i)
            printf("%02X", serialNumber[i]);
        println();
    }

    private static void printLong(String name, Long value) {
        printlnf("\t%-30s %d", name, value);
    }

    private static void printHex(String name, Long value) {
        printlnf("\t%-30s 0x%08X", name, value);
    }
}
