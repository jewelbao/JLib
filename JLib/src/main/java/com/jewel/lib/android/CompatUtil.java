package com.jewel.lib.android;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

/**
 * 兼容新旧版本的方法集
 *
 * @author jewelbao
 * @version 1.0
 * @since 2016/9/1
 */
@SuppressWarnings("ALL")
public class CompatUtil {

    public static void setOnPageChangeListener(ViewPager v, ViewPager.OnPageChangeListener p) {
        if (isBelowVersion(23)) {
            v.setOnPageChangeListener(p);
        } else {
            v.addOnPageChangeListener(p);
        }
    }

    public static void setBackground(View view, Drawable drawable) {
        if (isBelowVersion(16)) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    public static void setBackground(View view, @DrawableRes int drawableRes) {
        if (isBelowVersion(16)) {
            view.setBackgroundDrawable(getDrawable(view.getContext(), drawableRes));
        } else {
            view.setBackground(getDrawable(view.getContext(), drawableRes));
        }
    }

    public static void setTextAppearance(TextView view, @StyleRes int appearanceRes) {
        if (isBelowVersion(23)) {
            view.setTextAppearance(view.getContext(), appearanceRes);
        } else {
            view.setTextAppearance(appearanceRes);
        }
    }

    public static Drawable getDrawable(Context context, @DrawableRes int drawableRes) {
        if (isBelowVersion(21)) {
            return context.getResources().getDrawable(drawableRes);
        } else {
            return context.getDrawable(drawableRes);
        }
    }

    public static String getString(Context context, @StringRes int stringRes) {
        if (isBelowVersion(21)) {
            return context.getResources().getString(stringRes);
        } else {
            return context.getString(stringRes);
        }
    }

    public static int getColor(Context context, @ColorRes int colorRes) {
        if (isBelowVersion(23)) {
            return context.getResources().getColor(colorRes);
        } else {
            return context.getResources().getColor(colorRes, null);
        }
    }

    public static void setPadding(TextView view, int left, int top, int right, int bottom) {
        if (isBelowVersion(15)) {
            view.setPaddingRelative(left, top, right, bottom);
        } else {
            view.setPadding(left, top, right, bottom);
        }
    }

    public static void clearBackground(View... views) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    Drawable drawable = view.getBackground();
                    if (drawable != null) {
                        if (isBelowVersion(16)) {
                            view.setBackgroundDrawable(null);
                        } else {
                            view.setBackground(null);
                        }
                        drawable.setCallback(null);
                    }
                }
            }
        }

    }

    /**
     * Compile version is less than version
     */
    public static boolean isBelowVersion(int version) {
        if (Build.VERSION.SDK_INT < version) {
            return true;
        } else
            return false;
    }
}
