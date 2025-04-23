package com.lyranxi.link.gateway.util;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

/**
 * Base58使用58个安全字符来转换，不包含符号
 * @author fengxiaochun
 * @date   2020-04-22 14:11
 * @since  2200
 **/
public class Base58 {
    private static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final int BASE_58 = ALPHABET.length;
    private static final int BASE_256 = 256;

    private static final int[] INDEXES = new int[128];
    static {
        Arrays.fill(INDEXES, -1);
        for (int i = 0; i < ALPHABET.length; i++) {
            INDEXES[ALPHABET[i]] = i;
        }
    }

    private Base58() { }

    /**
     * UUID经过Base58压缩，区分大小写
     * @return 返回使用Base58压缩后的UUID字串，长度22
     */
    public static String uuid22(){
        return compress(UUID.randomUUID());
    }

    /**
     * 压缩UUID,输出长度22的字符串
     * @param uuid
     * @return
     */
    public static String compress(UUID uuid){
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return encode(bb.array());
    }
    /**
     * Encodes the given bytes in base58. No checksum is appended.
     */
    public static String encode(byte[] input) {
        if (input.length == 0) {
            // paying with the same coin
            return "";
        }
        // Make a copy of the input since we are going to modify it.
        input = copyOfRange(input, 0, input.length);
        // Count leading zeroes
        int zeroCount = 0;
        while (zeroCount < input.length && input[zeroCount] == 0) {
            ++zeroCount;
        }
        // The actual encoding
        byte[] temp = new byte[input.length * 2];
        int j = temp.length;
        int startAt = zeroCount;
        while (startAt < input.length) {
            byte mod = divmod58(input, startAt);
            if (input[startAt] == 0) {
                ++startAt;
            }

            temp[--j] = (byte) ALPHABET[mod];
        }
        // Strip extra '1' if any
        while (j < temp.length && temp[j] == ALPHABET[0]) {
            ++j;
        }
        // Add as many leading '1' as there were leading zeros.
        while (--zeroCount >= 0) {
            temp[--j] = (byte) ALPHABET[0];
        }
        byte[] output = copyOfRange(temp, j, temp.length);
        return new String(output);
    }

    public static byte[] decode(String input) {
        if (input.length() == 0) {
            // paying with the same coin
            return new byte[0];
        }
        byte[] input58 = new byte[input.length()];
        // Transform the String to a base58 byte sequence
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            int digit58 = -1;
            if (c >= 0 && c < 128) {
                digit58 = INDEXES[c];
            }
            if (digit58 < 0) {
                throw new RuntimeException("Not a Base58 input: " + input);
            }
            input58[i] = (byte) digit58;
        }
        // Count leading zeroes
        int zeroCount = 0;
        while (zeroCount < input58.length && input58[zeroCount] == 0) {
            ++zeroCount;
        }
        // The encoding
        byte[] temp = new byte[input.length()];
        int j = temp.length;
        int startAt = zeroCount;
        while (startAt < input58.length) {
            byte mod = divmod256(input58, startAt);
            if (input58[startAt] == 0) {
                ++startAt;
            }
            temp[--j] = mod;
        }
        // Do no add extra leading zeroes, move j to first non null byte.
        while (j < temp.length && temp[j] == 0) {
            ++j;
        }
        return copyOfRange(temp, j - zeroCount, temp.length);
    }

    /**
     * number -> number / 58, returns number % 58
     * @param number
     * @param startAt
     * @return
     */
    private static byte divmod58(byte[] number, int startAt) {
        int remainder = 0;
        for (int i = startAt; i < number.length; i++) {
            int digit256 = (int) number[i] & 0xFF;
            int temp = remainder * BASE_256 + digit256;
            number[i] = (byte) (temp / BASE_58);
            remainder = temp % BASE_58;
        }
        return (byte) remainder;
    }

    /**
     * number -> number / 256, returns number % 256
     * @param number58
     * @param startAt
     * @return
     */
    private static byte divmod256(byte[] number58, int startAt) {
        int remainder = 0;
        for (int i = startAt; i < number58.length; i++) {
            int digit58 = (int) number58[i] & 0xFF;
            int temp = remainder * BASE_58 + digit58;
            number58[i] = (byte) (temp / BASE_256);
            remainder = temp % BASE_256;
        }
        return (byte) remainder;
    }

    private static byte[] copyOfRange(byte[] source, int from, int to) {
        byte[] range = new byte[to - from];
        System.arraycopy(source, from, range, 0, range.length);
        return range;
    }
}
