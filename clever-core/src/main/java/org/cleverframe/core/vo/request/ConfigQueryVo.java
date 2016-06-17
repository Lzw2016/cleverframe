package org.cleverframe.core.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

/**
 * 查询(Config)的请求参数对象<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-17 11:17 <br/>
 */
public class ConfigQueryVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 数据全局标识UUID
     */
    private String uuid;

    /**
     * 数据ID
     */
    private Long id;

    /**
     * 删除标记（1：正常；2：删除；3：审核）,以字典表sys_dict.dict_key=‘删除标记’为准'
     */
    private Character delFlag;

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

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Character delFlag) {
        this.delFlag = delFlag;
    }

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
}
