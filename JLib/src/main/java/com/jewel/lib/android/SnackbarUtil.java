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
 * Snackbar needs to introduce the support.design library, which will crash if not introduced.
 * If you add JLib dependencies like this:<br>
 * <code>implementation('com.jewel.lib:JLib:xx.xx.xx') { <br>
 *          exclude group: "com.android.support" <br>
 *    } </code> <br>
 * you need to rely on the design library yourself.
 *
 * @author Jewel
 * @version 1.0
 * @since 2018/09/15
 */
@SuppressWarnings("unused")
public final class SnackbarUtil {
    /**
     * Long prompts that don't disappear automatically
     */
    public static void showIndefinite(View view, String text) {
        show(view, -1, text, -1, Snackbar.LENGTH_INDEFINITE);
    }

    /**
     * Long reminder disappearing after 2.5 seconds
     */
    public static void showLong(View view, String text) {
        show(view, -1, text, -1, Snackbar.LENGTH_LONG);
    }

    /**
     * Short tips that disappear after 1.5 seconds
     */
    public static void showShort(View view, String text) {
        show(view, -1, text, -1, Snackbar.LENGTH_SHORT);
    }

    /**
     * Customize the prompt to display time
     *
     * @param duration millisecond.
     */
    public static void showDuration(View view, String text, int duration) {
        show(view, -1, text, -1, duration);
    }

    /**
     * Click the button to hide the prompt
     */
    public static void showAction(View view, String text) {
        show(view, -1, text, -1, "隐藏", -1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, Snackbar.LENGTH_INDEFINITE);
    }

    /**
     * Click the button to hide the prompt
     *
     * @param action         Button text
     * @param actionListener Button event
     */
    public static void showAction(View view, String text, String action, @NonNull View.OnClickListener actionListener) {
        show(view, -1, text, -1, action, -1, actionListener, Snackbar.LENGTH_INDEFINITE);
    }

    /**
     * Tips for custom styles and display time
     *
     * @param duration  millisecond.
     * @param textColor Prompt text color
     * @param bgColor   background color
     */
    public static void show(View view, @ColorRes int bgColor, String text, @ColorRes int textColor, int duration) {
        show(view, bgColor, text, textColor, null, -1, null, duration);
    }


    /**
     * Tips for custom styles and display time
     *
     * @param duration  millisecond.
     * @param textColor Prompt text color
     * @param bgColor   background color
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
