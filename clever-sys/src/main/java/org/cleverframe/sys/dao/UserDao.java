package org.cleverframe.sys.dao;


import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.entity.User;
import org.springframework.stereotype.Repository;

/**
 * DAO，对应表sys_user(用户表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 22:29:06 <br/>
 */
@Repository(SysBeanNames.UserDao)
public class UserDao extends BaseDao<User> {

}
