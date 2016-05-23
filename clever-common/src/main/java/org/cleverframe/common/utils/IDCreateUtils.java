package org.cleverframe.common.utils;

import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 16:14 <br/>
 */
public class IDCreateUtils {
    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无"-"分割.<br/>
     * 例如：57d7058dbc79444db7e57a5d0b955cc8<br/>
     */
    public static String uuidNotSplit() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 封装JDK自带的UUID<br/>
     * 例如：57d7058d-bc79-444d-b7e5-7a5d0b955cc8<br/>
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }
}
