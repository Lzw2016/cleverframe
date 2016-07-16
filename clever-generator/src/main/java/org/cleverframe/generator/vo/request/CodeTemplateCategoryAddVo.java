package org.cleverframe.generator.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-16 12:21 <br/>
 */
public class CodeTemplateCategoryAddVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 备注
     */
    @Length(max = 255, message = "备注信息长度不能超过255个字符")
    private String remarks;

    /**
     * 父级编号,根节点的父级编号是：-1
     */
    @NotNull(message = "父级编号不能为空")
    private Long parentId;

    /**
     * 代码模版名称，不能重复
     */
    @NotBlank(message = "代码模版名称不能为空")
    @Length(max = 255, message = "代码模版名称长度不能超过255个字符")
    private String name;

    /**
     * 模版说明
     */
    @NotBlank(message = "模版说明不能为空")
    @Length(max = 1000, message = "模版说明长度不能超过1000个字符")
    private String description;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
