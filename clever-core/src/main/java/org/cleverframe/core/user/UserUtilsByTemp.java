package org.cleverframe.core.user;

import org.cleverframe.common.user.IUserUtils;
import org.cleverframe.core.CoreBeanNames;
import org.springframework.stereotype.Service;

/**
 * 用户信息获取
 *
 * 作者：LiZW <br/>
 * 创建时间：2016-6-16 10:55 <br/>
 */
@Service(CoreBeanNames.UserUtilsByTemp)
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
