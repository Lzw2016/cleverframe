package org.cleverframe.activiti.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/12/3 23:26 <br/>
 */
public class CreateModelVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 流程名称
     */
    @NotNull(message = "流程名称不能为空")
    @Length(max = 255, message = "流程名称值长度不能超过255个字符")
    private String name;

    /**
     * 流程Key
     */
    @NotNull(message = "流程Key不能为空")
    @Length(max = 255, message = "流程Key值长度不能超过255个字符")
    private String key;

    /**
     * 流程说明
     */
    @Length(max = 255, message = "流程说明值长度不能超过255个字符")
    private String description;

    /**
     * 流程类别
     */
    @Length(max = 255, message = "流程类别值长度不能超过255个字符")
    private String category;

    /**
     * 流程租户ID
     */
    @Length(max = 255, message = "流程租户ID值长度不能超过255个字符")
    private String tenantId;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
