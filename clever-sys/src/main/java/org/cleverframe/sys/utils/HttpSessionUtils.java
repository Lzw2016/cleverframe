package org.cleverframe.sys.utils;

import org.cleverframe.common.utils.ConversionUtils;
import org.cleverframe.common.vo.ValidateCode;
import org.cleverframe.sys.attributes.SysSessionAttributes;
import org.cleverframe.sys.entity.User;

import javax.servlet.http.HttpSession;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/15 19:48 <br/>
 */
public class HttpSessionUtils {

    /**
     * 用户退出时清除HttpSession里的用户登录信息
     */
    public static void logout(HttpSession httpSession) {
        httpSession.removeAttribute(SysSessionAttributes.LOGIN_USER);
        httpSession.removeAttribute(SysSessionAttributes.CURRENT_COMPANY);
        httpSession.removeAttribute(SysSessionAttributes.CURRENT_ORG);
    }

    /**
     * 返回用户连续登录失败的次数
     */
    public static int getLoginFailedCount(HttpSession httpSession) {
        if (httpSession == null) {
            throw new RuntimeException("参数HttpSession不能为空");
        }
        return ConversionUtils.converter(httpSession.getAttribute(SysSessionAttributes.LOGIN_FAILED_COUNT), 0);
    }

    /**
     * 设置用户连续登录失败的次数
     */
    public static void setLoginFailedCount(HttpSession httpSession, int count) {
        httpSession.setAttribute(SysSessionAttributes.LOGIN_FAILED_COUNT, count);
    }

    /**
     * 返回登录的用户
     */
    public static User getLoginUser(HttpSession httpSession) {
        Object object = httpSession.getAttribute(SysSessionAttributes.LOGIN_USER);
        if (!(object instanceof User)) {
            return null;
        }
        return (User) object;
    }

    /**
     * 设置登录的用户
     */
    public static void setLoginUser(HttpSession httpSession, User user) {
        httpSession.setAttribute(SysSessionAttributes.LOGIN_USER, user);
    }

    /**
     * 返回用户登录验证码,并从Session中移除验证码信息
     */
    public static ValidateCode getLoginValidateCode(HttpSession httpSession) {
        Object object = httpSession.getAttribute(SysSessionAttributes.LOGIN_VALIDATE_CODE);
        httpSession.removeAttribute(SysSessionAttributes.LOGIN_VALIDATE_CODE);
        if (!(object instanceof ValidateCode)) {
            return null;
        }
        return (ValidateCode) object;
    }

    /**
     * 设置用户登录验证码信息
     */
    public static void setLoginValidateCode(HttpSession httpSession, ValidateCode validateCode) {
        httpSession.setAttribute(SysSessionAttributes.LOGIN_VALIDATE_CODE, validateCode);
    }
}
