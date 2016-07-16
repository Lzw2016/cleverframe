package org.cleverframe.generator.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 23:02 <br/>
 */
public class CodeTemplateCodeAddVo extends BaseRequestVo {
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

    /**
     * 模版代码语言，如：java、html、jsp、sql<br/>
     * #see {@link org.cleverframe.generator.format.CodeType}
     */
    @NotBlank(message = "模版代码语言不能为空")
    @Length(max = 50, message = "模版代码语言长度不能超过255个字符")
    private String codeType;

    /*------------------------------------ core_template模版表字段数据 ------------------------------------*/

    /**
     * 模版内容
     */
    @NotBlank(message = "模版内容不能为空")
    @Length(max = 2000000, message = "模版内容长度不能超过2000000个字符")
    private String content;

    /**
     * 模版语言
     */
    @NotBlank(message = "模版语言不能为空")
    @Length(max = 50, message = "模版语言长度不能超过50个字符")
    private String locale;

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

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
