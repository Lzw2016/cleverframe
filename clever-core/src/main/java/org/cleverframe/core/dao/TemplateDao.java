package org.cleverframe.core.dao;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.entity.Template;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-17 15:52 <br/>
 */
@Repository(CoreBeanNames.TemplateDao)
public class TemplateDao extends BaseDao<Template> {

    /**
     * 查询所有未被软删除的模版数据<br/>
     *
     * @return 模版集合
     */
    public List<Template> findAll() {
        Parameter param = new Parameter(Template.DEL_FLAG_NORMAL);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.core.dao.TemplateDao.findAll");
        return hibernateDao.findBySql(sql, param);
    }

    /**
     * 获取数据库脚本，使用分页<br/>
     *
     * @param page    分页对象
     * @param name    查询参数：模版名称
     * @param locale  查询参数：模版语言
     * @param id      查询参数：ID
     * @param uuid    查询参数：UUID
     * @param delFlag 查询参数：删除标记
     * @return 分页数据
     */
    public Page<Template> findByPage(Page<Template> page, String name, String locale, Long id, String uuid, Character delFlag) {
        Parameter param = new Parameter(delFlag);
        param.put("name", name);
        param.put("locale", locale);
        param.put("id", id);
        param.put("uuid", uuid);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.core.dao.TemplateDao.findByPage");
        return hibernateDao.findBySql(page, sql, param);
    }

    /**
     * 根据模版名称查询模版<br/>
     *
     * @param name 模版名称
     * @return 模版对象, 不存在返回null
     */
    public Template getByName(String name) {
        Parameter param = new Parameter(Template.DEL_FLAG_NORMAL);
        param.put("name", name);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.core.dao.TemplateDao.getByName");
        return hibernateDao.getBySql(sql, param);
    }

    /**
     * 根据模版名称直接从数据库删除模版数据<br/>
     *
     * @param name 模版名称
     * @return 删除成功返回true
     */
    public boolean deleteByName(String name) {
        Parameter param = new Parameter();
        param.put("name", name);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.core.dao.TemplateDao.deleteByName");
        SQLQuery sqlQuery = hibernateDao.createSqlQuery(sql, param);
        sqlQuery.executeUpdate();
        return true;
    }

    /**
     * 模版名称是否存在
     *
     * @param name 模版名称
     * @return 存在返回true，不存在返回false
     */
    public boolean templateNameExists(String name) {
        Parameter param = new Parameter();
        param.put("name", name);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.core.dao.TemplateDao.templateNameExists");
        long count = hibernateDao.getBySql(long.class, sql, param);
        return count > 0;
    }
}
