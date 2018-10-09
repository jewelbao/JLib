package com.jewel.lib.java;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.StringRes;

import com.jewel.lib.android.CompatUtil;

/**
 * 字符串工具
 */
public final class StringUtil {

    private StringUtil() {
        throw new ExceptionInInitializerError();
    }

    /**
     * 从字符串资源中获取字符串
     */
    public static String get(Context context, @StringRes int stringRes) {
        return CompatUtil.getString(context, stringRes);
    }

    /**
     * 从字符串资源中获取特殊字符串并格式化
     */
    public static String get(Context context, @StringRes int stringRes, Object... args) {
        return String.format(CompatUtil.getString(context, stringRes), args);
    }

    /**
     * 获取格式化后的字符串。当source未包含"%s"时，将在source末尾添加"%s"
     * @param source 源字符串
     * @param arg 格式化参数
     * @return 格式化后的字符串
     */
    public static String get(String source, Object arg) {
        if(arg != null) {
            if(!source.contains("%s")) {
                source = source + "%s";
            }
        }
        return String.format(source, arg);
    }

    /**
     * 获取格式化后的字符串。当source未包含"%s"时，将根据args添加同等数的"%s"
     * @param source 源字符串
     * @param args 格式化参数
     * @return 格式化后的字符串
     */
    public static String get(String source, Object... args) {
        if(args != null && args.length > 0) {
            if(!source.contains("%s")) {
                source = source + getFormatBy(args.length);
            }
        }
        return String.format(source, args);
    }

    /**
     * 根据count得到相同数的“%s”字符串<br>
     *     <code>
     *         getFormatBy(1) == "%s"<br>
     *         getFormatBy(3) ==  "%s%s%s"
     *     </code>
     * @param count “%s”个数
     * @return “%s”* count
     */
    public static String getFormatBy(@IntRange(from = 1) int count) {
        StringBuilder format = new StringBuilder(1);
        for (int i = 1; i <= count; i++) {
            format.append("%s");
        }
        return format.toString();
    }
}
