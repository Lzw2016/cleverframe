package org.cleverframe.sys.dao;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.entity.Role;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

/**
 * DAO，对应表sys_role(角色表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 22:36:04 <br/>
 */
@Repository(SysBeanNames.RoleDao)
public class RoleDao extends BaseDao<Role> {

    /**
     * 分页查询角色数据
     *
     * @param name 角色名称 like匹配
     */
    public Page<Role> findByPage(Page<Role> page, String uuid, Long id, Character delFlag, String name) {
        Parameter param = new Parameter(delFlag);
        param.put("uuid", uuid);
        param.put("id", id);
        param.put("name", "%" + name + "%");
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.RoleDao.findByPage");
        return hibernateDao.findBySql(page, sql, param);
    }

    /**
     * 角色添加资源
     *
     * @param roleId      角色ID
     * @param resourcesId 资源ID
     * @return 成功返回 true
     */
    public boolean addRoleResources(Long roleId, Long resourcesId) {
        Parameter param = new Parameter();
        param.put("roleId", roleId);
        param.put("resourcesId", resourcesId);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.RoleDao.addRoleResources");
        SQLQuery sqlQuery = hibernateDao.createSqlQuery(sql, param);
        return sqlQuery.executeUpdate() == 1;
    }

    /**
     * 角色移除资源
     *
     * @param roleId      角色ID
     * @param resourcesId 资源ID
     * @return 成功返回 true
     */
    public boolean deleteRoleResources(Long roleId, Long resourcesId) {
        Parameter param = new Parameter();
        param.put("roleId", roleId);
        param.put("resourcesId", resourcesId);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.RoleDao.deleteRoleResources");
        SQLQuery sqlQuery = hibernateDao.createSqlQuery(sql, param);
        return sqlQuery.executeUpdate() == 1;
    }
}