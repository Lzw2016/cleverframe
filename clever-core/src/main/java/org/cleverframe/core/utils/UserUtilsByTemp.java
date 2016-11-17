package org.cleverframe.core.utils;

import org.cleverframe.common.user.IUserUtils;

/**
 * 用户信息获取
 *
 * 作者：LiZW <br/>
 * 创建时间：2016-6-16 10:55 <br/>
 */
//@Service(SpringBeanNames.UserUtils)
public class UserUtilsByTemp implements IUserUtils {
    @Override
    public String getUserCode() {
        return "Root";
    }

    @Override
    public String getCompanyCode() {
        return "Root";
    }

    @Override
    public String getOrgCode() {
        return "Root";
    }
}
