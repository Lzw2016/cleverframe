package org.cleverframe.core.dao;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.entity.Config;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
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
     * @param page        分页对象
     * @param configKey   配置键
     * @param configValue 配置数据值
     * @param configGroup 配置组名称
     * @param hotSwap     是否支持在线配置生效（0：否；1：是）
     * @param id          ID
     * @param uuid        UUID
     * @param delFlag     DelFlag
     * @return 分页对象
     */
    public Page<Config> findByPage(Page<Config> page, String configKey, String configValue, String configGroup, Character hotSwap, Long id, String uuid, Character delFlag) {
        Parameter param = new Parameter(delFlag);
        param.put("configKey", configKey);
        param.put("configValue", configValue);
        param.put("configGroup", configGroup);
        param.put("hotSwap", hotSwap);
        param.put("id", id);
        param.put("uuid", uuid);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.core.dao.ConfigDao.findByPage");
        return hibernateDao.findBySql(page, sql, param);
    }

    /**
     * 根据 配置键 查询配置对象
     *
     * @param configKey 配置键
     * @return 系统配置对象
     */
    public Config getByKey(String configKey) {
        Parameter param = new Parameter(Config.DEL_FLAG_NORMAL);
        param.put("configKey", configKey);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.core.dao.ConfigDao.getByKey");
        return hibernateDao.getBySql(sql, param);
    }

    /**
     * 查询所有的配置数据
     *
     * @return 系统配置对象集合
     */
    public List<Config> getAllConfig() {
        Parameter param = new Parameter(Config.DEL_FLAG_NORMAL);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.core.dao.ConfigDao.getAllConfig");
        return hibernateDao.findBySql(sql, param);
    }

    /**
     * 直接从数据库删除配置信息<br/>
     *
     * @param configKey 配置键
     * @return 成功返回true，失败返回false
     */
    public boolean deleteConfig(String configKey) {
        Parameter param = new Parameter();
        param.put("configKey", configKey);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.core.dao.ConfigDao.deleteConfig");
        SQLQuery sqlQuery = hibernateDao.createSqlQuery(sql, param);
        sqlQuery.executeUpdate();
        return true;
    }
}
