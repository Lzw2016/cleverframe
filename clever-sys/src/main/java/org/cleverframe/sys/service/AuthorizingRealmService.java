package org.cleverframe.sys.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.OrganizationDao;
import org.cleverframe.sys.dao.ResourcesDao;
import org.cleverframe.sys.dao.RoleDao;
import org.cleverframe.sys.dao.UserDao;
import org.cleverframe.sys.entity.Organization;
import org.cleverframe.sys.entity.Resources;
import org.cleverframe.sys.entity.Role;
import org.cleverframe.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 授权和认证的Service 用于到数据库查询用户、角色、权限等信息<br>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/8 22:59 <br/>
 */
@Service(SysBeanNames.AuthorizingRealmService)
public class AuthorizingRealmService extends BaseService {

    @Autowired
    @Qualifier(SysBeanNames.UserDao)
    private UserDao userDao;

    @Autowired
    @Qualifier(SysBeanNames.OrganizationDao)
    private OrganizationDao organizationDao;

    @Autowired
    @Qualifier(SysBeanNames.RoleDao)
    private RoleDao roleDao;

    @Autowired
    @Qualifier(SysBeanNames.ResourcesDao)
    private ResourcesDao resourcesDao;

    /**
     * 根据用户登录名查询用户信息（包括软删除的用户）
     *
     * @param loginName 用户登录名
     * @return 不存在返回null
     */
    public User getUserByLoginName(String loginName) {
        return userDao.getByLoginName(loginName);
    }

    /**
     * 返回用户的当前公司（不含软删除数据）
     *
     * @param user 当前用户
     * @return 当前用户的所属公司，不存在返回空
     */
    public Organization getHomeCompany(User user) {
        if (user == null) {
            return null;
        }
        return organizationDao.getOrganizationByCode(user.getHomeCompany());
    }

    /**
     * 返回用户的当前上级机构（不含软删除数据）
     *
     * @param user 当前用户
     * @return 当前用户的上级机构，不存在返回空
     */
    public Organization getHomeOrg(User user) {
        if (user == null) {
            return null;
        }
        return organizationDao.getOrganizationByCode(user.getHomeOrg());
    }

    /**
     * 获取用户所有的角色信息（不含软删除数据）
     *
     * @param user 用户
     * @return 不存在返回空集合
     */
    public List<Role> findRoleByUser(User user) {
        List<Role> result = new ArrayList<>();
        if (user == null) {
            return result;
        }
        List<Role> roleList = roleDao.findRoleByUser(user.getId());
        for (Role role : roleList) {
            if (Role.DEL_FLAG_NORMAL.equals(role.getDelFlag())) {
                result.add(role);
            }
        }
        return result;
    }

    /**
     * 获取用户所有的资源信息（不含软删除数据）
     *
     * @param user 用户
     * @return 不存在返回空集合
     */
    public List<Resources> findResourcesByUser(User user) {
        List<Resources> result = new ArrayList<>();
        if (user == null) {
            return result;
        }
        // 数据量大时 性能低下
//        result = resourcesDao.getResourcesByUser(user.getLoginName());

        // 大数据量使用
        Map<Long, Resources> map = new HashMap<>();
        List<Role> roleList = roleDao.findRoleByUser(user.getId());
        for (Role role : roleList) {
            if (!Role.DEL_FLAG_NORMAL.equals(role.getDelFlag())) {
                continue;
            }
            List<Resources> resourcesList = resourcesDao.findResourcesByRole(role.getId());
            for (Resources resources : resourcesList) {
                map.put(resources.getId(), resources);
            }
        }
        result.addAll(map.values());
        return result;
    }
}
