package org.cleverframe.generator.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-7-16 19:12 <br/>
 */
public class CodeTemplateDelVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 模版名称
     */
    @NotBlank(message = "代码模版名称不能为空")
    @Length(max = 255, message = "代码模版名称长度不能超过255个字符")
    private String codeTemplateName;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getCodeTemplateName() {
        return codeTemplateName;
    }

    public void setCodeTemplateName(String codeTemplateName) {
        this.codeTemplateName = codeTemplateName;
    }
}
