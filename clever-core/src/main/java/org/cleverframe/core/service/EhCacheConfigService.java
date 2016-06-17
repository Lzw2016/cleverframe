package org.cleverframe.core.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.cleverframe.common.configuration.IConfig;
import org.cleverframe.common.ehcache.EhCacheNames;
import org.cleverframe.common.ehcache.EhCacheUtils;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.dao.ConfigDao;
import org.cleverframe.core.entity.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置信息获取或修改接口EhCache缓存实现方式<br/>
 * <b>注意:Config的更新只能通过该服务的方法</b>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-16 18:20 <br/>
 */
@Service(CoreBeanNames.EhCacheConfigService)
public class EhCacheConfigService extends BaseService implements IConfig {

    @Autowired
    @Qualifier(CoreBeanNames.ConfigDao)
    private ConfigDao configDao;

    /**
     * 系统配置缓存<br/>
     * <b>注意：不缓存软删除了的数据</b>
     */
    private Cache configCache = EhCacheUtils.createCache(EhCacheNames.ConfigCache);

    /**
     * 根据 配置键 查询配置对象<br/>
     * <b>
     * 1.先到缓存中获取,取不到再到数据库中获取<br/>
     * 2.获取到之后放到缓存中<br/>
     * </b>
     *
     * @param key 配置键
     * @return 系统配置对象
     */
    public Config getByKey(String key) {
        Config config = null;
        Element element = configCache.get(key);
        if (null != element && element.getObjectValue() instanceof Config) {
            config = (Config) element.getObjectValue();
        }
        if (null == config) {
            config = configDao.getByKey(key);
            if (config != null) {
                element = new Element(config.getConfigKey(), config);
                configCache.put(element);
                return config;
            }
        }
        return config;
    }

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
        page = configDao.findByPage(page, key, value, group, swap, id, uuid, delFlag);
        for (Config config : page.getList()) {
            if (Config.DEL_FLAG_NORMAL.equals(config.getDelFlag())) {
                Element element = new Element(config.getConfigKey(), config);
                configCache.put(element);
            }
        }
        return page;
    }

    /**
     * 保存配置信息到数据库,加到缓存中(不添加软删除的数据)<br/>
     *
     * @param config 配置信息对象
     * @return 成功返回true，失败返回false
     */
    @Transactional(readOnly = false)
    public boolean saveConfig(Config config) {
        configDao.getHibernateDao().save(config);
        if (Config.DEL_FLAG_NORMAL.equals(config.getDelFlag())) {
            Element element = new Element(config.getConfigKey(), config);
            configCache.put(element);
        }
        return true;
    }

    /**
     * 更新配置信息到数据库,加到缓存中(不添加软删除的数据)<br/>
     *
     * @param config 配置信息对象
     * @return 成功返回true，失败返回false
     */
    @Transactional(readOnly = false)
    public boolean updateConfig(Config config) {
        configDao.getHibernateDao().update(config.getId(), config);
        if (Config.DEL_FLAG_NORMAL.equals(config.getDelFlag())) {
            Element element = new Element(config.getConfigKey(), config);
            configCache.put(element);
        }
        return true;
    }

    /**
     * 直接从数据库删除配置信息,也会从缓存中删除<br/>
     *
     * @param key 配置键
     * @return 成功返回true，失败返回false
     */
    @Transactional(readOnly = false)
    public boolean deleteConfig(String key) {
        configDao.deleteConfig(key);
        configCache.remove(key);
        return true;
    }

    /* ====================================================================================================================================================== */

    /**
     * 获取配置信息,没有配置可以使用默认值
     *
     * @param key          配置键
     * @param defaultValue 默认值
     * @return 配置值，不存在返回defaultValue
     */
    @Override
    public String getConfig(String key, String defaultValue) {
        Config config = getByKey(key);
        if (config != null) {
            return config.getConfigValue();
        }
        return defaultValue;
    }

    /**
     * 获取配置信息
     *
     * @param key 配置键
     * @return 配置值，不存在返回null
     */
    @Override
    public String getConfig(String key) {
        return getConfig(key, null);
    }

    /**
     * 获取所有的配置信息
     *
     * @return 所有的配置
     */
    @Override
    public Map<String, String> getAllConfig() {
        Map<String, String> configMap = new HashMap<>();
        List<Config> configList = configDao.getAllConfig();
        for (Config config : configList) {
            Element element = new Element(config.getConfigKey(), config);
            configCache.put(element);
            configMap.put(config.getConfigKey(), config.getConfigValue());
        }
        return configMap;
    }

    /**
     * 重新加载某个配置
     *
     * @param key 配置键
     * @return 加载成功返回true，失败返回false
     */
    @Override
    public boolean reLoad(String key) {
        Config config = configDao.getByKey(key);
        if (config != null) {
            Element element = new Element(key, config);
            configCache.put(element);
            return true;
        }
        return false;
    }

    /**
     * 重新加载所有的配置
     *
     * @return 加载成功返回true，失败返回false
     */
    @Override
    public boolean reLoadAll() {
        getAllConfig();
        return true;
    }

    /**
     * 更新某个配置<br/>
     * 调用前需要检查配置是否支持更新，调用isCanUpdate(String key)<br/>
     *
     * @param key   配置键
     * @param value 配置值
     * @return 更新成功返回true，失败返回false
     * @see #isCanUpdate(String)
     */
    @Transactional(readOnly = false)
    @Override
    public boolean updateConfig(String key, String value) {
        Config config = configDao.getByKey(key);
        if (config != null && Config.YES.equals(config.getHotSwap())) {
            config.setConfigValue(value);
            configDao.getHibernateDao().update(config);
            Element element = new Element(config.getConfigKey(), config);
            configCache.put(element);
            return true;
        }
        return false;
    }

    /**
     * 检查配置是否支持更新操作<br/>
     * 此处的更新是指能够在线更新生效，不需要重启服务器<br/>
     *
     * @param key 配置键
     * @return 支持返回true，不支持返回false
     */
    @Override
    public boolean isCanUpdate(String key) {
        Config config = getByKey(key);
        return config != null && Config.YES.equals(config.getHotSwap());
    }
}
