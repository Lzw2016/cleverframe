package org.cleverframe.sys.attributes;

/**
 * SYS模块的Session属性范围值名称
 * Created by LiZW on 2016-4-24.
 */
public class SysSessionAttributes {

    /**
     * 用户连续登入失败次数,类型:int
     */
    public final static String LOGIN_FAILED_COUNT = "Sys_Login_Failed_Count";

    /**
     * 当前登入的用户,类型:User
     */
    public final static String LOGIN_USER = "Sys_Login_User";

    /**
     * 当前登入用户的所属公司,类型:Organization
     */
    public final static String CURRENT_COMPANY = "Sys_Current_Company";

    /**
     * 当前登入用户的归属机构,类型:Organization
     */
    public final static String CURRENT_ORG = "Sys_Current_Org";

}
