package org.cleverframe.sys.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.user.IUserUtils;
import org.cleverframe.sys.entity.Organization;
import org.cleverframe.sys.entity.User;
import org.cleverframe.sys.shiro.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/17 17:16 <br/>
 */
@Service(SpringBeanNames.UserUtils)
public class UserUtilsByShiro implements IUserUtils {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(UserUtilsByShiro.class);

    /**
     * 获取当前登录的用户
     */
    public UserPrincipal getCurrentUser() {
        Object object = SecurityUtils.getSubject().getPrincipal();
        if (object == null) {
            logger.warn("获取当前Principal为空");
            return null;
        }
        if (!(object instanceof UserPrincipal)) {
            logger.warn("当前Principal类型不是UserPrincipal, Principal Class=" + object.getClass().getName());
            return null;
        }
        return (UserPrincipal) object;
    }

    @Override
    public String getUserCode() {
        String defaultCode = "-1";
        UserPrincipal userPrincipal = getCurrentUser();
        if (userPrincipal == null) {
            return defaultCode;
        }
        User user = userPrincipal.getUser();
        if (user == null) {
            throw new RuntimeException("当前Principal的User为空");
        }
        if (StringUtils.isBlank(user.getLoginName())) {
            throw new RuntimeException("当前User的LoginName为空");
        }
        return user.getLoginName();
    }

    @Override
    public String getCompanyCode() {
        String defaultCode = "-1";
        UserPrincipal userPrincipal = getCurrentUser();
        if (userPrincipal == null) {
            return defaultCode;
        }
        Organization organization = userPrincipal.getHomeCompany();
        if (organization == null) {
            logger.warn("当前Principal的HomeCompany(归属公司)为空");
            return defaultCode;
        }
        if (StringUtils.isBlank(organization.getCode())) {
            logger.warn("当前HomeCompany(归属公司)的code(机构编码)为空");
            return defaultCode;
        }
        return organization.getCode();
    }

    @Override
    public String getOrgCode() {
        String defaultCode = "-1";
        UserPrincipal userPrincipal = getCurrentUser();
        if (userPrincipal == null) {
            return defaultCode;
        }
        Organization organization = userPrincipal.getHomeOrg();
        if (organization == null) {
            logger.warn("当前Principal的HomeOrg(直属机构)为空");
            return defaultCode;
        }
        if (StringUtils.isBlank(organization.getCode())) {
            logger.warn("当前HomeOrg(直属机构)的code(机构编码)为空");
            return defaultCode;
        }
        return organization.getCode();
    }
}
