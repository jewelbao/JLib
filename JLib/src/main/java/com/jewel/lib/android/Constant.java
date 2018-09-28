package com.jewel.lib.android;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Constant {

    public static final int DIRECTION_LEFT = 0;
    public static final int DIRECTION_TOP = 1;
    public static final int DIRECTION_RIGHT = 2;
    public static final int DIRECTION_BOTTOM = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({DIRECTION_LEFT, DIRECTION_TOP, DIRECTION_RIGHT, DIRECTION_BOTTOM})
    @interface Direction {}


}
