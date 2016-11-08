package org.cleverframe.sys.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 基于Shiro实现的用户登录的令牌(Token)，继承自Shiro的UsernamePasswordToken类<br/>
 * 1.UsernamePasswordToken类包含属性：username、password、rememberMe、host<br/>
 * 2.增加验证码属性：captcha<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/8 21:54 <br/>
 */
public class UserLoginToken extends UsernamePasswordToken {
    private static final long serialVersionUID = 1L;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 默认构造
     */
    public UserLoginToken() {
        super();
    }

    /**
     * 用户登录的令牌 构造方法<br>
     *
     * @param username   用户名
     * @param password   密码
     * @param rememberMe 记住我
     * @param host       请求主机
     * @param captcha    验证码
     */
    public UserLoginToken(String username, char[] password, boolean rememberMe, String host, String captcha) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    public String toString() {
        String password = new String(this.getPassword());
        return "UserLoginToken{" +
                "username='" + getUsername() + '\'' +
                ", password='" + password + '\'' +
                ", rememberMe='" + isRememberMe() + '\'' +
                ", host='" + getHost() + '\'' +
                ", captcha='" + getCaptcha() + '\'' +
                ", captcha='" + captcha + '\'' +
                '}';
    }
}