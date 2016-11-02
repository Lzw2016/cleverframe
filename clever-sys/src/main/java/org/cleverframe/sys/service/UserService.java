package org.cleverframe.sys.service;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.RoleDao;
import org.cleverframe.sys.dao.UserDao;
import org.cleverframe.sys.entity.Role;
import org.cleverframe.sys.entity.User;
import org.cleverframe.sys.vo.request.UserQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Service，对应表sys_user(用户表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 22:29:06 <br/>
 */
@Service(SysBeanNames.UserService)
public class UserService extends BaseService {

    @Autowired
    @Qualifier(SysBeanNames.UserDao)
    private UserDao userDao;

    @Autowired
    @Qualifier(SysBeanNames.RoleDao)
    private RoleDao roleDao;

    /**
     * 分页查询
     */
    public Page<User> findByPage(Page<User> page, UserQueryVo userQueryVo) {
        return userDao.findByPage(page, userQueryVo);
    }

    /**
     * 新增用户
     *
     * @param user 用户
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    public boolean addUser(User user) {
        userDao.getHibernateDao().save(user);
        return true;
    }

    /**
     * 更新用户
     *
     * @param user 用户
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    public boolean updateUser(User user) {
        userDao.getHibernateDao().update(user, false, true);
        return true;
    }

    /**
     * 删除用户
     *
     * @param user 用户
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    public boolean deleteUser(User user) {
        userDao.getHibernateDao().deleteForSoft(user);
        return true;
    }

    /**
     * 查询用户的所有数据 (不分页)
     *
     * @param userId 用户ID
     */
    public List<Role> findRoleByUser(Serializable userId) {
        return roleDao.findRoleByUser(userId);
    }

    /**
     * 为用户添加角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    public boolean addUserRole(Serializable userId, Serializable roleId) {
        return userDao.addUserRole(userId, roleId);
    }

    /**
     * 为用户移除角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 成功返回true
     */
    @Transactional(readOnly = false)
    public boolean deleteUserRole(Serializable userId, Serializable roleId) {
        return userDao.deleteUserRole(userId, roleId);
    }
}
