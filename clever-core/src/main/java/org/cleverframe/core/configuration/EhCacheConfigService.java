package org.cleverframe.core.configuration;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.cleverframe.common.configuration.IConfig;
import org.cleverframe.common.ehcache.EhCacheNames;
import org.cleverframe.common.ehcache.EhCacheUtils;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.dao.ConfigDao;
import org.cleverframe.core.entity.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
public class EhCacheConfigService implements IConfig {

    @Autowired
    @Qualifier(CoreBeanNames.ConfigDao)
    private ConfigDao configDao;

    /**
     * 系统配置缓存<br/>
     * <b>注意：不缓存软删除了的数据</b>
     */
    private Cache configCache = EhCacheUtils.createCache(EhCacheNames.ConfigCache);

    /**
     * 获取配置信息,没有配置可以使用默认值
     *
     * @param key          配置键
     * @param defaultValue 默认值
     * @return 配置值，不存在返回defaultValue
     */
    @Override
    public String getConfig(String key, String defaultValue) {
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
                return config.getConfigValue();
            }
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
        Config config = configDao.getByKey(key);
        if (config != null) {
            return Config.YES.equals(config.getHotSwap());
        }
        return false;
    }
}
