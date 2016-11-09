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
     * 系统内部处理异常
     */
    public final static String System_Exception = "001";

    /**
     * 没有使用验证登录
     */
    public final static String Not_Validate_Code = "101";

    /**
     * 验证码失效(超时)
     */
    public final static String Validate_Code_Invalid = "102";

    /**
     * 验证码不匹配(验证码错误)
     */
    public final static String Validate_Code_Error = "103";

    /**
     * 账号不存在
     */
    public final static String Account_Not_Exists = "201";

    /**
     * 用户名、密码为空
     */
    public final static String UserName_Password_Is_Empty = "202";

    /**
     * 帐号被删除
     */
    public final static String Account_Delete = "203";

    /**
     * 你的帐号已被锁定
     */
    public final static String Account_Locked = "204";

    /**
     * 员工离职
     */
    public final static String User_Leave = "205";

    /**
     * 登入失败 编码
     */
    private String errorCode;

    public UserLoginException() {
        super();
    }

    /**
     * @param errorCode 登入错误码
     * @param message   登入错误消息
     */
    public UserLoginException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * @param errorCode 登入错误码
     * @param message   登入错误消息
     * @param cause     内部错误
     */
    public UserLoginException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}