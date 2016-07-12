package org.cleverframe.core.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 更新(Config)的请求参数对象<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-17 14:27 <br/>
 */
public class ConfigUpdateVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 数据ID
     */
    @NotNull(message = "数据ID不能为空")
    private Long id;

    /**
     * 删除标记（1：正常；2：删除；3：审核）,以字典表sys_dict.dict_key=‘删除标记’为准'
     */
    @Pattern(regexp = "1|2", message = "删除标记只能是：1(正常)、2(删除)")
    private String delFlag;

    /**
     * 配置键
     */
    @Length(min = 0, max = 100, message = "配置键长度不能超过100个字符")
    private String configKey;

    /**
     * 配置数据值
     */
    @Length(min = 0, max = 255, message = "配置数据值长度不能超过255个字符")
    private String configValue;

    /**
     * 配置组名称
     */
    @Length(min = 0, max = 255, message = "配置组名称长度不能超过100个字符")
    private String configGroup;

    /**
     * 是否支持在线配置生效（0：否；1：是）
     */
    @Pattern(regexp = "0|1", message = "是否支持在线配置生效只能是：0-否,1-是")
    private String hotSwap;

    /**
     * 描述
     */
    @Length(min = 1, max = 500, message = "配置描述长度不能操作500个字符")
    private String description;

    /**
     * 排序(升序)
     */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
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
