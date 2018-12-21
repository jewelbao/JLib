package com.jewel.lib.android;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

import static com.jewel.lib.android.Constant.DIRECTION_BOTTOM;
import static com.jewel.lib.android.Constant.DIRECTION_LEFT;
import static com.jewel.lib.android.Constant.DIRECTION_RIGHT;
import static com.jewel.lib.android.Constant.DIRECTION_TOP;

/**
 *
 * @author Jewel
 * @version 1.0
 * @since 2018/06/15
 */
@SuppressWarnings("unused")
public final class TextViewUtil {


    private TextViewUtil() {
        throw new ExceptionInInitializerError("don't init TextViewUtil, ok!");
    }

    /**
     * @param direction The location shown, {@link Constant#DIRECTION_LEFT}, {@link Constant#DIRECTION_TOP}, {@link Constant#DIRECTION_RIGHT}, {@link Constant#DIRECTION_BOTTOM}
     * @param left      The drawable x coordinate of the drawable to be displayed
     * @param top       The drawable y coordinate of the drawable to be displayed
     * @param right     The drawable end point x coordinate to display
     * @param bottom    The drawable end point y coordinate to display
     */
    public static void setDrawableCompat(TextView view, @DrawableRes int drawableId, @Constant.Direction int direction, int left, int top, int right, int bottom) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setDrawableRelative(view, drawableId, direction, left, top, right, bottom);
        } else {
            setDrawable(view, drawableId, direction, left, top, right, bottom);
        }
    }

    /**
     * Set Drawables to display on the left, top, right, and bottom of the text.
     *
     * @param direction      The location shown, {@link Constant#DIRECTION_LEFT}, {@link Constant#DIRECTION_TOP}, {@link Constant#DIRECTION_RIGHT}, {@link Constant#DIRECTION_BOTTOM}
     * @param drawableWidth  The width of the drawable to display
     * @param drawableHeight The height of the drawable to display
     */
    public static void setDrawableCompat(TextView view, @DrawableRes int drawableId, @Constant.Direction int direction, int drawableWidth, int drawableHeight) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setDrawableRelative(view, drawableId, direction, 0, 0, drawableWidth, drawableHeight);
        } else {
            setDrawable(view, drawableId, direction, 0, 0, drawableWidth, drawableHeight);
        }
    }

    /**
     *  Set Drawables to display on the left, top, right, and bottom of the text.
     *
     * @param direction  The location shown, {@link Constant#DIRECTION_LEFT}, {@link Constant#DIRECTION_TOP}, {@link Constant#DIRECTION_RIGHT}, {@link Constant#DIRECTION_BOTTOM}
     */
    public static void setDrawableWithIntrinsicBoundsCompat(TextView view, @DrawableRes int drawableId, @Constant.Direction int direction) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setDrawableRelative(view, drawableId, direction);
        } else {
            setDrawable(view, drawableId, direction);
        }
    }

    /**
     *  Set Drawables to display on the left, top, right, and bottom of the text.
     *
     * @param direction The location shown, {@link Constant#DIRECTION_LEFT}, {@link Constant#DIRECTION_TOP}, {@link Constant#DIRECTION_RIGHT}, {@link Constant#DIRECTION_BOTTOM}
     */
    public static void setDrawableWithIntrinsicBoundsCompat(TextView view, Drawable drawable, @Constant.Direction int direction) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setDrawableRelative(view, drawable, direction);
        } else {
            setDrawable(view, drawable, direction);
        }
    }

    private static void setDrawable(TextView view, @DrawableRes int drawableId, @Constant.Direction int direction, int left, int top, int right, int bottom) {
        Drawable drawable = Resources.getSystem().getDrawable(drawableId);
        int width = right;
        int height = bottom;
        if (width <= 0 || width <= left) {
            width = drawable.getIntrinsicWidth();
        }
        if (height <= 0 || height <= top) {
            height = drawable.getIntrinsicHeight();
        }
        drawable.setBounds(left, top, width, height);
        switch (direction) {
            case DIRECTION_LEFT:
                view.setCompoundDrawables(drawable, null, null, null);
                break;
            case DIRECTION_TOP:
                view.setCompoundDrawables(null, drawable, null, null);
                break;
            case DIRECTION_RIGHT:
                view.setCompoundDrawables(null, null, drawable, null);
                break;
            case DIRECTION_BOTTOM:
                view.setCompoundDrawables(null, null, null, drawable);
                break;
        }
    }

    @SuppressLint("NewApi")
    private static void setDrawableRelative(TextView view, @DrawableRes int drawableId, @Constant.Direction int direction, int left, int top, int right, int bottom) {
        Drawable drawable = Resources.getSystem().getDrawable(drawableId);
        int width = right;
        int height = bottom;
        if (width <= 0 || width <= left) {
            width = drawable.getIntrinsicWidth();
        }
        if (height <= 0 || height <= top) {
            height = drawable.getIntrinsicHeight();
        }
        drawable.setBounds(left, top, width, height);
        switch (direction) {
            case DIRECTION_LEFT:
                view.setCompoundDrawablesRelative(drawable, null, null, null);
                break;
            case DIRECTION_TOP:
                view.setCompoundDrawablesRelative(null, drawable, null, null);
                break;
            case DIRECTION_RIGHT:
                view.setCompoundDrawablesRelative(null, null, drawable, null);
                break;
            case DIRECTION_BOTTOM:
                view.setCompoundDrawablesRelative(null, null, null, drawable);
                break;
        }
    }

    private static void setDrawable(TextView view, @DrawableRes int drawableId, @Constant.Direction int direction) {
        switch (direction) {
            case DIRECTION_LEFT:
                view.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0);
                break;
            case DIRECTION_TOP:
                view.setCompoundDrawablesWithIntrinsicBounds(0, drawableId, 0, 0);
                break;
            case DIRECTION_RIGHT:
                view.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableId, 0);
                break;
            case DIRECTION_BOTTOM:
                view.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, drawableId);
                break;
        }
    }

    private static void setDrawable(TextView view, Drawable drawable, @Constant.Direction int direction) {
        switch (direction) {
            case DIRECTION_LEFT:
                view.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                break;
            case DIRECTION_TOP:
                view.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                break;
            case DIRECTION_RIGHT:
                view.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                break;
            case DIRECTION_BOTTOM:
                view.setCompoundDrawablesWithIntrinsicBounds(null, null, null, drawable);
                break;
        }
    }

    @SuppressLint("NewApi")
    private static void setDrawableRelative(TextView view, @DrawableRes int drawableId, @Constant.Direction int direction) {
        switch (direction) {
            case DIRECTION_LEFT:
                view.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableId, 0, 0, 0);
                break;
            case DIRECTION_TOP:
                view.setCompoundDrawablesRelativeWithIntrinsicBounds(0, drawableId, 0, 0);
                break;
            case DIRECTION_RIGHT:
                view.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawableId, 0);
                break;
            case DIRECTION_BOTTOM:
                view.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, drawableId);
                break;
        }
    }

    @SuppressLint("NewApi")
    private static void setDrawableRelative(TextView view, Drawable drawable, @Constant.Direction int direction) {
        switch (direction) {
            case DIRECTION_LEFT:
                view.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
                break;
            case DIRECTION_TOP:
                view.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
                break;
            case DIRECTION_RIGHT:
                view.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null);
                break;
            case DIRECTION_BOTTOM:
                view.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, drawable);
                break;
        }
    }
}
