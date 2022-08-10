package ru.rutoken.pkcs11wrapper.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static Date parseDate(byte[] year, byte[] month, byte[] day) {
        try {
            // TODO use LocalDate when go to api 26
            final Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(parseDateCharacters(year)),
                    Short.parseShort(parseDateCharacters(month)),
                    Short.parseShort(parseDateCharacters(day)));
            return calendar.getTime();
        } catch (Exception e) {
            throw new RuntimeException("data parse error", e);
        }
    }

    public static void assignCkDate(CkDate ckDate, Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        ckDate.setYear(String.format(Locale.US, "%tY", calendar).getBytes());
        ckDate.setMonth(String.format(Locale.US, "%tm", calendar).getBytes());
        ckDate.setDay(String.format(Locale.US, "%td", calendar).getBytes());
    }

    private static String parseDateCharacters(byte[] characters) {
        final StringBuilder builder = new StringBuilder();
        for (byte ch : characters) {
            builder.append((char) ch);
        }
        return builder.toString();
    }
}
