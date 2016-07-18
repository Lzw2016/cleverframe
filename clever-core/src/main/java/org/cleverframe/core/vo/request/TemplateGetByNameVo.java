package org.cleverframe.core.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-16 16:24 <br/>
 */
public class TemplateGetByNameVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 模版名称，不能重复
     */
    @NotBlank(message = "模版名称不能为空")
    @Length(max = 255, message = "模版名称长度不能超过255个字符")
    private String name;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
