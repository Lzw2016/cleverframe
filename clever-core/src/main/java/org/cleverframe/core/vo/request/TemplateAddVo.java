package org.cleverframe.core.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-17 15:29 <br/>
 */
public class TemplateAddVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 备注
     */
    @Length(max = 255, message = "备注信息长度不能超过255个字符")
    private String remarks;

    /**
     * 模版名称，不能重复
     */
    @NotBlank(message = "模版名称不能为空")
    @Length(max = 255, message = "模版名称长度不能超过255个字符")
    private String name;

    /**
     * 模版内容
     */
    @NotBlank(message = "模版内容不能为空")
    @Length(max = 2000, message = "模版内容长度不能超过2000个字符")
    private String content;

    /**
     * 模版语言
     */
    @NotBlank(message = "模版语言不能为空")
    @Length(max = 50, message = "模版语言长度不能超过2000个字符")
    private String locale;

    /**
     * 模版说明
     */
    @NotBlank(message = "模版说明不能为空")
    @Length(max = 1000, message = "模版说明长度不能超过2000个字符")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
