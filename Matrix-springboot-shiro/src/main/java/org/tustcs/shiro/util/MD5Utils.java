package org.tustcs.shiro.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by L.key on 2017/6/2.
 */
public class MD5Utils {

    public static String getMD5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            // 使用指定的 byte 数组对摘要进行最后更新，然后完成摘要计算。
            // 也就是说，此方法首先调用 update(input)，向 update 方法传递 input 数组，然后调用 digest()。
            byte[] bytes = messageDigest.digest(str.getBytes("UTF-8"));

            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, bytes).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
