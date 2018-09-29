package com.jewel.lib.java;

import org.junit.Test;


public class DigitUtilTest {

    @Test
    public void getPercent() {
        for (int i = 0; i < 100; i++) {
            System.out.println(DigitUtil.percent(i, 100));
        }
    }

    /**
     * 测试方法：假设测试的值为N个小数点的数<br>
     * 1.截取小数点规格：<br>
     * > a.截取N-1个小数点<br>
     * > b.截取N个小数点<br>
     * > c.截取N+1个小数点<br>
     * > d.截取0个小数点<br>
     * 2.根据正负数分别测试。
     */
    @Test
    public void alwaysUp() {
        for (int i = 1; i < 10; i++) {
            double value = DigitUtil.alwaysUp(10.1100111d, i);
            System.out.println("原值：10.1100111" + "    截取小数点位数：" + i + "   结果：" + String.valueOf(value));
        }
    }

    @Test
    public void halfUp() {
        for (int i = 1; i < 4; i++) {
            double value =  DigitUtil.halfUp(10.105, i);
            System.out.println("原值：10.105" + "    截取小数点位数：" + i + "   结果：" + String.valueOf(value));
        }

        for (int i = 1; i < 4; i++) {
            double value =  DigitUtil.halfUp(-10.105, i);
            System.out.println("原值：-10.105" + "    截取小数点位数：" + i + "   结果：" + String.valueOf(value));
        }

        for (int i = 1; i < 4; i++) {
            float value = DigitUtil.halfUp(10.105f, i);
            System.out.println("原值：10.105" + "    截取小数点位数：" + i + "   结果：" + String.valueOf(value));
        }

        for (int i = 1; i < 4; i++) {
            float value = DigitUtil.halfUp(-10.105f, i);
            System.out.println("原值：-10.105" + "    截取小数点位数：" + i + "   结果：" + String.valueOf(value));
        }
    }
}