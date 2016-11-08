package org.cleverframe.sys.attributes;

import org.cleverframe.common.attributes.ISessionAttributes;

/**
 * SYS模块的Session属性范围值名称
 * Created by LiZW on 2016-4-24.
 */
public class SysSessionAttributes implements ISessionAttributes {
    /**
     * 用户连续登录失败次数,类型:int
     */
    public final static String LOGIN_FAILED_COUNT = "sys_login_failed_count";
    /**
     * 当前登录的用户,类型:User
     */
    public final static String LOGIN_USER = "sys_login_user";

    /**
     * 当前登录用户的所属公司,类型:Organization
     */
    public final static String CURRENT_COMPANY = "sys_current_company";

    /**
     * 当前登录用户的归属机构,类型:Organization
     */
    public final static String CURRENT_ORG = "sys_current_org";

    /**
     * 用户登录验证码
     */
    public final static String LOGIN_VALIDATE_CODE = "sys_login_validate_code";

}
