package org.cleverframe.core.dao;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.entity.Config;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-16 20:41 <br/>
 */
@Repository(CoreBeanNames.ConfigDao)
public class ConfigDao extends BaseDao<Config> {

    /**
     * 分页查询配置数据
     *
     * @param page    分页对象
     * @param key     配置键
     * @param value   配置数据值
     * @param group   配置组名称
     * @param swap    是否支持在线配置生效（0：否；1：是）
     * @param id      ID
     * @param uuid    UUID
     * @param delFlag DelFlag
     * @return 分页对象
     */
    public Page<Config> findByPage(Page<Config> page, String key, String value, String group, Character swap, Long id, String uuid, Character delFlag) {
        Parameter param = new Parameter(delFlag);
        param.put("key", key);
        param.put("value", value);
        param.put("group", group);
        param.put("swap", swap);
        param.put("id", id);
        param.put("uuid", uuid);
        return hibernateDao.findBySql(page, "org.cleverframe.core.dao.ConfigDao.findByPage", param);
    }

    /**
     * 根据 配置键 查询配置对象
     *
     * @param key 配置键
     * @return 系统配置对象
     */
    public Config getByKey(String key) {
        Parameter param = new Parameter(Config.DEL_FLAG_NORMAL);
        param.put("key", key);
        return hibernateDao.getBySql("org.cleverframe.core.dao.ConfigDao.getByKey", param);
    }

    /**
     * 查询所有的配置数据
     *
     * @return 系统配置对象集合
     */
    public List<Config> getAllConfig() {
        Parameter param = new Parameter(Config.DEL_FLAG_NORMAL);
        return hibernateDao.findBySql("org.cleverframe.core.dao.ConfigDao.getAllConfig", param);
    }

    /**
     * 直接从数据库删除配置信息<br/>
     *
     * @param key 配置键
     * @return 成功返回true，失败返回false
     */
    public boolean deleteConfig(String key) {
        Parameter param = new Parameter();
        param.put("key", key);
        SQLQuery sqlQuery = hibernateDao.createSqlQuery("org.cleverframe.core.dao.ConfigDao.deleteConfig", param);
        sqlQuery.executeUpdate();
        return true;
    }
}
