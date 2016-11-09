package org.cleverframe.sys.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 用户登录失败异常信息， 验证码验证失败异常类<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/8 22:27 <br/>
 */
public class UserLoginException extends AuthenticationException {
    private static final long serialVersionUID = 1L;

    /**
     * 登入失败 编码
     */
    private String errorCode;

    public UserLoginException() {
        super();
    }

    public UserLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserLoginException(String message) {
        super(message);
    }

    public UserLoginException(Throwable cause) {
        super(cause);
    }
}