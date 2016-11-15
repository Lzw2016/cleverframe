package org.cleverframe.sys.utils;

import org.cleverframe.sys.attributes.SysSessionAttributes;

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
        httpSession.removeAttribute(SysSessionAttributes.LOGIN_FAILED_COUNT);
        httpSession.removeAttribute(SysSessionAttributes.LOGIN_USER);
        httpSession.removeAttribute(SysSessionAttributes.CURRENT_COMPANY);
        httpSession.removeAttribute(SysSessionAttributes.CURRENT_ORG);
        httpSession.removeAttribute(SysSessionAttributes.LOGIN_VALIDATE_CODE);
    }
}
