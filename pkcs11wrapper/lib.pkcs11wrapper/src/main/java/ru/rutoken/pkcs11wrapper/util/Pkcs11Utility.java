/*
 * Copyright (c) 2018, Aktiv-Soft JSC.
 * See the LICENSE file at the top-level directory of this distribution.
 * All Rights Reserved.
 */

package ru.rutoken.pkcs11wrapper.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import ru.rutoken.pkcs11wrapper.lowlevel.datatype.CkDate;

public class Pkcs11Utility {
    public static Date parseTime(byte[] time) {
        final int reserved = 2;
        if (time.length > reserved) {
            final String timeString = new String(time, 0, time.length - reserved);
            final SimpleDateFormat utc = new SimpleDateFormat("yyyyMMddhhmmss", Locale.US);
            utc.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                return utc.parse(timeString);
            } catch (ParseException e) {
                throw new RuntimeException("time parse error", e);
            }
        }
        throw new RuntimeException("time parse error");
    }

    public static LocalDate parseDate(byte[] year, byte[] month, byte[] day) {
        try {
            return LocalDate.of(Integer.parseInt(parseDateCharacters(year)),
                    Short.parseShort(parseDateCharacters(month)),
                    Short.parseShort(parseDateCharacters(day)));
        } catch (Exception e) {
            throw new RuntimeException("data parse error", e);
        }
    }

    public static void assignCkDate(CkDate ckDate, LocalDate date) {
        ckDate.setYear(String.format(Locale.US, "%04d", date.getYear()).getBytes());
        ckDate.setMonth(String.format(Locale.US, "%02d", date.getMonthValue()).getBytes());
        ckDate.setDay(String.format(Locale.US, "%02d", date.getDayOfMonth()).getBytes());
    }

    private static String parseDateCharacters(byte[] characters) {
        final StringBuilder builder = new StringBuilder();
        for (byte ch : characters) {
            builder.append((char) ch);
        }
        return builder.toString();
    }
}
