package org.cleverframe.core.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 增加(Config)的请求参数对象<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-17 11:47 <br/>
 */
public class ConfigAddVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 配置键
     */
    @NotBlank(message = "配置键不能为空")
    @Length(min = 1, max = 100, message = "配置键长度不能超过100个字符")
    private String configKey;

    /**
     * 配置数据值
     */
    @NotBlank(message = "配置数据值不能为空")
    @Length(min = 1, max = 255, message = "配置数据值长度不能超过255个字符")
    private String configValue;

    /**
     * 配置组名称
     */
    @Length(min = 0, max = 255, message = "配置组名称长度不能超过100个字符")
    private String configGroup;

    /**
     * 是否支持在线配置生效（0：否；1：是）
     */
    @NotBlank(message = "是否支持在线配置生效不能为空")
    @Pattern(regexp = "0|1", message = "是否支持在线配置生效只能是：0-否,1-是")
    private String hotSwap;

    /**
     * 描述
     */
    @NotBlank(message = "配置描述不能为空")
    @Length(min = 1, max = 500, message = "配置描述长度不能操作500个字符")
    private String description;

    /**
     * 排序(升序)
     */
    @NotBlank(message = "排序值不能为空")
    private Integer sort;

    /**
     * 备注
     */
    @Length(min = 0, max = 255, message = "备注信息长度不能操作255个字符")
    private String remarks;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getHotSwap() {
        return hotSwap;
    }

    public void setHotSwap(String hotSwap) {
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
