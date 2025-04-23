package com.lyranxi.link.user.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 数字混淆转换器,将long转换为可恢复的字符串
 *
 * @author ranxi
 * @date 2025-04-14 15:25
 */
public class NumberConfuseConverter {

    private static final int MIN_DICT_LEN = 10;
    private final char[] digits;
    private final int seed;

    public NumberConfuseConverter(String dict) {
        if (dict == null || dict.length() < MIN_DICT_LEN) {
            throw new IllegalArgumentException("dict cannot be null and length must be more than " + MIN_DICT_LEN);
        }
        this.digits = dict.toCharArray();
        this.seed = this.digits.length;
    }

    /**
     * 将十进制数字转换为制定进制的字符串
     *
     * @param number 十进制数字
     * @return 制定进制的字符串
     */
    public String toOtherNumberSystem(long number) {
        if (number < 0) {
            number = ((long) 2 * 0x7fffffff) + number + 2;
        }
        char[] buf = new char[32];
        int charPos = 32;
        while ((number / seed) > 0) {
            buf[--charPos] = digits[(int) (number % seed)];
            number /= seed;
        }
        buf[--charPos] = digits[(int) (number % seed)];
        return new String(buf, charPos, (32 - charPos));
    }


    /**
     * 将其它进制的数字（字符串形式）转换为十进制的数字
     *
     * @param number 其它进制的数字（字符串形式）
     * @return 十进制的数字
     */
    public long toDecimalNumber(String number) {
        char[] charBuf = number.toCharArray();
        if (seed == 10) {
            return Long.parseLong(number);
        }

        long result = 0, base = 1;

        for (int i = charBuf.length - 1; i >= 0; i--) {
            int index = 0;
            for (int j = 0, length = digits.length; j < length; j++) {
                //找到对应字符的下标，对应的下标才是具体的数值
                if (digits[j] == charBuf[i]) {
                    index = j;
                }
            }
            result += index * base;
            base *= seed;
        }
        return result;
    }

    /**
     * 字符串内容打乱(随机), 如: abcdef -> dacfe
     *
     * @param str 原字符串
     * @return String  打乱顺序的字符串
     **/
    public static String shuffle(String str) {
        if (str == null || str.length() < 1) {
            return "";
        }
        List<String> list = Arrays.asList(str.split(""));
        Collections.shuffle(list);
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            builder.append(s);
        }
        return builder.toString();
    }
}
