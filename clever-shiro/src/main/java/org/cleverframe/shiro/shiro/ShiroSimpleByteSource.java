package org.cleverframe.shiro.shiro;

import org.apache.shiro.util.SimpleByteSource;

import java.io.Serializable;

/**
 * Shiro的SimpleByteSource类并未实现Serializable接口，
 * 在使用Ehcahe或其他缓存框架时会出现SimpleByteSource
 * 不能序列化而报错，这里主要是让SimpleByteSource能实现
 * Serializable接口<br>
 * 
 * @author LiZhiWei
 * @version 2015年6月20日 上午8:39:39
 */
public class ShiroSimpleByteSource extends SimpleByteSource implements Serializable
{
	private static final long serialVersionUID = 1;

	public ShiroSimpleByteSource(byte[] bytes)
	{
		super(bytes);
	}
}
