package org.cleverframe.common.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EHCache 工具类，管理所有的Cache对象<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-12 17:28 <br/>
 */
public class EhCacheUtils {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(EhCacheUtils.class);

    /**
     * Ehcache的缓存管理器
     */
    private static CacheManager CACHE_MANAGER = SpringContextHolder.getBean(SpringBeanNames.CacheManager);

    /**
     * 得到Ehcahe的CacheManager
     */
    public static CacheManager getCacheManager() {
        if (null == CACHE_MANAGER) {
            CACHE_MANAGER = SpringContextHolder.getBean(SpringBeanNames.CacheManager);
        }
        if (null == CACHE_MANAGER) {
            logger.error("### EhCacheUtils.CACHE_MANAGER 初始化失败（从Spring容器里获取失败）");
        }
        return CACHE_MANAGER;
    }

    /**
     * 获得一个Cache，没有返回null<br/>
     *
     * @param cacheName Ehcache缓存名称，参考：EhCacheNames
     * @return Cache对象实例
     */
    public static Cache getCache(String cacheName) {
        return CACHE_MANAGER.getCache(cacheName);
    }

    /**
     * 创建一个新的Cache，若Cache已存在则直接返回<br/>
     * <b>注意：创建新的缓存要到EhCacheNames类中定义名称</b>
     *
     * @param cacheName Ehcache缓存名称，参考：EhCacheNames
     * @return Cache对象实例
     */
    public static Cache createCache(String cacheName) {
        Cache cache = CACHE_MANAGER.getCache(cacheName);
        if (cache == null) {
            CACHE_MANAGER.addCache(cacheName);
            cache = CACHE_MANAGER.getCache(cacheName);
            // cache.getCacheConfiguration().setEternal(true);
        }
        return cache;
    }

    /**
     * 返回所有的Cache的名称<br/>
     *
     * @return Cache的名称数组
     */
    public static String[] getCacheName() {
        return CACHE_MANAGER.getCacheNames();
    }

    /**
     * 判断Cache是否存在
     *
     * @param cacheName Cache的名称
     * @return 存在返回true，不存在返回false
     */
    public static boolean cacheExists(String cacheName) {
        return CACHE_MANAGER.cacheExists(cacheName);
    }

    /**
     * 从指定的缓存中删除对象<br/>
     *
     * @param cacheName 缓存名称，参考类：EhCacheNames
     * @param key       缓存对象的主键
     */
    public static void remove(String cacheName, String key) {
        Cache cache = CACHE_MANAGER.getCache(cacheName);
        if (cache != null) {
            cache.remove(key);
        }
    }

    /**
     * 把对象存入指定的缓存中<br/>
     *
     * @param cacheName 缓存名称，参考类：EhCacheNames
     * @param key       缓存对象的主键
     * @param value     缓存对象
     * @return 操作成功返回true
     */
    public static boolean put(String cacheName, String key, Object value) {
        Element element = new Element(key, value);
        Cache cache = CACHE_MANAGER.getCache(cacheName);
        if (cache == null) {
            return false;
        }
        cache.put(element);
        return true;
    }

    /**
     * 从指定的缓存中取得对象<br/>
     *
     * @param cacheName 缓存名称，参考类：EhCacheNames
     * @param key       缓存对象的主键
     * @return 不存在返回null
     */
    public static Object get(String cacheName, String key) {
        Cache cache = CACHE_MANAGER.getCache(cacheName);
        if (cache == null) {
            return null;
        }
        Element element = cache.get(key);
        return element == null ? null : element.getObjectValue();
    }
}
