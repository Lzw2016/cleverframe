package org.cleverframe.shiro.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * Shiro身份验证异常， 验证码验证失败异常类<br>
 * 
 * @author LiZW
 * @version 2015年6月19日 下午2:03:12
 */
public class CaptchaException extends AuthenticationException
{
	private static final long serialVersionUID = 1L;

	public CaptchaException()
	{
		super();
	}

	public CaptchaException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public CaptchaException(String message)
	{
		super(message);
	}

	public CaptchaException(Throwable cause)
	{
		super(cause);
	}
}
