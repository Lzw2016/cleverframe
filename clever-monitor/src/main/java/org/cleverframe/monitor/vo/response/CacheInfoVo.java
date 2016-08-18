package org.cleverframe.monitor.vo.response;

import org.cleverframe.common.vo.response.BaseResponseVo;

/**
 * 参考 CacheStatistics
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-8-18 14:43 <br/>
 */
public class CacheInfoVo extends BaseResponseVo {
    private static final long serialVersionUID = 1L;
    /**
     * 缓存名称
     */
    private String name;

    /**
     * 缓存状态
     */
    private String status;

    /**
     * 高速缓存唯一ID
     */
    private String guid;

    /**
     * 是否支持搜索
     */
    private boolean searchable;

    /**
     * 缓存元素个数
     */
    private int size;

    /**
     * 集群支持批量加载
     */
    private boolean clusterBulkLoadEnabled;

    /**
     * 当前节点支持批量加载
     */
    private boolean nodeBulkLoadEnabled;

    /**
     * 是否禁用缓存
     */
    private boolean disabled;

    /**
     * 是否是Terracotta集群
     */
    private boolean terracottaClustered;

    /**
     * 缓存配置
     */
    private String configurationXml;

    /**
     * 缓存配置
     */
    private String configurationJson;

    /**
     * 缓存命中次数
     */
    private long cacheHits;

    /**
     * 缓存未命中次数
     */
    private long cacheMisses;

    /**
     * 内存缓存命中次数
     */
    private long inMemoryHits;

    /**
     * 内存缓存未命中次数
     */
    private long inMemoryMisses;

    /**
     * 非堆存储缓存命中次数
     */
    private long offHeapHits;

    /**
     * 非堆存储缓存未命中次数
     */
    private long offHeapMisses;

    /**
     * 磁盘存储缓存命中次数
     */
    private long onDiskHits;

    /**
     * 磁盘存储缓存未命中次数
     */
    private long onDiskMisses;

    /**
     * 缓存元素数量
     */
    private long objectCount;

    /**
     * 准备写入缓的元素数量
     */
    private long writerQueueLength;

    /**
     * 准备写入缓的元素的最大限制值
     */
    private int writerMaxQueueSize;

    /**
     * 内存中缓存元素的数量
     */
    private long memoryStoreObjectCount;

    /**
     * 内存中缓存占用空间大小
     */
    private long heapSizeInBytes;

    /**
     * 非堆存储中缓存元素的数量
     */
    private long offHeapStoreObjectCount;

    /**
     * 非堆存储占用空间大小
     */
    private long offHeapSizeInBytes;

    /**
     * 磁盘存储中缓存元素的数量
     */
    private long diskStoreObjectCount;

    /**
     * 磁盘存储占用空间大小
     */
    private long diskSizeInBytes;

    /**
     * 缓存命中率
     */
    private double cacheHitPercentage;

    /**
     * 缓存未命中率
     */
    private double cacheMissPercentage;

    /**
     * 内存缓存命中率
     */
    private double inMemoryHitPercentage;

    /**
     * 非堆存储缓存命中率
     */
    private double offHeapHitPercentage;

    /**
     * 磁盘存储缓存命中率
     */
    private double onDiskHitPercentage;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isClusterBulkLoadEnabled() {
        return clusterBulkLoadEnabled;
    }

    public void setClusterBulkLoadEnabled(boolean clusterBulkLoadEnabled) {
        this.clusterBulkLoadEnabled = clusterBulkLoadEnabled;
    }

    public boolean isNodeBulkLoadEnabled() {
        return nodeBulkLoadEnabled;
    }

