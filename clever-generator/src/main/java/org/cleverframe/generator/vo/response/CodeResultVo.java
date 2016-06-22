package org.cleverframe.generator.vo.response;

import org.cleverframe.common.vo.response.BaseResponseVo;

/**
 * 根据模版生成的代码，请求返回对象
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-22 10:33 <br/>
 */
public class CodeResultVo extends BaseResponseVo {
    private static final long serialVersionUID = 1L;

    /**
     * 代码模版名称
     */
    private String templateName;

    /**
     * 模版说明
     */
    private String description;

    /**
     * 代码语言，如：java、html、jsp、sql
     */
    private String codeType;

    /**
     * 根据模版和数据生成的代码
     */
    private String codeContent;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
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

    public String getCodeContent() {
        return codeContent;
    }

    public void setCodeContent(String codeContent) {
        this.codeContent = codeContent;
    }
}
