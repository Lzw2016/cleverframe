package org.cleverframe.monitor.vo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.cleverframe.common.vo.response.BaseResponseVo;

import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-8-18 15:34 <br/>
 */
public class ElementInfoVo extends BaseResponseVo {
    private static final long serialVersionUID = 1L;

    /**
     * 元素键
     */
    private Object key;

    /**
     * 元素键 类型
     */
    private String keyClass;

    /**
     * 元素值
     */
    private Object value;

    /**
     * 元素键 类型
     */
    private String valueClass;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date createTime;

    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date expirationTime;

    /**
     * 命中次数
     */
    private long hitCount;

    /**
     * 最后访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date lastAccessTime;

    /**
     * 最后更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date lastUpdateTime;

    /**
     * 元素大小
     */
    private long serializedSize;
    /**
     * 空闲时间
     */
    private long timeToIdle;

    /**
     * 存储时间
     */
    private long timeToLive;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public String getValueClass() {
        return valueClass;
    }

    public void setValueClass(String valueClass) {
        this.valueClass = valueClass;
    }

    public String getKeyClass() {
        return keyClass;
    }

    public void setKeyClass(String keyClass) {
        this.keyClass = keyClass;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public long getHitCount() {
        return hitCount;
    }

    public void setHitCount(long hitCount) {
        this.hitCount = hitCount;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public long getSerializedSize() {
        return serializedSize;
    }

    public void setSerializedSize(long serializedSize) {
        this.serializedSize = serializedSize;
    }

    public long getTimeToIdle() {
        return timeToIdle;
    }

    public void setTimeToIdle(long timeToIdle) {
        this.timeToIdle = timeToIdle;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }
}
