package org.cleverframe.sys.shiro;

import org.apache.shiro.util.SimpleByteSource;

import java.io.Serializable;

/**
 * Shiro的SimpleByteSource类并未实现Serializable接口，
 * 在使用Ehcahe或其他缓存框架时会出现SimpleByteSource
 * 不能序列化而报错，这里主要是让SimpleByteSource能实现
 * Serializable接口<br>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/8 23:24 <br/>
 */
public class SaltByteSource extends SimpleByteSource implements Serializable {
    private static final long serialVersionUID = 1;

    public SaltByteSource(byte[] bytes) {
        super(bytes);
    }
}
