package com.jewel.lib.java;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.StringRes;

import com.jewel.lib.android.CompatUtil;

public final class StringUtil {

    private StringUtil() {
        throw new ExceptionInInitializerError();
    }

    /**
     * Get a string from a string resource
     */
    public static String get(Context context, @StringRes int stringRes) {
        return CompatUtil.getString(context, stringRes);
    }

    /**
     * Get a special string from a string resource and format it
     */
    public static String get(Context context, @StringRes int stringRes, Object... args) {
        return String.format(CompatUtil.getString(context, stringRes), args);
    }

    /**
     * Get the formatted string. When the source does not contain "%s", "%s" will be added at the end of the source
     *
     * @param source Source string
     * @param arg    Formatting parameter
     * @return Formatted string
     */
    public static String get(String source, Object arg) {
        if (arg != null) {
            if (!source.contains("%s")) {
                source = source + "%s";
            }
        }
        return String.format(source, arg);
    }

    /**
     * Get the formatted string. When source does not contain "%s", an equal number of "%s" will be added according to args
     *
     * @param source source string
     * @param args   formatting parameters
     * @return formatted string
     */
    public static String get(String source, Object... args) {
        if (args != null && args.length > 0) {
            if (!source.contains("%s")) {
                source = source + getFormatBy(args.length);
            }
        }
        return String.format(source, args);
    }

    /**
     * Get the same number of "%s" strings according to count<br>
     * getFormatBy(1) == "%s"<br>
     * getFormatBy(3) == "%s%s%s"
     *
     * @param count "%s" number
     * @return  "%s" * count
     */
    public static String getFormatBy(@IntRange(from = 1) int count) {
        StringBuilder format = new StringBuilder(1);
        for (int i = 1; i <= count; i++) {
            format.append("%s");
        }
        return format.toString();
    }
}
