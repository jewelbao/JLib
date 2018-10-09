package com.jewel.lib.android;

import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * Snackbar需要引入support.design库，如果没引入会导致奔溃。
 * 如果您添加JLib的依赖方式像这样：<br><code>
 *     implementation('com.jewel.lib:JLib:xx.xx.xx') { <br>
 *                exclude group: "com.android.support" <br>
 *      }
 * </code>，则需要自己依赖design库。
 */
@SuppressWarnings("unused")
public final class SnackbarUtil {
    /**
     * 不自动消失的长提示
     */
    public static void showIndefinite(View view, String text) {
        show(view, -1, text, -1, Snackbar.LENGTH_INDEFINITE);
    }

    /**
     * 2.5秒后消失的长提示
     */
    public static void showLong(View view, String text) {
        show(view, -1, text, -1, Snackbar.LENGTH_LONG);
    }

    /**
     * 1.5秒后消失的短提示
     */
    public static void showShort(View view, String text) {
        show(view, -1, text, -1, Snackbar.LENGTH_SHORT);
    }

    /**
     * 自定义显示时间的提示
     *
     * @param duration 毫秒。
     */
    public static void showDuration(View view, String text, int duration) {
        show(view, -1, text, -1, duration);
    }

    /**
     * 点击按钮隐藏提示
     */
    public static void showAction(View view, String text) {
        show(view, -1, text, -1, "隐藏", -1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, Snackbar.LENGTH_INDEFINITE);
    }

    /**
     * 点击按钮隐藏提示
     *
     * @param action         按钮文本
     * @param actionListener 按钮事件
     */
    public static void showAction(View view, String text, String action, @NonNull View.OnClickListener actionListener) {
        show(view, -1, text, -1, action, -1, actionListener, Snackbar.LENGTH_INDEFINITE);
    }

    /**
     * 自定义样式和显示时间的提示
     *
     * @param duration  毫秒。
     * @param textColor 提示文本颜色
     * @param bgColor   背景颜色
     */
    public static void show(View view, @ColorRes int bgColor, String text, @ColorRes int textColor, int duration) {
        show(view, bgColor, text, textColor, null, -1, null, duration);
    }


    /**
     * 自定义样式和显示时间的提示
     *
     * @param duration  毫秒。
     * @param textColor 提示文本颜色
     * @param bgColor   背景颜色
     */
    public static void show(View view, @ColorRes int bgColor, @NonNull String text, @ColorRes int textColor, @Nullable String action, @ColorRes int actionColor, View.OnClickListener actionListener, int duration) {
        Resources resources = view.getResources();
        Snackbar snackbar = Snackbar.make(view, text, duration);
        if (!TextUtils.isEmpty(action)) {
            snackbar.setAction(action, actionListener);
            if (actionColor != -1) {
                snackbar.setActionTextColor(resources.getColor(actionColor));
            }
        }
        View snackBarLayout = snackbar.getView();
        if (bgColor != -1) {
            snackBarLayout.setBackgroundResource(bgColor);
        }
        TextView textView = snackBarLayout.findViewById(android.support.design.R.id.snackbar_text);
        if (textView != null) {
            if (textColor != -1) {
                textView.setTextColor(resources.getColor(textColor));
            }
            textView.setMaxLines(Integer.MAX_VALUE);
        }
        snackbar.show();
    }
}
