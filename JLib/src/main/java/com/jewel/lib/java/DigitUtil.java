package com.jewel.lib.java;

import java.math.BigDecimal;

/**
 * 数字处理工具
 */
public class DigitUtil {

    /**
     * A number of 1 (count of 0) is obtained from the count value. For example, if count is 2, then 100 is obtained, count is 1 and 10 is obtained, and count is less than 0, and 1 is obtained.
     *
     * @param count 0 number after 1 increase
     * @return long
     */
    public static long zeroCountToNum(int count) {
        long num = 1;
        if (count <= 0) {
            return num;
        }
        for (int i = 1; i <= count; i++) {
            num *= 10;
        }
        return num;
    }

    /**
     * Acquisition ratio
     *
     * @param current molecule
     * @param max     Denominator
     * @return Proportion (0-100)
     */
    public static int percent(long current, long max) {
        return (int) (halfUp((float) current / max, 2) * zeroCountToNum(2));
    }

    /**
     * Always return a value greater than or equal to value, and use negative numbers with caution.
     *
     * @param value    The value to be converted
     * @param newScale Decimal point reserved
     * @return If there is a value greater than 0 after the newScale+1 bit after the decimal point, then (the negative number discards the new digit after the newScale, the positive newScale bit is directly +1). E.g.
     * alwaysUp(10.11001101f, 2) == 10.12
     * alwaysUp(10.11001101f, 3) == 10.111
     * alwaysUp(-10.11001101f, 2) == -10.11
     *
     */
    public static float alwaysUp(float value, int newScale) {
        return getBigDecimal(value, newScale, BigDecimal.ROUND_CEILING);
    }

    /**
     * Always return a value greater than or equal to value, and use negative numbers with caution.
     *
     * @see #alwaysUp(float, int)
     */
    public static double alwaysUp(double value, int newScale) {
        return getBigDecimal(value, newScale, BigDecimal.ROUND_CEILING);
    }

    /**
     * Get rounded values
     *
     * @param value    The value to be converted
     * @param newScale Decimal point reserved
     * @return The value after rounding. Use case results:
     * Original value: 10.105 Intercept the number of decimal places: 1 Result: 10.1<br>
     * Original value: 10.105 Intercept the number of decimal places: 2 Result: 10.11<br>
     * Original value: 10.105 Intercept the number of decimal places: 3 Results: 10.105<br>
     * Original value: -10.105 Intercept the number of decimal places: 1 Result: -10.1<br>
     * Original value: -10.105 Intercept the number of decimal places: 2 Results: -10.11<br>
     * Original value: -10.105 Intercept the number of decimal places: 3 Results: -10.105<br>
     *
     */
    public static float halfUp(float value, int newScale) {
        return getBigDecimal(value, newScale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Get rounded values
     *
     * @see #halfUp(float, int)
     */
    public static double halfUp(double value, int newScale) {
        return getBigDecimal(value, newScale, BigDecimal.ROUND_HALF_UP);
    }

    private static float getBigDecimal(float value, int newScale, int roundingMode) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(newScale, roundingMode).floatValue();
    }

    private static double getBigDecimal(double value, int newScale, int roundingMode) {
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.setScale(newScale, roundingMode).doubleValue();
    }
}
