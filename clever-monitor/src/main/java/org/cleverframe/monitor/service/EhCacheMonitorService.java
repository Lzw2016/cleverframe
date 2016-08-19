package org.cleverframe.monitor.service;

import com.alibaba.fastjson.JSON;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.ehcache.EhCacheUtils;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.monitor.MonitorBeanNames;
import org.cleverframe.monitor.vo.response.CacheInfoVo;
import org.cleverframe.monitor.vo.response.CacheManagerInfoVo;
import org.cleverframe.monitor.vo.response.ElementInfoVo;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-17 19:52 <br/>
 */
@Component(MonitorBeanNames.EhCacheMonitorService)
public class EhCacheMonitorService {
//    /**
//    * 日志对象
//    */
//    private final static Logger logger = LoggerFactory.getLogger(EhCacheMonitorService.class);

    /**
     * 计算百分比
     */
    private static double getPercentage(long number, long total) {
        if (total == 0) {
            return 0.0;
        } else {
            return number / (double) total;
        }
    }

    /**
     * 获取所有的EhCache缓存名称
     */
    public List<String> getAllEhCacheNames() {
        CacheManager cacheManager = EhCacheUtils.getCacheManager();
        String[] cacheNames = cacheManager.getCacheNames();
        Arrays.sort(cacheNames);
        return Arrays.asList(cacheNames);
    }

    /**
     * 获取缓存管理器信息
     *
     * @return 获取缓存管理器信息
     */
    public CacheManagerInfoVo getCacheManagerInfo() {
        CacheManagerInfoVo cacheManagerInfoVo = new CacheManagerInfoVo();
        CacheManager cacheManager = EhCacheUtils.getCacheManager();
        cacheManagerInfoVo.setName(cacheManager.getName());
        cacheManagerInfoVo.setClusterUUID(cacheManager.getClusterUUID());
        cacheManagerInfoVo.setStatus(cacheManager.getStatus().toString());
        cacheManagerInfoVo.setNamed(cacheManager.isNamed());
        cacheManagerInfoVo.setConfigurationXml(cacheManager.getActiveConfigurationText());
        cacheManagerInfoVo.setConfigurationJson(JSON.toJSONString(cacheManager.getConfiguration()));
        cacheManagerInfoVo.setDiskStorePathManagerJson(JSON.toJSONString(cacheManager.getDiskStorePathManager()));
        cacheManagerInfoVo.setOnDiskPoolJson(JSON.toJSONString(cacheManager.getOnDiskPool()));
        cacheManagerInfoVo.setOnHeapPoolJson(JSON.toJSONString(cacheManager.getOnHeapPool()));
        return cacheManagerInfoVo;
    }

    /**
     * 获取缓存信息
     *
     * @param cacheName 缓存名称
     * @return 不存在返回null
     */
    public CacheInfoVo getCacheInfo(String cacheName) {
        CacheManager cacheManager = EhCacheUtils.getCacheManager();
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            return null;
        }
        CacheInfoVo cacheInfoVo = new CacheInfoVo();
        cacheInfoVo.setName(cache.getName());
        cacheInfoVo.setStatus(cache.getStatus().toString());
        cacheInfoVo.setGuid(cache.getGuid());
        cacheInfoVo.setSearchable(cache.isSearchable());
        cacheInfoVo.setSize(cache.getSize());
        // cacheInfoVo.setClusterBulkLoadEnabled(cache.isClusterBulkLoadEnabled());
        // cacheInfoVo.setNodeBulkLoadEnabled(cache.isNodeBulkLoadEnabled());
        cacheInfoVo.setDisabled(cache.isDisabled());
        cacheInfoVo.setTerracottaClustered(cache.isTerracottaClustered());
        cacheInfoVo.setConfigurationXml(cacheManager.getActiveConfigurationText(cacheName));
        cacheInfoVo.setConfigurationJson(JSON.toJSONString(cache.getCacheConfiguration()));
        cacheInfoVo.setCacheHits(cache.getStatistics().cacheHitCount());
        cacheInfoVo.setCacheMisses(cache.getStatistics().cacheMissCount());
        cacheInfoVo.setInMemoryHits(cache.getStatistics().localHeapHitCount());
        cacheInfoVo.setInMemoryMisses(cache.getStatistics().localHeapMissCount());
        cacheInfoVo.setOffHeapHits(cache.getStatistics().localOffHeapHitCount());
        cacheInfoVo.setOffHeapMisses(cache.getStatistics().localOffHeapMissCount());
        cacheInfoVo.setOnDiskHits(cache.getStatistics().localDiskHitCount());
        cacheInfoVo.setOnDiskMisses(cache.getStatistics().localDiskMissCount());
        cacheInfoVo.setObjectCount(cache.getStatistics().getSize());
        cacheInfoVo.setWriterQueueLength(cache.getStatistics().getWriterQueueLength());
        cacheInfoVo.setWriterMaxQueueSize(cache.getCacheConfiguration().getCacheWriterConfiguration().getWriteBehindMaxQueueSize());
        cacheInfoVo.setMemoryStoreObjectCount(cache.getStatistics().getLocalHeapSize());
        cacheInfoVo.setHeapSizeInBytes(cache.getStatistics().getLocalHeapSizeInBytes());
        cacheInfoVo.setOffHeapStoreObjectCount(cache.getStatistics().getLocalOffHeapSize());
        cacheInfoVo.setOffHeapSizeInBytes(cache.getStatistics().getLocalOffHeapSizeInBytes());
        cacheInfoVo.setDiskStoreObjectCount(cache.getStatistics().getLocalDiskSize());
        cacheInfoVo.setDiskSizeInBytes(cache.getStatistics().getLocalDiskSizeInBytes());

