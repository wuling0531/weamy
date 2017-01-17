package com.waemy.utils;

import java.util.Random;

/**
 * 随机字符串的生成
 * @since 2015年9月17日 上午11:25:13
 * @author wuling
 */
public class RandomStringUtil {
    
    public static String getRandomString(int length) {
        StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(random.nextInt(range)));
        }
        return sb.toString();
    }
}
