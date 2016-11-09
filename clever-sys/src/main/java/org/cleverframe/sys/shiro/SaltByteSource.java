package org.cleverframe.sys.shiro;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Shiro的SimpleByteSource类并未实现Serializable接口，
 * 在使用Ehcahe或其他缓存框架时会出现SimpleByteSource
 * 不能序列化而报错，这里主要是让SimpleByteSource能实现
 * Serializable接口<br>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/8 23:24 <br/>
 *
 * @see org.apache.shiro.util.SimpleByteSource
 */
public class SaltByteSource implements ByteSource, Serializable {
    private static final long serialVersionUID = 1;
    private byte[] bytes;
    private String cachedHex;
    private String cachedBase64;

    public SaltByteSource() {

    }

    public SaltByteSource(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public byte[] getBytes() {
        return this.bytes;
    }

    @Override
    public String toHex() {
        if (this.cachedHex == null) {
            this.cachedHex = Hex.encodeToString(getBytes());
        }
        return this.cachedHex;
    }

    @Override
    public String toBase64() {
        if (this.cachedBase64 == null) {
            this.cachedBase64 = Base64.encodeToString(getBytes());
        }
        return this.cachedBase64;
    }

    @Override
    public boolean isEmpty() {
        return this.bytes == null || this.bytes.length == 0;
    }

    public static boolean isCompatible(Object o) {
        return o instanceof byte[] || o instanceof char[] || o instanceof String ||
                o instanceof ByteSource || o instanceof File || o instanceof InputStream;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
        this.cachedHex = null;
        this.cachedBase64 = null;
    }

    public String toString() {
        return toBase64();
    }

    public int hashCode() {
        if (this.bytes == null || this.bytes.length == 0) {
            return 0;
        }
        return Arrays.hashCode(this.bytes);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ByteSource) {
            ByteSource bs = (ByteSource) o;
            return Arrays.equals(getBytes(), bs.getBytes());
        }
        return false;
    }
}
