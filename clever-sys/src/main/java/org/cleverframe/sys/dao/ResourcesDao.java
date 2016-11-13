package org.cleverframe.sys.dao;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.entity.Resources;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/10/21 17:08 <br/>
 */
@Repository(SysBeanNames.ResourcesDao)
public class ResourcesDao extends BaseDao<Resources> {

    /**
     * 分页查询数据
     *
     * @param title         资源标题
     * @param resourcesUrl  资源URL地址
     * @param permission    资源访问所需要的权限标识字符串
     * @param resourcesType 包含的资源类型（1:Web页面URL地址, 2:后台请求URL地址, 3:Web页面UI资源） 如:('2', '3')
     */
    public Page<Resources> findByPage(Page<Resources> page, String title, String resourcesUrl, String permission, String resourcesType) {
        Parameter param = new Parameter();
        param.put("title", "%" + title + "%");
        param.put("resourcesUrl", "%" + resourcesUrl + "%");
        param.put("permission", permission);
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("resourcesType", resourcesType);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.ResourcesDao.findByPage", dataModel);
        return hibernateDao.findBySql(page, sql, param);
    }

    /**
     * 查询一个页面资源的所有依赖资源
     *
     * @param resourcesId 页面资源ID
     */
    public List<Resources> findDependenceResources(Serializable resourcesId) {
        Parameter param = new Parameter();
        param.put("resourcesId", resourcesId);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.ResourcesDao.findDependenceResources");
        return hibernateDao.findBySql(sql, param);
    }

    /**
     * 为页面资源增加一个依赖资源
     *
     * @param resourcesId           资源ID
     * @param dependenceResourcesId 依赖的资源ID
     * @return 成功返回true
     */
    public boolean addDependenceResources(Serializable resourcesId, Serializable dependenceResourcesId) {
        Parameter param = new Parameter();
        param.put("resourcesId", resourcesId);
        param.put("dependenceResourcesId", dependenceResourcesId);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.ResourcesDao.addDependenceResources");
        SQLQuery sqlQuery = hibernateDao.createSqlQuery(sql, param);
        return sqlQuery.executeUpdate() == 1;
    }

    /**
     * 为页面资源删除一个依赖资源
     *
     * @param resourcesId           资源ID
     * @param dependenceResourcesId 依赖的资源ID
     * @return 成功返回true
     */
    public boolean deleteDependenceResources(Serializable resourcesId, Serializable dependenceResourcesId) {
        Parameter param = new Parameter();
        param.put("resourcesId", resourcesId);
        param.put("dependenceResourcesId", dependenceResourcesId);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.ResourcesDao.deleteDependenceResources");
        SQLQuery sqlQuery = hibernateDao.createSqlQuery(sql, param);
        return sqlQuery.executeUpdate() == 1;
    }

    /**
     * 查询所有的资源信息数据
     */
    public List<Resources> findAllResources() {
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.ResourcesDao.findAllResources");
        return hibernateDao.findBySql(sql);
    }

    /**
     * 查询所有的资源关系信息 - 直接查询sys_resources_relation表
     *
     * @return 返回字段 resources_id,dependence_resources_id
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findAllResourcesRelation() {
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.ResourcesDao.findAllResourcesRelation");
        SQLQuery sqlQuery = hibernateDao.createSqlQuery(sql, null);
        sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return (List<Map<String, Object>>) sqlQuery.list();
    }

    /**
     * 查询角色拥有的所有资源数据 (不分页)
     *
     * @param roleId 角色ID
     */
    public List<Resources> findResourcesByRole(Serializable roleId) {
        Parameter param = new Parameter();
        param.put("roleId", roleId);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.ResourcesDao.findResourcesByRole");
        return hibernateDao.findBySql(sql, param);
    }

    /**
     * 根据资源路径(含有变量)查询资源
     *
     * @param resourcesUrl 资源路径(含有变量)
     * @return 不存在返回null
     */
    public Resources getResources(String resourcesUrl) {
        Parameter param = new Parameter();
        param.put("resourcesUrl", resourcesUrl);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.ResourcesDao.getResources");
        return hibernateDao.getBySql(sql, param);
    }

    /**
     * 返回用户所有的资源信息(不包含软删除数据)<br/>
     * <b>注意：数据量大时性能低下(5张表关联查询)</b>
     *
     * @param loginName 用户登录名
     * @return 不存在返回空集合
     */
    public List<Resources> getResourcesByUser(String loginName) {
        Parameter param = new Parameter(Resources.DEL_FLAG_NORMAL);
        param.put("loginName", loginName);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.ResourcesDao.getResourcesByUser");
        return hibernateDao.findBySql(sql, param);
    }
}
