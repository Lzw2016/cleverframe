package org.cleverframe.monitor.vo.response;

import org.cleverframe.common.vo.response.BaseResponseVo;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-18 13:55 <br/>
 */
public class CacheManagerInfoVo extends BaseResponseVo {
    private static final long serialVersionUID = 1L;

    /**
     * 缓存管理器 名称
     */
    private String name;

    /**
     * 缓存管理器 集群UUID
     */
    private String clusterUUID;

    /**
     * 缓存管理器 状态
     */
    private String status;

    /**
     * 显式命名的缓存管理
     */
    private boolean named;

    /**
     * 缓存管理器 配置文件
     */
    private String configurationXml;

    /**
     * 缓存管理器 配置文件
     */
    private String configurationJson;

    /**
     * 缓存管理器 磁盘存储路径管理器
     */
    private String diskStorePathManagerJson;

    /**
     * 缓存管理器 磁盘数据池
     */
    private String onDiskPoolJson;

    /**
     * 缓存管理器 内存数据池
     */
    private String onHeapPoolJson;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClusterUUID() {
        return clusterUUID;
    }

    public void setClusterUUID(String clusterUUID) {
        this.clusterUUID = clusterUUID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isNamed() {
        return named;
    }

    public void setNamed(boolean named) {
        this.named = named;
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

    public String getDiskStorePathManagerJson() {
        return diskStorePathManagerJson;
    }

    public void setDiskStorePathManagerJson(String diskStorePathManagerJson) {
        this.diskStorePathManagerJson = diskStorePathManagerJson;
    }

    public String getOnDiskPoolJson() {
        return onDiskPoolJson;
    }

    public void setOnDiskPoolJson(String onDiskPoolJson) {
        this.onDiskPoolJson = onDiskPoolJson;
    }

    public String getOnHeapPoolJson() {
        return onHeapPoolJson;
    }

    public void setOnHeapPoolJson(String onHeapPoolJson) {
        this.onHeapPoolJson = onHeapPoolJson;
    }
}
