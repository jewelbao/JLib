package com.jewel.lib;

import android.content.res.Resources;
import android.support.annotation.StringRes;

public final class StringFormat {

    private StringFormat() {
        throw new ExceptionInInitializerError();
    }

    /**
     * 从字符串资源中获取字符串
     */
    public static String get(@StringRes int stringRes) {
        Resources resources = Resources.getSystem();
        return resources.getString(stringRes);
    }

    /**
     * 从字符串资源中获取特殊字符串并格式化
     */
    public static String get(@StringRes int stringRes, Object... args) {
        Resources resources = Resources.getSystem();
        return String.format(resources.getString(stringRes), args);
    }
}
