package org.cleverframe.shiro.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 基于Shiro实现的用户登入的令牌(Token)，继承自Shiro的UsernamePasswordToken类<br>
 * 1.UsernamePasswordToken类包含属性：username、password、rememberMe、host<br>
 * 2.增加验证码属性：captcha<br>
 * 
 * @author LiZW
 * @version 2015年6月18日 下午1:49:27
 */
public class ShiroUserToken extends UsernamePasswordToken
{
	private static final long serialVersionUID = 1L;

	/** 验证码 */
	private String captcha;
	
	/** 默认构造 */
	public ShiroUserToken()
	{
		super();
	}
	
	/**
	 * 用户登入的令牌 构造方法<br>
	 * 
	 * @param username 用户名
	 * @param password 密码
	 * @param rememberMe 记住我
	 * @param host 请求主机
	 * @param captcha 验证码
	 * */
	public ShiroUserToken(String username, char[] password, boolean rememberMe, String host, String captcha)
	{
		super(username, password, rememberMe, host);
		this.captcha = captcha;
	}

	public String getCaptcha()
	{
		return captcha;
	}

	public void setCaptcha(String captcha)
	{
		this.captcha = captcha;
	}
}