    public void setNodeBulkLoadEnabled(boolean nodeBulkLoadEnabled) {
        this.nodeBulkLoadEnabled = nodeBulkLoadEnabled;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isTerracottaClustered() {
        return terracottaClustered;
    }

    public void setTerracottaClustered(boolean terracottaClustered) {
        this.terracottaClustered = terracottaClustered;
    }

    public String getConfigurationXml() {
        return configurationXml;
    }

    public void setConfigurationXml(String configurationXml) {
        this.configurationXml = configurationXml;
    }

    public String getConfigurationJson() {
        return configurationJson;
    }

    public void setConfigurationJson(String configurationJson) {
        this.configurationJson = configurationJson;
    }

    public long getCacheHits() {
        return cacheHits;
    }

    public void setCacheHits(long cacheHits) {
        this.cacheHits = cacheHits;
    }

    public long getCacheMisses() {
        return cacheMisses;
    }

    public void setCacheMisses(long cacheMisses) {
        this.cacheMisses = cacheMisses;
    }

    public long getInMemoryHits() {
        return inMemoryHits;
    }

    public void setInMemoryHits(long inMemoryHits) {
        this.inMemoryHits = inMemoryHits;
    }

    public long getInMemoryMisses() {
        return inMemoryMisses;
    }

    public void setInMemoryMisses(long inMemoryMisses) {
        this.inMemoryMisses = inMemoryMisses;
    }

    public long getOffHeapHits() {
        return offHeapHits;
    }

    public void setOffHeapHits(long offHeapHits) {
        this.offHeapHits = offHeapHits;
    }

    public long getOffHeapMisses() {
        return offHeapMisses;
    }

    public void setOffHeapMisses(long offHeapMisses) {
        this.offHeapMisses = offHeapMisses;
    }

    public long getOnDiskHits() {
        return onDiskHits;
    }

    public void setOnDiskHits(long onDiskHits) {
        this.onDiskHits = onDiskHits;
    }

    public long getOnDiskMisses() {
        return onDiskMisses;
    }

    public void setOnDiskMisses(long onDiskMisses) {
        this.onDiskMisses = onDiskMisses;
    }

    public long getObjectCount() {
        return objectCount;
    }

    public void setObjectCount(long objectCount) {
        this.objectCount = objectCount;
    }

    public long getWriterQueueLength() {
        return writerQueueLength;
    }

    public void setWriterQueueLength(long writerQueueLength) {
        this.writerQueueLength = writerQueueLength;
    }

    public int getWriterMaxQueueSize() {
        return writerMaxQueueSize;
    }

    public void setWriterMaxQueueSize(int writerMaxQueueSize) {
        this.writerMaxQueueSize = writerMaxQueueSize;
    }

    public long getMemoryStoreObjectCount() {
        return memoryStoreObjectCount;
    }

    public void setMemoryStoreObjectCount(long memoryStoreObjectCount) {
        this.memoryStoreObjectCount = memoryStoreObjectCount;
    }

    public long getHeapSizeInBytes() {
        return heapSizeInBytes;
    }

    public void setHeapSizeInBytes(long heapSizeInBytes) {
        this.heapSizeInBytes = heapSizeInBytes;
    }

    public long getOffHeapStoreObjectCount() {
        return offHeapStoreObjectCount;
    }

    public void setOffHeapStoreObjectCount(long offHeapStoreObjectCount) {
        this.offHeapStoreObjectCount = offHeapStoreObjectCount;
    }

    public long getOffHeapSizeInBytes() {
        return offHeapSizeInBytes;
    }

    public void setOffHeapSizeInBytes(long offHeapSizeInBytes) {
        this.offHeapSizeInBytes = offHeapSizeInBytes;
    }

    public long getDiskStoreObjectCount() {
        return diskStoreObjectCount;
    }

    public void setDiskStoreObjectCount(long diskStoreObjectCount) {
        this.diskStoreObjectCount = diskStoreObjectCount;
    }

    public long getDiskSizeInBytes() {
        return diskSizeInBytes;
    }

    public void setDiskSizeInBytes(long diskSizeInBytes) {
        this.diskSizeInBytes = diskSizeInBytes;
    }

    public double getCacheHitPercentage() {
        return cacheHitPercentage;
    }

    public void setCacheHitPercentage(double cacheHitPercentage) {
        this.cacheHitPercentage = cacheHitPercentage;
    }

    public double getCacheMissPercentage() {
        return cacheMissPercentage;
    }

    public void setCacheMissPercentage(double cacheMissPercentage) {
        this.cacheMissPercentage = cacheMissPercentage;
    }

    public double getInMemoryHitPercentage() {
        return inMemoryHitPercentage;
    }

    public void setInMemoryHitPercentage(double inMemoryHitPercentage) {
        this.inMemoryHitPercentage = inMemoryHitPercentage;
    }

    public double getOffHeapHitPercentage() {
        return offHeapHitPercentage;
    }

    public void setOffHeapHitPercentage(double offHeapHitPercentage) {
        this.offHeapHitPercentage = offHeapHitPercentage;
    }

    public double getOnDiskHitPercentage() {
        return onDiskHitPercentage;
    }

    public void setOnDiskHitPercentage(double onDiskHitPercentage) {
        this.onDiskHitPercentage = onDiskHitPercentage;
    }
}
