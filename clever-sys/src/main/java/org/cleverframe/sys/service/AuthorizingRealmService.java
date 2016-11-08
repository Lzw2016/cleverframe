package org.cleverframe.sys.service;

import org.cleverframe.common.service.BaseService;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.dao.UserDao;
import org.cleverframe.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 授权和认证的Service 用于到数据库查询用户、角色、权限等信息<br>
 *
 * 作者：LiZW <br/>
 * 创建时间：2016/11/8 22:59 <br/>
 */
@Service(SysBeanNames.AuthorizingRealmService)
public class AuthorizingRealmService extends BaseService {

    @Autowired
    @Qualifier(SysBeanNames.UserDao)
    private UserDao userDao;

    /**
     * 根据用户登录名查询用户信息
     * @param loginName 用户登录名
     * @return 不存在返回null
     */
    public User getUserByLoginName(String loginName){
       return userDao.getByLoginName(loginName);
    }

}
