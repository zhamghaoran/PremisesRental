package com.rental.premisesrental.util;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.MD5;

/**
 * @author 20179
 */
public class MD5Util {
    /**
     *
     * @param data 需要被加密的字符串
     * @return 加密的md5 16位字符串
     */
    public static String getMd5Encode(String data) {
        MD5 md5 = MD5.create();
        return md5.digestHex16(data);
    }
}
