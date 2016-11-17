package org.cleverframe.filemanager.utils;

import org.cleverframe.common.codec.DigestUtils;
import org.cleverframe.common.codec.EncodeDecodeUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * 用于处理文件签名的工具类，文件签名统一处理<br/>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/17 22:25 <br/>
 */
public class FileDigestUtils {
    /**
     * 获取文件的MD5签名字符串<br>
     *
     * @param data 文件二进制数据
     * @return 文件的MD5签名字符串
     */
    public static String FileDigestByMD5(byte[] data) {
        return EncodeDecodeUtils.encodeHex(DigestUtils.md5(data));
    }

    /**
     * 获取文件的SHA1签名字符串<br>
     *
     * @param data 文件二进制数据
     * @return 文件的SHA1签名字符串
     */
    public static String FileDigestBySHA1(byte[] data) {
        return EncodeDecodeUtils.encodeHex(DigestUtils.sha1(data));
    }

    /**
     * 获取文件的MD5签名字符串<br>
     *
     * @param stream 文件流
     * @return 文件的MD5签名字符串
     * @throws IOException 操作失败
     */
    public static String FileDigestByMD5(InputStream stream) throws IOException {
        return EncodeDecodeUtils.encodeHex(DigestUtils.md5(stream));
    }

    /**
     * 获取文件的SHA1签名字符串<br>
     *
     * @param stream 文件流
     * @return 文件的SHA1签名字符串
     * @throws IOException 操作失败
     */
    public static String FileDigestBySHA1(InputStream stream) throws IOException {
        return EncodeDecodeUtils.encodeHex(DigestUtils.sha1(stream));
    }
}
