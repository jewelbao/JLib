package com.jewel.lib.java;

import android.content.Context;
import android.support.annotation.StringRes;

import com.jewel.lib.android.CompatUtil;

/**
 * 字符串工具
 */
public final class StringFormat {

    private StringFormat() {
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
}
