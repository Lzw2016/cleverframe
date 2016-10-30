package org.cleverframe.sys.service;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.UserDao;
import org.cleverframe.sys.entity.User;
import org.cleverframe.sys.vo.request.UserQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
    public boolean deleteUser(User user) {
        userDao.getHibernateDao().deleteForSoft(user);
        return true;
    }
}
