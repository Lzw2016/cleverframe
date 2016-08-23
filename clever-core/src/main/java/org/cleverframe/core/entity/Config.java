package org.cleverframe.core.entity;

import org.cleverframe.core.persistence.entity.DataEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统配置
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-19 14:38 <br/>
 */
@Entity
@Table(name = "core_config")
@DynamicInsert
@DynamicUpdate
public class Config extends DataEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 配置键
     */
    private String configKey;

    /**
     * 配置数据值
     */
    private String configValue;

    /**
     * 配置组名称
     */
    private String configGroup;

    /**
     * 是否支持在线配置生效（0：否；1：是）
     */
    private Character hotSwap;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序(升序)
     */
    private Integer sort;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getConfigGroup() {
        return configGroup;
    }

    public void setConfigGroup(String configGroup) {
        this.configGroup = configGroup;
    }

    public Character getHotSwap() {
        return hotSwap;
    }

    public void setHotSwap(Character hotSwap) {
        this.hotSwap = hotSwap;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
