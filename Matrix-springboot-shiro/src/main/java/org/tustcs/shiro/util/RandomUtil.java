package org.tustcs.shiro.util;

import java.util.Random;

public class RandomUtil {

    /**
     * 获取指定位数的随机字符串
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String randomString = "qwertyuiopasdfghjklzxcvbnm1234567890";
        int lengths = randomString.length();
        StringBuffer stringBuffer = new StringBuffer(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            stringBuffer.append(randomString.charAt(random.nextInt(lengths)));
        }
        return stringBuffer.toString();
    }
}
