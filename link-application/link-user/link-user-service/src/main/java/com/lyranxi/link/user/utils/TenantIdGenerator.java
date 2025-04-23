package com.lyranxi.link.user.utils;

/**
 * 6位字符门店ID生成器
 * dict示例(34位,没有字母l): 0123456789abcdefghijkmnopqrstuvwxyz
 * 永远不会被分配的: llllll
 *
 * @author ranxi
 */
public class TenantIdGenerator {
    /**
     * 永远不会生成的序列,6个l
     */
    public static final String NEVER = "llllll";
    /**
     * 门店号临界值
     */
    public static final long MIN_ID = 52521875L;
    public static final long MAX_ID = 1838265624L;
    /**
     * 门店ID字符串长度
     */
    private static final int ID_LENGTH = 6;
    private final NumberConfuseConverter converter;

    public TenantIdGenerator(String dict) {
        if (dict == null || dict.isEmpty()) {
            throw new NullPointerException("dict can not be null");
        }
        if (dict.length() != 35) {
            throw new IllegalArgumentException("dict length must be 35");
        }
        this.converter = new NumberConfuseConverter(dict);
    }

    /**
     * 生成6位长度门店ID,num介于[601692057,34296447248L]
     *
     * @param num 介于[601692057,34296447248L]
     * @return String
     * @since 2206
     **/
    public String toString(long num) {
        checkStoreIdNum(num);
        String str = converter.toOtherNumberSystem(num);
        if (str == null || str.length() != ID_LENGTH) {
            throw new IllegalArgumentException("生成的ID不是6位长度:" + str);
        }
        char[] chars = str.toCharArray();
        // 位置1/4交换
        swap(chars, 0, 3);
        // 位置2/6交换
        swap(chars, 1, 5);
        return new String(chars);
    }

    /**
     * 解密门店ID
     *
     * @param tenantId 6位字符串长度的门店ID
     * @return long  门店值
     * @since 2206
     **/
    public long toNumber(String tenantId) {
        if (tenantId == null || tenantId.length() != ID_LENGTH) {
            throw new IllegalArgumentException("生成的ID不是6位长度:" + tenantId);
        }
        char[] chars = tenantId.toCharArray();
        // 位置6/2交换
        swap(chars, 5, 1);
        // 位置4/1交换
        swap(chars, 3, 0);
        String realStoreId = new String(chars);
        return converter.toDecimalNumber(realStoreId);
    }

    /**
     * 交换位置
     *
     * @param chars 原始char数组
     * @param seat1 位置1
     * @param seat2 位置2
     * @since 2206
     **/
    private void swap(char[] chars, int seat1, int seat2) {
        char tmp = chars[seat2];
        chars[seat2] = chars[seat1];
        chars[seat1] = tmp;
    }

    /**
     * 检查门店值取值范围,因为生成的门店ID字符串必须时6位的
     *
     * @param num 门店ID值检查
     **/
    private void checkStoreIdNum(long num) {
        if (num < MIN_ID || num > MAX_ID) {
            throw new IllegalArgumentException("num['" + num + "'] must between " + MIN_ID + " and " + MAX_ID);
        }
    }


}
