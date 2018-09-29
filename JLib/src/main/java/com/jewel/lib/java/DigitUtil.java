package com.jewel.lib.java;

import java.math.BigDecimal;

/**
 * 数字处理工具
 */
public class DigitUtil {

    /**
     * 根据count值得到1(count个数的0)的数。例如count为2，则得到100，count为1则得到10，count小于0则得到1
     * @param count 1后补增的0个数
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
     * 获取比例
     * @param current 分子
     * @param max 分母
     * @return 比例（0-100）
     */
    public static int percent(long current, long max) {
        return (int) (halfUp((float) current / max, 2) * zeroCountToNum(2));
    }

    /**
     * 总是返回大于等于value的值，负数谨慎使用。
     *
     * @param value    需要转换的数值
     * @param newScale 小数点保留位数
     * @return 小数点后newScale+1位后若有大于0的值，则(负数舍弃newScale后几位，正数newScale位直接+1)。例如。<br><code>
     * alwaysUp(10.11001101f, 2) == 10.12 <br>
     * alwaysUp(10.11001101f, 3) == 10.111 <br>
     * alwaysUp(-10.11001101f, 2) == -10.11 <br>
     * </code>
     */
    public static float alwaysUp(float value, int newScale) {
        return getBigDecimal(value, newScale, BigDecimal.ROUND_CEILING);
    }

    /**
     * 总是返回大于等于value的值，负数谨慎使用。
     *
     * @see #alwaysUp(float, int)
     */
    public static double alwaysUp(double value, int newScale) {
        return getBigDecimal(value, newScale, BigDecimal.ROUND_CEILING);
    }

    /**
     * 获取四舍五入的值
     *
     * @param value    需要转换的数值
     * @param newScale 小数点保留位数
     * @return 四舍五入后的值。用例结果：<br><code>
     * 原值：10.105    截取小数点位数：1   结果：10.1<br>
     * 原值：10.105    截取小数点位数：2   结果：10.11<br>
     * 原值：10.105    截取小数点位数：3   结果：10.105<br>
     * 原值：-10.105    截取小数点位数：1   结果：-10.1<br>
     * 原值：-10.105    截取小数点位数：2   结果：-10.11<br>
     * 原值：-10.105    截取小数点位数：3   结果：-10.105<br>
     * </code>
     */
    public static float halfUp(float value, int newScale) {
        return getBigDecimal(value, newScale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 获取四舍五入的值
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
