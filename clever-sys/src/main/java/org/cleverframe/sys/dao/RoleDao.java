package org.cleverframe.sys.dao;

import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.entity.Role;
import org.springframework.stereotype.Repository;

/**
 * DAO，对应表sys_role(角色表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 22:36:04 <br/>
 */
@Repository(SysBeanNames.RoleDao)
public class RoleDao extends BaseDao<Role> {

}