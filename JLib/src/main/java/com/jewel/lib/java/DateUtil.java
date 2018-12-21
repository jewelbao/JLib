package com.jewel.lib.java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/**
 * 日期工具
 */
public class DateUtil {

    /**
     * @param data       Date type time
     * @param formatType The format is yyyy-MM-dd HH:mm:ss//yyyy MM month dd day HH hour mm ss seconds
     */
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    /**
     *
     * @param currentTime The time of the long type to be converted
     * @param formatType The format is yyyy-MM-dd HH:mm:ss//yyyy MM month dd day HH hour mm ss seconds
     */
    public static String longToString(long currentTime, String formatType) {
        Date date = longToDate(currentTime, formatType); // Long type converted to Date type
        return dateToString(date, formatType);
    }

    /**
     *
     * @param strTime The time of the string type to be converted, the time format of strTime must be the same as the time format of formatType
     * @param formatType The format to be converted yyyy-MM-dd HH:mm:ss//yyyy year MM month dd day HH hour mm minute ss seconds
     */
    public static Date stringToDate(String strTime, String formatType)  {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     *
     * @param currentTime The time of the long type to be converted
     * @param formatType Time format to be converted yyyy-MM-dd HH:mm:ss//yyyy year MM month dd day HH hour mm minute ss seconds
     */
    public static Date longToDate(long currentTime, String formatType)  {
        Date dateOld = new Date(currentTime); // The time of a date type according to the number of milliseconds of the long type
        String sDateTime = dateToString(dateOld, formatType); // Convert date type to string
        return stringToDate(sDateTime, formatType);
    }

    /**
     *
     * @param strTime The time of the String type to be converted
     * @param formatType Time format to be converted yyyy-MM-dd HH:mm:ss//yyyy year MM month dd day HH hour mm minute ss seconds
     */
    public static long stringToLong(String strTime, String formatType) {
        Date date = stringToDate(strTime, formatType); // String type into date type
        if (date == null) {
            return 0;
        } else {
            return dateToLong(date);
        }
    }

    /**
     * @param date The time of the Date type to be converted
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * @param timeMs Time to convert to %d:%02d:%02d or %02d:%02d String type
     */
    public static String timeToString(long timeMs) {
        if (timeMs <= 0 || timeMs >= 24 * 60 * 60 * 1000) {
            return "00:00";
        }
        long totalSeconds = timeMs / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        StringBuilder stringBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(stringBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }
}