        long hits = cache.getStatistics().cacheHitCount();
        long misses = cache.getStatistics().cacheMissCount();
        long total = hits + misses;
        cacheInfoVo.setCacheHitPercentage(getPercentage(hits, total));
        cacheInfoVo.setCacheMissPercentage(getPercentage(misses, total));

        hits = cache.getStatistics().localHeapHitCount();
        misses = cache.getStatistics().localHeapMissCount();
        total = hits + misses;
        cacheInfoVo.setInMemoryHitPercentage(getPercentage(hits, total));

        hits = cache.getStatistics().localOffHeapHitCount();
        misses = cache.getStatistics().localOffHeapMissCount();
        total = hits + misses;
        cacheInfoVo.setOffHeapHitPercentage(getPercentage(hits, total));

        hits = cache.getStatistics().localDiskHitCount();
        misses = cache.getStatistics().localDiskMissCount();
        total = hits + misses;
        cacheInfoVo.setOnDiskHitPercentage(getPercentage(hits, total));

        return cacheInfoVo;
    }

    /**
     * 清除缓存数据
     *
     * @param cacheName 缓存名称
     * @return 成功返回true
     */
    public boolean clearCache(String cacheName) {
        CacheManager cacheManager = EhCacheUtils.getCacheManager();
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            return false;
        }
        cache.removeAll();
        return true;
    }

    /**
     * 获取缓存元素信息
     *
     * @param element 缓存元素对象
     * @return 获取缓存元素信息
     */
    public ElementInfoVo getElementInfo(Element element) {
        ElementInfoVo elementInfoVo = new ElementInfoVo();
        elementInfoVo.setKey(element.getObjectKey());
        elementInfoVo.setKeyClass(element.getObjectKey() == null ? null : element.getObjectKey().getClass().getCanonicalName());
        elementInfoVo.setValue(element.getObjectValue());
        elementInfoVo.setValueClass(element.getObjectValue() == null ? null : element.getObjectValue().getClass().getCanonicalName());
        elementInfoVo.setCreateTime(new Date(element.getCreationTime()));
        elementInfoVo.setExpirationTime(new Date(element.getExpirationTime()));
        elementInfoVo.setHitCount(element.getHitCount());
        elementInfoVo.setLastAccessTime(new Date(element.getLastAccessTime()));
        elementInfoVo.setLastUpdateTime(new Date(element.getLastUpdateTime()));
        elementInfoVo.setSerializedSize(element.getSerializedSize());
        elementInfoVo.setTimeToIdle(element.getTimeToIdle());
        elementInfoVo.setTimeToLive(element.getTimeToLive());
        return elementInfoVo;
    }

    /**
     * 获取缓存元素信息
     *
     * @param cacheName 缓存名称
     * @param key       缓存键
     * @return 获取缓存元素信息, 不存在返回null
     */
    public ElementInfoVo getElementInfo(String cacheName, Serializable key) {
        CacheManager cacheManager = EhCacheUtils.getCacheManager();
        Cache cache = cacheManager.getCache(cacheName);
        Element element = cache.get(key);
        if (element == null) {
            return null;
        }
        return this.getElementInfo(element);
    }

    /**
     * 移除缓存元素信息
     *
     * @param cacheName 缓存名称
     * @param key       缓存键
     * @return 移除成功返回true
     */
    public boolean removeElement(String cacheName, Serializable key) {
        CacheManager cacheManager = EhCacheUtils.getCacheManager();
        Cache cache = cacheManager.getCache(cacheName);
        cache.remove(key);
        return true;
    }

    /**
     * 分页获取缓存数据<br/>
     * <b>注意:生产环境慎用!</b>
     *
     * @param page      分页数据
     * @param cacheName 缓存名称
     * @param key       缓存键
     * @return 分页数据
     */
    public Page<ElementInfoVo> getCacheData(Page<ElementInfoVo> page, String cacheName, Serializable key) {
        List<ElementInfoVo> result = new ArrayList<>();
        // 只查询一个元素
        if (key != null && StringUtils.isNotBlank(key.toString())) {
            ElementInfoVo elementInfoVo = getElementInfo(cacheName, key);
            if (elementInfoVo == null) {
                page.setCount(0);
            } else {
                result.add(elementInfoVo);
                page.setCount(1);
                page.setList(result);
            }
            return page;
        }

        // 分页获取缓存数据
        CacheManager cacheManager = EhCacheUtils.getCacheManager();
        Cache cache = cacheManager.getCache(cacheName);
        if (cache.isSearchable()) {
            // 支持搜索
            Query query = cache.createQuery().includeKeys();
            // query.maxResults(10000000);
            Results results = query.execute();
            List<Result> resultList = results.range(page.getFirstResult(), page.getPageSize());
            for (Result item : resultList) {
                Element element = cache.get(item.getKey());
                result.add(getElementInfo(element));
            }
        } else {
            List list = cache.getKeys();
            int lastResult = page.getFirstResult() + page.getPageSize();
            for (int i = page.getFirstResult(); i < lastResult && i < list.size(); i++) {
                Object itemKey = list.get(i);
                Element element = cache.get(itemKey);
                result.add(getElementInfo(element));
            }
        }
        page.setCount(cache.getSize());
        page.setList(result);
        return page;
    }
}
